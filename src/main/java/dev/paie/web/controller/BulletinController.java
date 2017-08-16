package dev.paie.web.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Periode;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.repository.BulletinSalaireRepository;
import dev.paie.repository.PeriodeRepository;
import dev.paie.repository.RemunerationEmployeRepository;
import dev.paie.service.BulletinSalaireService;
import dev.paie.service.CalculerRemunerationService;

@Controller
@RequestMapping("/bulletins")
public class BulletinController {

	@Autowired
	private PeriodeRepository perRepo;

	@Autowired
	private RemunerationEmployeRepository remRepo;
	@Autowired
	private BulletinSalaireRepository bulRepo;
	@Autowired
	private BulletinSalaireService bulSer;

	@Autowired
	private CalculerRemunerationService remuServ;

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	@Secured("ROLE_ADMINISTRATEUR")
	public ModelAndView creerEmploye() {
		List<Periode> listPer = perRepo.findAll();
		List<RemunerationEmploye> listRem = remRepo.findAll();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bulletins/creerBulletin");
		mv.addObject("listPer", listPer);
		mv.addObject("listRem", listRem);
		return mv;
	}

	@RequestMapping(path = "/creer", method = RequestMethod.POST)
	@Secured("ROLE_ADMINISTRATEUR")
	public String submitForm(@RequestParam("periode") String periode, @RequestParam("remuneration") String matricule,
			@RequestParam("prime") String prime) {
		BulletinSalaire bulletin = new BulletinSalaire();
		String[] per = periode.split("/");
		String[] perDetaille = per[0].split("-");
		bulletin.setPeriode(perRepo.findByDateDebut(LocalDate.of(Integer.parseInt(perDetaille[0]),
				Integer.parseInt(perDetaille[1]), Integer.parseInt(perDetaille[2]))));
		bulletin.setRemunerationEmploye(remRepo.findOneByMatricule(matricule));
		bulletin.setPrimeExceptionnelle(new BigDecimal(prime));
		bulletin.setDateHeure(ZonedDateTime.now());
		bulRepo.save(bulletin);
		return "redirect:/mvc/bulletins/lister";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	@Secured({ "ROLE_UTILISATEUR", "ROLE_ADMINISTRATEUR" })
	public ModelAndView afficherPageLister() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bulletins/listerBulletins");
		mv.addObject("listBul", bulSer.lister());
		return mv;
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@Secured({ "ROLE_UTILISATEUR", "ROLE_ADMINISTRATEUR" })
	public ModelAndView visualiserBulletin(@PathVariable Integer id) {
		Optional<BulletinSalaire> bulletin = Optional.ofNullable(bulRepo.findOne(id));
		if (bulletin.isPresent()) {
			ResultatCalculRemuneration result = bulSer.findOneById(id);
			ModelAndView mv = new ModelAndView();
			mv.setViewName("bulletins/visualiserBulletin");
			mv.addObject("result", result);
			mv.addObject("bul", bulletin.get());
			BigDecimal totalRetenueMontantSalarial = bulletin.get().getRemunerationEmploye().getProfilRemuneration()
					.getCotisationsNonImposables().stream().filter(c -> c.getTauxSalarial() != null)
					.map(c -> c.getTauxSalarial().multiply(new BigDecimal(result.getSalaireBrut())))
					.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

			BigDecimal totalRetenueCotisationsPatronales = bulletin.get().getRemunerationEmploye()
					.getProfilRemuneration().getCotisationsNonImposables().stream()
					.filter(c -> c.getTauxPatronal() != null)
					.map(c -> c.getTauxPatronal().multiply(new BigDecimal(result.getSalaireBrut())))
					.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

			mv.addObject("totalRetenueMontantSalarial", totalRetenueMontantSalarial);
			mv.addObject("totalRetenueCotisationsPatronales", totalRetenueCotisationsPatronales);

			return mv;
		} else {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("bulletins/listerBulletins");
			return mv;
		}

	}

}
