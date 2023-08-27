package es.unican.IoTGuardian.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.unican.IoTGuardian.domain.Categoria;
import es.unican.IoTGuardian.domain.DispositivoIoT;
import es.unican.IoTGuardian.repository.DispositivoIoTRepository;

@Service
public class DispositivoIoTService {
	
	@Autowired
	private DispositivoIoTRepository repository;
	
	public List<DispositivoIoT> dispositivosIoT() {
		return repository.findAll();
	}
	
	public List<DispositivoIoT> dispositivosIoTPorCategoria(Categoria categoria) {
		return repository.findByCategoria(categoria);
	}
	
	public DispositivoIoT buscaDispositivoIoT(String nombre) {
		return repository.findByNombre(nombre);
	}
}
