package com.blueinnovation.demo;

import com.blueinnovation.demo.model.Utente;
import com.blueinnovation.demo.model.Sedia;
import com.blueinnovation.demo.model.UtenteSeduto;
import com.blueinnovation.demo.repository.UtenteRepository;
import com.blueinnovation.demo.repository.SediaRepository;
import com.blueinnovation.demo.repository.UtenteSedutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoBlueInnovationApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UtenteRepository utenteRepo;

    @Autowired
    private SediaRepository sediaRepo;

    @Autowired
    private UtenteSedutoRepository sedutoRepo;

    @BeforeEach
    void cleanDb() {
        sedutoRepo.deleteAll();
        utenteRepo.deleteAll();
        sediaRepo.deleteAll();
    }

    @Test
    void testUtenteCrud() throws Exception {
        // POST /utenti
    	String nome = "Giuseppe";
        Utente utente = new Utente(nome);
        String jsonUtente = objectMapper.writeValueAsString(utente);

        String response = mockMvc.perform(post("/utenti")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUtente))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Utente utenteCreato = objectMapper.readValue(response, Utente.class);

        // GET /utenti/{id}
        mockMvc.perform(get("/utenti/" + utenteCreato.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(nome));

        // DELETE /utenti/{id}
        mockMvc.perform(delete("/utenti/" + utenteCreato.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testSediaCrud() throws Exception {
        // POST /sedie
    	String codice = "ABC30";
        Sedia sedia = new Sedia(codice);
        String jsonSedia = objectMapper.writeValueAsString(sedia);

        String response = mockMvc.perform(post("/sedie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSedia))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Sedia sediaCreato = objectMapper.readValue(response, Sedia.class);

        // GET /sedie/{id}
        mockMvc.perform(get("/sedie/" + sediaCreato.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codice").value(codice));

        // DELETE /sedie/{id}
        mockMvc.perform(delete("/sedie/" + sediaCreato.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testUtenteSedutoCrud() throws Exception {
        Utente utente = new Utente("Pasquale"); 
        utente = utenteRepo.save(utente);
        Sedia sedia = new Sedia("ZZZ40"); 
        sedia = sediaRepo.save(sedia);

        // POST /seduti
        String jsonSeduta = String.format("{\"utenteId\":%d,\"codiceSedia\":\"%s\"}", utente.getId(), sedia.getCodice());
        String response = mockMvc.perform(post("/seduti")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSeduta))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UtenteSeduto sedutaCreato = objectMapper.readValue(response, UtenteSeduto.class);
        
        // POST /seduti - Utente già seduto e sedia in utilizzo
        jsonSeduta = String.format("{\"utenteId\":%d,\"codiceSedia\":\"%s\"}", utente.getId(), sedia.getCodice());
        mockMvc.perform(post("/seduti")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSeduta))
                .andExpect(status().isConflict());

        // GET /seduti/{id}
        mockMvc.perform(get("/seduti/" + sedutaCreato.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.utente.id").value(utente.getId()))
                .andExpect(jsonPath("$.sedia.codice").value(sedia.getCodice()));

        // GET /seduti/by-sedia/{codiceSedia}
        mockMvc.perform(get("/seduti/by-sedia/" + sedia.getCodice()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.utente.id").value(utente.getId()));

        // DELETE /seduti/{id}
        mockMvc.perform(delete("/seduti/" + sedutaCreato.getId()))
                .andExpect(status().isOk());
    }
}
