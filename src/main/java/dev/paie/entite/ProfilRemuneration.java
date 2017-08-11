package dev.paie.entite;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class ProfilRemuneration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String code;

	@ManyToMany
	@JoinTable(name = "ProfilRemun_CotisationImpo", joinColumns = @JoinColumn(name = "ProfilRenum_Id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "CotisationImpo_Id", referencedColumnName = "id"))
	private List<Cotisation> cotisationsImposables;

	@ManyToMany
	@JoinTable(name = "ProfilRemun_CotisationNonImpo", joinColumns = @JoinColumn(name = "ProfilRenum_Id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "CotisationNonImpo_Id", referencedColumnName = "id"))
	private List<Cotisation> cotisationsNonImposables;

	@ManyToMany
	@JoinTable(name = "Avantage_Cotisation", joinColumns = @JoinColumn(name = "ProfilRenum_Id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "Avantage_Id", referencedColumnName = "id"))
	private List<Avantage> avantages;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Cotisation> getCotisationsNonImposables() {
		return cotisationsNonImposables;
	}

	public void setCotisationsNonImposables(List<Cotisation> cotisationsNonImposables) {
		this.cotisationsNonImposables = cotisationsNonImposables;
	}

	public List<Cotisation> getCotisationsImposables() {
		return cotisationsImposables;
	}

	public void setCotisationsImposables(List<Cotisation> cotisationsImposables) {
		this.cotisationsImposables = cotisationsImposables;
	}

	public List<Avantage> getAvantages() {
		return avantages;
	}

	public void setAvantages(List<Avantage> avantages) {
		this.avantages = avantages;
	}

}
