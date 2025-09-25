package com.blueinnovation.demo.repository;

import com.blueinnovation.demo.model.UtenteSeduto;
import com.blueinnovation.demo.model.Utente;
import com.blueinnovation.demo.model.Sedia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtenteSedutoRepository extends JpaRepository<UtenteSeduto, Long> {
	Optional<UtenteSeduto> findByUtente(Utente utente);
    Optional<UtenteSeduto> findBySedia(Sedia sedia);
}
