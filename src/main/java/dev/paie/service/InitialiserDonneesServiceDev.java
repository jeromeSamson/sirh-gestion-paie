package dev.paie.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;
import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.Periode;
import dev.paie.entite.ProfilRemuneration;
import dev.paie.entite.Utilisateur;
import dev.paie.entite.Utilisateur.ROLES;

@Service
public class InitialiserDonneesServiceDev implements InitialiserDonneesService {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void initialiser() {
		Stream.of(Grade.class, Cotisation.class, Entreprise.class, ProfilRemuneration.class)
				.forEach(t -> context.getBeansOfType(t).values().stream().forEach(p -> em.persist(p)));
		IntStream.range(1, 13).forEach(mois -> {
			Periode periode = new Periode();
			periode.setDateDebut(LocalDate.of(LocalDate.now().getYear(), mois, 01));
			periode.setDateFin(periode.getDateDebut().with(TemporalAdjusters.lastDayOfMonth()));
			em.persist(periode);
		});

		Utilisateur user = new Utilisateur();
		user.setEstActif(true);
		user.setMotDePasse(passwordEncoder.encode("admin"));
		user.setNomUtilisateur("admin");
		user.setRole(ROLES.ROLE_ADMINISTRATEUR);
		em.persist(user);

		user = new Utilisateur();
		user.setEstActif(true);
		user.setMotDePasse(passwordEncoder.encode("user"));
		user.setNomUtilisateur("user");
		user.setRole(ROLES.ROLE_UTILISATEUR);
		em.persist(user);

	}

}