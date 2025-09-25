package com.blueinnovation.demo.controller;

import com.blueinnovation.demo.model.Sedia;
import com.blueinnovation.demo.repository.SediaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/sedie")
public class SediaController {

    private final SediaRepository repo;

    public SediaController(SediaRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Sedia crea(@RequestBody Sedia sedia) {
        repo.findByCodice(sedia.getCodice()).ifPresent(s -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Una sedia con questo codice esiste già");
        });
        return repo.save(sedia);
    }

    @GetMapping("/{id}")
    public Sedia get(@PathVariable("id") Long id) {
        return repo.findById(id)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sedia non trovata"));
    }

    @DeleteMapping("/{id}")
    public void elimina(@PathVariable("id") Long id) {
        repo.deleteById(id);
    }
}
