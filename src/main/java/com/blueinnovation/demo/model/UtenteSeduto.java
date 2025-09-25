package com.blueinnovation.demo.model;

import jakarta.persistence.*;

@Entity
public class UtenteSeduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "utente_id", unique = true)
    private Utente utente;


    @OneToOne
    @JoinColumn(name = "sedia_id", unique = true)
    private Sedia sedia;

    public UtenteSeduto() {
    }

    public UtenteSeduto(Utente utente, Sedia sedia) {
        this.utente = utente;
        this.sedia = sedia;
    }

    public Long getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }

    public Sedia getSedia() {
        return sedia;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setSedia(Sedia sedia) {
        this.sedia = sedia;
    }
}
