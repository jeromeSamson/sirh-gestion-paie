package dev.paie.web.controller;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.ProfilRemuneration;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.ProfilRemunerationRepository;
import dev.paie.repository.RemunerationEmployeRepository;

@Controller
@RequestMapping("/employes")
public class RemunerationEmployeController {
	@Autowired
	private EntrepriseRepository entrepriseRepo;

	@Autowired
	private ProfilRemunerationRepository profilRepo;

	@Autowired
	private GradeRepository gradeRepo;

	@Autowired
	private RemunerationEmployeRepository remEmpRepo;

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	@Secured("ROLE_ADMINISTRATEUR")
	public String creerEmploye(Model mv) {
		List<Entreprise> listEntre = entrepriseRepo.findAll();
		List<ProfilRemuneration> listProfil = profilRepo.findAll();
		List<Grade> listGrade = gradeRepo.findAll();
		FormulaireViewAjouterEmployer remEmp = new FormulaireViewAjouterEmployer();
		mv.addAttribute("remEmp", remEmp);
		mv.addAttribute("listEntreprise", listEntre);
		mv.addAttribute("listProfil", listProfil);
		mv.addAttribute("listGrade", listGrade);

		return "employes/creerEmploye";
	}

	@RequestMapping(path = "/creer", method = RequestMethod.POST)
	@Secured("ROLE_ADMINISTRATEUR")
	public String submitForm(@ModelAttribute("remEmp") FormulaireViewAjouterEmployer form) {
		RemunerationEmploye remEmp = new RemunerationEmploye();
		remEmp.setDateHeure(ZonedDateTime.now());
		remEmp.setEntreprise(entrepriseRepo.findOneByDenomination(form.getEntreprise()));
		remEmp.setGrade(gradeRepo.findOneByCode(form.getGrade()));
		remEmp.setMatricule(form.getMatricule());
		remEmp.setProfilRemuneration(profilRepo.findOneByCode(form.getProfil()));
		remEmpRepo.save(remEmp);
		return "redirect:/mvc/employes/lister";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	@Secured({ "ROLE_UTILISATEUR", "ROLE_ADMINISTRATEUR" })
	public ModelAndView afficherPageLister() {
		ModelAndView mv = new ModelAndView();
		List<RemunerationEmploye> listRem = remEmpRepo.findAll();
		mv.setViewName("employes/listerEmployer");
		mv.addObject("listRem", listRem);
		return mv;
	}
}