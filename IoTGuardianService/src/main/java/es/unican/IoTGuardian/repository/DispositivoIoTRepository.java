package es.unican.IoTGuardian.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.unican.IoTGuardian.domain.Categoria;
import es.unican.IoTGuardian.domain.DispositivoIoT;

public interface DispositivoIoTRepository extends JpaRepository<DispositivoIoT, Long> {
	
	public List<DispositivoIoT> findByCategoria(Categoria categoria);

	public DispositivoIoT findByNombre(String nombre);
}
