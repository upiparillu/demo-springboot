package com.blueinnovation.demo.model;

import jakarta.persistence.*;

@Entity
public class Sedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codice;

    public Sedia() {}

    public Sedia(String codice) {
        this.codice = codice;
    }

    public Long getId() { return id; }
    public String getCodice() { return codice; }
    public void setCodice(String codice) { this.codice = codice; }
}
