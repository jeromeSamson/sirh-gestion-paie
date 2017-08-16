package dev.paie.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.repository.BulletinSalaireRepository;

@Service
public class BulletinSalaireServiceSimple implements BulletinSalaireService {

	@Autowired
	private BulletinSalaireRepository bulRepo;

	@Autowired
	private CalculerRemunerationServiceSimple calcul;

	@Override
	@Transactional
	public Map<BulletinSalaire, ResultatCalculRemuneration> lister() {
		Map<BulletinSalaire, ResultatCalculRemuneration> list = new HashMap<>();
		bulRepo.findAll().stream().forEach(b -> list.put(b, calcul.calculer(b)));
		return list;
	}

	@Override
	@Transactional
	public ResultatCalculRemuneration findOneById(Integer id) {
		return calcul.calculer(bulRepo.findOneById(id));
	}

}
