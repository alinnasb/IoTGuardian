package es.unican.IoTGuardian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.unican.IoTGuardian.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Categoria findByNombre(String nombre);

}
