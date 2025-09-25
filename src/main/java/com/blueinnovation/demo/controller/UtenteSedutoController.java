package com.blueinnovation.demo.controller;

import com.blueinnovation.demo.dto.SedutaRequest;
import com.blueinnovation.demo.model.Sedia;
import com.blueinnovation.demo.model.Utente;
import com.blueinnovation.demo.model.UtenteSeduto;
import com.blueinnovation.demo.repository.SediaRepository;
import com.blueinnovation.demo.repository.UtenteRepository;
import com.blueinnovation.demo.repository.UtenteSedutoRepository;
import com.blueinnovation.demo.service.UtenteSedutoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/seduti")
public class UtenteSedutoController {

    private final UtenteSedutoService service;
    private final UtenteRepository utenteRepo;
    private final SediaRepository sediaRepo;
    private final UtenteSedutoRepository sedutoRepo;

    public UtenteSedutoController(UtenteSedutoService service, UtenteRepository utenteRepo, SediaRepository sediaRepo, UtenteSedutoRepository sedutoRepo) {
        this.service = service;
        this.utenteRepo = utenteRepo;
        this.sediaRepo = sediaRepo;
        this.sedutoRepo = sedutoRepo;
    }

    @PostMapping
    public UtenteSeduto crea(@RequestBody SedutaRequest sedutaRequest) {
        Utente utente = utenteRepo.findById(sedutaRequest.getUtenteId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
        Sedia sedia = sediaRepo.findByCodice(sedutaRequest.getCodiceSedia()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sedia non trovata"));
        return service.assegnaSedia(utente, sedia);
    }

    @GetMapping("/{id}")
    public UtenteSeduto get(@PathVariable("id") Long id) {
        return sedutoRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associazione non trovata"));
    }

    @DeleteMapping("/{id}")
    public void rimuovi(@PathVariable("id") Long id) {
        service.rimuovi(id);
    }

    @GetMapping("/by-sedia/{codiceSedia}")
    public UtenteSeduto getBySedia(@PathVariable("codiceSedia") String codiceSedia) {
        Sedia sedia = sediaRepo.findByCodice(codiceSedia).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sedia non trovata"));
        return sedutoRepo.findBySedia(sedia).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nessuno è seduto su questa sedia"));
    }
}
