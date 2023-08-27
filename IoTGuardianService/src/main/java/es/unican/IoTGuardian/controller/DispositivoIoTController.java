package es.unican.IoTGuardian.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.unican.IoTGuardian.domain.DispositivoIoT;
import es.unican.IoTGuardian.service.CategoriaService;
import es.unican.IoTGuardian.service.DispositivoIoTService;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoIoTController {
	
	@Autowired
	private DispositivoIoTService dispositivoIoTService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public List<DispositivoIoT> getDispositivosIoT(@RequestParam(value="categoria", required = false) String categoria) {
		
		List<DispositivoIoT> disps = new LinkedList<DispositivoIoT>();
		
		if (categoria != null) {
			disps = dispositivoIoTService.dispositivosIoTPorCategoria(categoriaService.buscaCategoriaPorNombre(categoria));
		} else {
			disps = dispositivoIoTService.dispositivosIoT();
		}
		
		return disps;
	}
	
	@GetMapping("/{nombre}")
	public ResponseEntity<DispositivoIoT> getAplicacion(@PathVariable String nombre) {
		ResponseEntity<DispositivoIoT> result;
		DispositivoIoT disp = dispositivoIoTService.buscaDispositivoIoT(nombre);
		
		if (disp == null) {
			result = ResponseEntity.notFound().build();
		} else {
			result = ResponseEntity.ok(disp);
		}
		return result;
	}
	
}
