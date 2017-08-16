package dev.paie.service;

import java.util.Map;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.ResultatCalculRemuneration;

public interface BulletinSalaireService {
	public Map<BulletinSalaire, ResultatCalculRemuneration> lister();

	ResultatCalculRemuneration findOneById(Integer id);
}
