package es.unican.IoTGuardian.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Control {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String descripcion;
	
	@ManyToMany(mappedBy = "controles")
	@JsonBackReference
	private List<Riesgo> mitigaRiesgos;

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

	public List<Riesgo> getMitigaRiesgos() {
		return mitigaRiesgos;
	}

	public void setMitigaRiesgos(List<Riesgo> mitigaRiesgos) {
		this.mitigaRiesgos = mitigaRiesgos;
	}
}
