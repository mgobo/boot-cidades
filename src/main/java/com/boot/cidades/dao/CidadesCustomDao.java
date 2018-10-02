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
		TypedQuery<Long> tQuery = em.createQuery(String.format(dynamic, "c."+columnName),Long.class);		
		Long result = tQuery.getSingleResult();
		if(result != null) {
			return result;			
		}				
		return 0l;
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
