package com.blueinnovation.demo.service;

import com.blueinnovation.demo.model.Sedia;
import com.blueinnovation.demo.model.Utente;
import com.blueinnovation.demo.model.UtenteSeduto;
import com.blueinnovation.demo.repository.UtenteSedutoRepository;
import com.blueinnovation.demo.service.UtenteSedutoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UtenteSedutoService {

    private final UtenteSedutoRepository repository;

    public UtenteSedutoService(UtenteSedutoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UtenteSeduto assegnaSedia(Utente utente, Sedia sedia) {
        repository.findByUtente(utente).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "L'utente è già seduto su un'altra sedia");
        });
        repository.findBySedia(sedia).ifPresent(s -> {
        	throw new ResponseStatusException(HttpStatus.CONFLICT, "L'utente è già seduto su un'altra sedia");
        });
        return repository.save(new UtenteSeduto(utente, sedia));
    }

    @Transactional
    public void rimuovi(Long id) {
        repository.deleteById(id);
    }
}
