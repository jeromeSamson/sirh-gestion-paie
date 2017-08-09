package dev.paie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;

@Service
public class CotisationServiceJpa implements CotisationService {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void sauvegarder(Cotisation nouvelleCotisation) {
		em.persist(nouvelleCotisation);

	}

	@Override
	@Transactional
	public void mettreAJour(Cotisation cotisation) {
		Cotisation co = em.createNamedQuery("cotisation.findById", Cotisation.class)
				.setParameter("id", cotisation.getId()).getSingleResult();
		co.setCode(cotisation.getCode());
		co.setLibelle(cotisation.getLibelle());
		co.setTauxPatronal(cotisation.getTauxPatronal());
		co.setTauxSalarial(cotisation.getTauxSalarial());
		em.merge(co);

	}

	@Override
	public List<Cotisation> lister() {

		return em.createNamedQuery("cotisation.findAll", Cotisation.class).getResultList();
	}

}
