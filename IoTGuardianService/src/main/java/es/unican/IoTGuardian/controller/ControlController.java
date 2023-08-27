package es.unican.IoTGuardian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.unican.IoTGuardian.domain.Control;
import es.unican.IoTGuardian.service.ControlService;

@RestController
@RequestMapping("/controles")
public class ControlController {
	
	@Autowired
	private ControlService controlService;
	
	@GetMapping
	public List<Control> getControles() {
		return controlService.controles();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Control> getControl(@PathVariable Long id) {
		ResponseEntity<Control> result;
		Control control = controlService.buscaControl(id);
		
		if (control == null) {
			result = ResponseEntity.notFound().build();
		} else {
			result = ResponseEntity.ok(control);
		}
		return result;
	}
	
}
