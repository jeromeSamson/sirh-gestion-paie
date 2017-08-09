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
import dev.paie.entite.Cotisation;
import dev.paie.spring.JpaConfig;

@ContextConfiguration(classes = { JpaConfig.class, ServicesConfig.class })
@RunWith(SpringRunner.class)
public class CotisationServiceJpaTest {
	@Autowired
	private CotisationService cotisationService;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder une nouvelle cotisation
		Cotisation co = new Cotisation();
		co.setCode("co");
		co.setLibelle("Test");
		co.setTauxPatronal(new BigDecimal("10"));
		co.setTauxSalarial(new BigDecimal("10"));
		cotisationService.sauvegarder(co);
		// TODO vérifier qu'il est possible de récupérer la nouvelle cotisation
		// via la méthode lister
		List<Cotisation> listCo = cotisationService.lister();

		Stream.of("Test", "co", "etc").forEach(
				code -> assertThat(listCo.stream().filter(c -> c.getCode().equals(code)).findAny().isPresent()));

		// TODO modifier une cotisation
		co.setId(listCo.get(0).getId());
		co.setCode("Test");
		co.setLibelle("co");
		cotisationService.mettreAJour(co);
		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode lister
		List<Cotisation> listCo2 = cotisationService.lister();

		Stream.of("Test", "co", "etc").forEach(
				code -> assertThat(listCo2.stream().filter(c -> c.getCode().equals(code)).findAny().isPresent()));

		Stream.of("Test", "co", "etc").forEach(
				libe -> assertThat(listCo2.stream().filter(c -> c.getLibelle().equals(libe)).findAny().isPresent()));
	}
}
