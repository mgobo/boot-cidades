package com.boot.cidades.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cidades")
public class Cidades {

	@Id
	private Long ibgeCidade;
	
	@Column
	private String uf;
	
	@Column
	private String name;
	
	@Column
	private boolean capital;
	
	@Column
	private Double lon;
	
	@Column
	private Double lat;
	
	@Column
	private String no_accents;
	
	@Column
	private String alternative_names;
	
	@Column
	private String microregion;

	@Column
	private String mesoregion;
	
	public Long getIbgeCidade() {
		return ibgeCidade;
	}

	public void setIbgeCidade(Long ibgeCidade) {
		this.ibgeCidade = ibgeCidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getCapital() {
		return capital;
	}

	public void setCapital(boolean capital) {
		this.capital = capital;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public String getNo_accents() {
		return no_accents;
	}

	public void setNo_accents(String no_accents) {
		this.no_accents = no_accents;
	}

	public String getAlternative_names() {
		return alternative_names;
	}

	public void setAlternative_names(String alternative_names) {
		this.alternative_names = alternative_names;
	}

	public String getMicroregion() {
		return microregion;
	}

	public void setMicroregion(String microregion) {
		this.microregion = microregion;
	}

	public String getMesoregion() {
		return mesoregion;
	}

	public void setMesoregion(String mesoregion) {
		this.mesoregion = mesoregion;
	}				
}
