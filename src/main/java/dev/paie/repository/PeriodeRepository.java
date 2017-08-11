package dev.paie.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.paie.entite.Periode;

public interface PeriodeRepository extends JpaRepository<Periode, Integer> {

	@Query("Select p from Periode p where p.dateDebut=:dateDebut")
	Periode findByDateDebut(@Param("dateDebut") LocalDate dateDebut);

}
