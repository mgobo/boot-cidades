package com.boot.cidades.repositories;

public interface CidadesCustomImpl {
	
	String dynamic = "select distinct count(%s) from Cidades c ";
	Long totalCidadesPorDataSet(String columnName);
	double distance(double lat1, double lon1, double lat2, double lon2);
	
}
