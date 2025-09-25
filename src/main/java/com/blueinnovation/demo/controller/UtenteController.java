package com.blueinnovation.demo.controller;

import com.blueinnovation.demo.model.Utente;
import com.blueinnovation.demo.repository.UtenteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    private final UtenteRepository repo;

    public UtenteController(UtenteRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Utente crea(@RequestBody Utente utente) {
        return repo.save(utente);
    }

    @GetMapping("/{id}")
    public Utente get(@PathVariable("id") Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
    }

    @DeleteMapping("/{id}")
    public void elimina(@PathVariable("id") Long id) {
        repo.deleteById(id);
    }
}
