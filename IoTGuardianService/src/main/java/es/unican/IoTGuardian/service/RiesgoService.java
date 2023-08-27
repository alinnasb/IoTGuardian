package es.unican.IoTGuardian.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.unican.IoTGuardian.domain.Riesgo;
import es.unican.IoTGuardian.repository.RiesgoRepository;

@Service
public class RiesgoService {
	
	@Autowired
	private RiesgoRepository repository;
	
	public List<Riesgo> riesgos() {
		return repository.findAll();
	}
	
	public Riesgo buscaRiesgo(Long id) {
		return repository.findById(id).orElse(null);
	}
}
