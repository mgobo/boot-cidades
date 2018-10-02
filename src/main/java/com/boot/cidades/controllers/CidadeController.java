package com.boot.cidades.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boot.cidades.model.Cidades;
import com.boot.cidades.repositories.CidadeRepository;
import com.boot.cidades.repositories.CidadesCustomImpl;
import com.google.gson.Gson;

@RestController
@RequestMapping(path="/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CidadesCustomImpl cidadesCustomDao;
	
	private final static String URI_CIDADES = "cidades";
	
	@GetMapping(path="/all")
	public List<Cidades> findAll(){		
		List<Cidades> cidades = cidadeRepository.findAll();
		cidades.sort((c1,c2)-> c1.getIbgeCidade().compareTo(c2.getIbgeCidade()));
		return cidades;
	}
	
	@GetMapping(path="/all/total")
	public int allRecords(){		
		return cidadeRepository.findAll().size();
	}
	
	@GetMapping(path="/capital")
	public List<Cidades> cidadesCapital(){
		return cidadeRepository.cidadesCapital();
	}
	
	@GetMapping(path="/total/{uf}")
	public int contarCidadesPorEstado(@PathVariable("uf")String uf){
		return cidadeRepository.contarCidadesPorEstado(uf);
	}
	
	@GetMapping(path="/ibge/{ibge}")
	public Cidades cidadesPorIbge(@PathVariable("ibge")String ibgeCidade){
		return cidadeRepository.cidadesPorIbge(new Long(ibgeCidade));
	}
	
	@GetMapping(path="/uf/{uf}")
	public List<Cidades> cidadesPorEstado(@PathVariable("uf")String uf){
		return cidadeRepository.cidadesPorEstado(uf);
	}
	
	@GetMapping(path="/all/data/{data}")
	public Long totalCidadesPorDataSet(@PathVariable("data")String data){
		Long total = this.cidadesCustomDao.totalCidadesPorDataSet(data);
		return total;
	}
	
	@GetMapping(path="/distance")
	public Double distanceCities() {
		List<Cidades> cidades = this.cidadeRepository.findAll();		
		cidades.sort((c1,c2)-> c1.getLat().compareTo(c2.getLat()));		
		Cidades cid1 = cidades.get(0);				
		
		cidades.sort((c1,c2)-> c1.getLon().compareTo(c2.getLon()));
		Cidades cid2 = cidades.get(0);
		
		Double lat1 = cid1.getLat();
		Double lon1 = cid1.getLat();
		
		Double lat2 = cid2.getLat();
		Double lon2 = cid2.getLat();		
		return this.cidadesCustomDao.distance(lat1, lon1, lat2, lon2);
	}		
	
	@PostMapping(path="/save", consumes= {"application/json"}, produces= {"application/json"})
	public String save(@RequestBody String cidade) {
		Gson gsonCidade = new Gson();
		Cidades cidades = gsonCidade.fromJson(cidade, Cidades.class);
		
		this.cidadeRepository.saveAndFlush(cidades);
		return "cidades/all";
	}
	
	@GetMapping(path="/estados")
	public List<String> estados(){
		return this.cidadeRepository.estados();
	}	
	
	@GetMapping(path="/allView")
	public ModelAndView allView() {		
		List<Cidades> cidades =  this.cidadeRepository.findAll();
		cidades.sort((c1,c2)-> c1.getIbgeCidade().compareTo(c2.getIbgeCidade()));
		
		Map<String,List<Cidades>> dataset = new HashMap<>();
		dataset.put("cidades", cidades);
		return new ModelAndView("cidades/list","cidades", cidades);
	}		
	
	@GetMapping(path="/new")
	public ModelAndView newCity(@ModelAttribute Cidades cidade) {			
		Map<String, Cidades> data = new HashMap<>();
		data.put("cidade", cidade);
		return new ModelAndView(URI_CIDADES+"/form",data);
	}
	
	@PostMapping(value="salvar", params = {"form"})
	public ModelAndView salvar(@Valid Cidades cidades, BindingResult result, RedirectAttributes redirect) {
		this.cidadeRepository.saveAndFlush(cidades);
		redirect.addFlashAttribute("globalMessage","Cidade "+cidades.getName()+" criada com sucesso...");
		return new ModelAndView("redirect:/"+URI_CIDADES+"/allView");
	}	
	
	@GetMapping(path="/detail/{ibge}")
	public ModelAndView detail(@PathVariable("ibge")String ibge, RedirectAttributes redirect) {			
		Map<String, Cidades> data = new HashMap<>();
		Cidades cidades = this.cidadeRepository.cidadesPorIbge(new Long(ibge));
		data.put("cidade", cidades);
		return new ModelAndView(URI_CIDADES+"/details",data);
	}
	
	@GetMapping(path="/remove/{ibge}")
	public ModelAndView remover(@PathVariable("ibge")String ibge, RedirectAttributes redirect) {
		Cidades cidade = this.cidadeRepository.findOne(new Long(ibge));
		this.cidadeRepository.delete(cidade);
		return new ModelAndView("redirect:/"+URI_CIDADES+"/allView");
	}
}
