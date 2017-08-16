package dev.paie.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.ServicesConfig;
import dev.paie.entite.Grade;
import dev.paie.spring.DataSourceMySQLConfig;

@ContextConfiguration(classes = { DataSourceMySQLConfig.class, ServicesConfig.class })
@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {

	@Autowired
	private GradeService gradeService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder un nouveau grade
		Grade grade = new Grade();
		grade.setCode("Test");
		grade.setNbHeuresBase(new BigDecimal("10"));
		grade.setTauxBase(new BigDecimal("12"));
		gradeService.sauvegarder(grade);
		// TODO vérifier qu'il est possible de récupérer le nouveau grade via la
		// méthode lister

		List<Grade> listGrade = gradeService.lister();

		Stream.of("Test", "test", "Plus de test").forEach(code -> assertThat(
				gradeService.lister().stream().filter(c -> c.getCode().equals(code)).findAny().isPresent()));

		// TODO modifier un grade
		grade.setId(listGrade.get(0).getId());
		grade.setCode("Plus de test");
		grade.setNbHeuresBase(new BigDecimal("0"));
		grade.setTauxBase(new BigDecimal("2"));
		gradeService.mettreAJour(grade);
		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		listGrade = gradeService.lister();
		assertThat(listGrade.get(0).getCode()).isEqualToIgnoringCase("Plus de test");
	}
}
