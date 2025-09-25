package com.blueinnovation.demo.repository;

import com.blueinnovation.demo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
}
