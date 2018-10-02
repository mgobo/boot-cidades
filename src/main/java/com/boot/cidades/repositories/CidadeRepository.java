package com.boot.cidades.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boot.cidades.model.Cidades;

public interface CidadeRepository extends JpaRepository<Cidades, Long> {

	@Query(value="select c from Cidades c order by c.ibgeCidade")
	List<Cidades> all();
	
	@Query(value="select c from Cidades c where c.capital = true order by c.name")
	List<Cidades> cidadesCapital();
	
	@Query(value="select count(c) from Cidades c where c.uf = :uf")
	int contarCidadesPorEstado(@Param("uf") String uf);
	
	@Query(value="select distinct(c.uf) from Cidades c order by c.uf")
	List<String> estados();
	
	@Query(value="select c from Cidades c where c.ibgeCidade = :ibgeCidade")
	Cidades cidadesPorIbge(@Param("ibgeCidade") Long ibgeCidade);
	
	@Query(value="select c from Cidades c where c.uf LIKE %:uf%")
	List<Cidades> cidadesPorEstadoLike(@Param("uf") String uf);
	
	@Query(value="select c from Cidades c where c.uf = :uf order by c.name")
	List<Cidades> cidadesPorEstado(@Param("uf") String uf);						
}
