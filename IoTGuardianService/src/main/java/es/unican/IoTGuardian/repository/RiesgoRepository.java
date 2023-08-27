package es.unican.IoTGuardian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.unican.IoTGuardian.domain.Riesgo;

public interface RiesgoRepository extends JpaRepository<Riesgo, Long> {

}
