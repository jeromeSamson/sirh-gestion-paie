package dev.paie.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.ServicesConfig;
import dev.paie.entite.Avantage;

@ContextConfiguration(classes = ServicesConfig.class)
@RunWith(SpringRunner.class)
public class AvantageRepositoryTest {
	@Autowired
	private AvantageRepository avantageRepository;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// TODO sauvegarder un nouvel avantage
		Avantage av = new Avantage();
		av.setCode("Avantage 1");
		av.setMontant(new BigDecimal("10"));
		av.setNom("Nom de l'avantage");

		avantageRepository.save(av);
		// TODO vérifier qu'il est possible de récupérer le nouvel avantage via
		// la méthode findOne
		List<Avantage> listAv = avantageRepository.findAll();
		av.setId(listAv.stream().filter(c -> c.getCode().equals("Avantage 1")).findAny().get().getId());
		Avantage avOne = avantageRepository.findOne(av.getId());
		assertThat(avOne.getCode()).isEqualTo(av.getCode());
		// TODO modifier un avantage
		avOne.setCode("Avantage 2");
		avantageRepository.save(avOne);
		// TODO vérifier que les modifications sont bien prises en compte via la
		// méthode findOne

		av = avantageRepository.findOneByCode("Avantage 2");
		assertThat(avOne.getCode()).isEqualTo(av.getCode());
	}
}
