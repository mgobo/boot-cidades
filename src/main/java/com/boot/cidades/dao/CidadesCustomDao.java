package com.boot.cidades.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.boot.cidades.repositories.CidadesCustomImpl;

@Repository
public class CidadesCustomDao implements CidadesCustomImpl{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Long totalCidadesPorDataSet(String columnName) {
		String[] sQuery = columnName.split("&");
		String query    = "";
		if(sQuery.length == 1) {
			query = query(sQuery);
		}else {
			query = query(sQuery);
		}		
		TypedQuery<Long> tQuery = em.createQuery(query,Long.class);		
		Long result = tQuery.getSingleResult();
		if(result != null) {
			return result;			
		}				
		return 0l;
	}	

	public String query(String[] query) {
		String replace = "";
		boolean first = false;
		for(String q : query) {			
			String[] scn = q.split("=");
			switch(scn[0]) {
				case "ibgeCidade":
					replace = replace + (!first ? "c.ibgeCidade = "+scn[1] : " AND c.ibgeCidade = "+scn[1]);						
					break;
				case "uf":
					replace = replace + (!first ? "c.uf = '"+scn[1]+"'" : " AND c.uf = '"+scn[1]+"'");
					break;						
				case "name":
					replace = replace + (!first ? "c.name = '"+scn[1]+"'" : " AND c.name = '"+scn[1]+"'");
				break;					
				case "capital":
					replace = replace + (!first ? "c.capital = "+scn[1]+"" : " AND c.capital = '"+scn[1]+"'");
					break;					
				case "lon":
					replace = replace + (!first ? "c.lon = "+scn[1]+"" : " AND c.lon = '"+scn[1]+"'");
				break;					
				case "lat":
					replace = replace + (!first ? "c.lat = "+scn[1]+"" : " AND c.lat = '"+scn[1]+"'");
				break;					
				case "no_accents":
					replace = replace + (!first ? "c.no_accents = '"+scn[1]+"'" : " AND c.no_accents = '"+scn[1]+"'");
					break;						
				case "alternative_names":
					replace = replace + (!first ? "c.alternative_names = '"+scn[1]+"'" : " AND c.alternative_names = '"+scn[1]+"'");
					break;
				case "microregion":
					replace = replace + (!first ? "c.microregion = '"+scn[1]+"'" : " AND c.microregion = '"+scn[1]+"'");
					break;
				case "mesoregion":
					replace = replace + (!first ? "c.mesoregion = '"+scn[1]+"'" : " AND c.mesoregion = '"+scn[1]+"'");
					break;
				default : break;
			}
			first = true;
		}
		return String.format(dynamic, replace);
	}
	
	@Override
	public double distance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;		
		dist = dist * 1.609344;		
		return (dist);
	}
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}	
}
