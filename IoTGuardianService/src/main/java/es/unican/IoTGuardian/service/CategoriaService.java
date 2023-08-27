package es.unican.IoTGuardian.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.unican.IoTGuardian.domain.Categoria;
import es.unican.IoTGuardian.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public List<Categoria> categorias() {
		return repository.findAll();
	}
	
	public Categoria buscaCategoria(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public Categoria buscaCategoriaPorNombre(String nombre) {
		return repository.findByNombre(nombre);
	}
}
