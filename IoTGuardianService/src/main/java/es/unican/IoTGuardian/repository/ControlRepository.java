package es.unican.IoTGuardian.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.unican.IoTGuardian.domain.Control;

public interface ControlRepository extends JpaRepository<Control, Long> {

}
