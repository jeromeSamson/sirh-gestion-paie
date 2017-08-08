package dev.paie.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Grade;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.util.PaieUtils;

@Service
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {

	@Autowired
	private PaieUtils paieUtil;

	@Override
	public ResultatCalculRemuneration calculer(BulletinSalaire bulletin) {
		ResultatCalculRemuneration result = new ResultatCalculRemuneration();

		Grade grade = bulletin.getRemunerationEmploye().getGrade();

		result.setSalaireDeBase(paieUtil.formaterBigDecimal(grade.getNbHeuresBase().multiply(grade.getTauxBase())));

		BigDecimal salaireBrut = new BigDecimal(result.getSalaireDeBase()).add(bulletin.getPrimeExceptionnelle());
		result.setSalaireBrut(paieUtil.formaterBigDecimal(salaireBrut));

		BigDecimal totalRetenueSalariale = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsNonImposables().stream().filter(c -> c.getTauxSalarial() != null)
				.map(c -> c.getTauxSalarial().multiply(new BigDecimal(result.getSalaireBrut()))).reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);

		result.setTotalRetenueSalarial(paieUtil.formaterBigDecimal(totalRetenueSalariale));

		BigDecimal totalCotisationPatronale = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsNonImposables().stream().filter(c -> c.getTauxPatronal() != null)
				.map(c -> c.getTauxPatronal().multiply(new BigDecimal(result.getSalaireBrut()))).reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);

		result.setTotalCotisationsPatronales(paieUtil.formaterBigDecimal(totalCotisationPatronale));

		result.setNetImposable(paieUtil.formaterBigDecimal(
				new BigDecimal(result.getSalaireBrut()).subtract(new BigDecimal(result.getTotalRetenueSalarial()))));

		BigDecimal totalRetenueSalarialeImposable = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsImposables().stream().filter(c -> c.getTauxSalarial() != null)
				.map(c -> c.getTauxSalarial().multiply(new BigDecimal(result.getSalaireBrut()))).reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);

		result.setNetAPayer(paieUtil
				.formaterBigDecimal(new BigDecimal(result.getNetImposable()).subtract(totalRetenueSalarialeImposable)));

		return result;

	}

}
