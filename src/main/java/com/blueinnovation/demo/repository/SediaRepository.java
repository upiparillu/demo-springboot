package com.blueinnovation.demo.repository;

import com.blueinnovation.demo.model.Sedia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SediaRepository extends JpaRepository<Sedia, Long> {
	Optional<Sedia> findByCodice(String codice);
}
