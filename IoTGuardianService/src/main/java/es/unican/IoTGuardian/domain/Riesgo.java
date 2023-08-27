package es.unican.IoTGuardian.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity
public class Riesgo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String descripcion;
	
	@ManyToMany
	@JoinTable(name = "control_x_riesgo",
				joinColumns = @JoinColumn(name = "fk_riesgo"),
				inverseJoinColumns = @JoinColumn(name = "fk_control"))
	@JsonManagedReference
	private List<Control> controles;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<Control> getControles() {
		return controles;
	}
	
	public void setControles(List<Control> controles) {
		this.controles = controles;
	}
}
