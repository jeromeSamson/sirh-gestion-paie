package dev.paie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.paie.entite.BulletinSalaire;

public interface BulletinSalaireRepository extends JpaRepository<BulletinSalaire, Integer> {

	BulletinSalaire findOneById(Integer id);

}
