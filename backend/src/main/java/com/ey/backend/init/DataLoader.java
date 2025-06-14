package com.ey.backend.init;

import com.ey.backend.dto.PersonaRequest;
import com.ey.backend.dto.PersonaResponse;
import com.ey.backend.dto.ResidenzaRequest;
import com.ey.backend.dto.ResidenzaResponse;
import com.ey.backend.service.PersonaService;
import com.ey.backend.service.ResidenzaService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PersonaService personaService;
    private final ResidenzaService residenzaService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Inizializzazione dei dati nel database H2...");

        // --- Creazione della Persona 1 ---
        PersonaRequest persona1Request = new PersonaRequest();
        persona1Request.setNome("Mario");
        persona1Request.setCognome("Rossi");
        persona1Request.setCodiceFiscale("RSSMRA80A01H501J");
        persona1Request.setDataNascita(LocalDate.of(1980, 1, 15));
        PersonaResponse persona1 = personaService.addPersona(persona1Request);
        System.out.println("Creata Persona: " + persona1.getNome() + " " + persona1.getCognome() + " (ID: " + persona1.getId() + ")");

        // --- Aggiunta di Residenza per Persona 1 ---
        ResidenzaRequest residenza1_1Request = new ResidenzaRequest();
        residenza1_1Request.setIndirizzo("Via Roma, 10");
        residenza1_1Request.setCap("00100");
        residenza1_1Request.setCitta("Roma");
        residenza1_1Request.setPersonaId(persona1.getId()); // personaId è già impostato nel DTO
        ResidenzaResponse residenza1_1 = residenzaService.addResidenza(residenza1_1Request);
        System.out.println("Aggiunta residenza a " + persona1.getNome() + ": " + residenza1_1.getIndirizzo());

        ResidenzaRequest residenza1_2Request = new ResidenzaRequest();
        residenza1_2Request.setIndirizzo("Piazza Duomo, 5");
        residenza1_2Request.setCap("20121");
        residenza1_2Request.setCitta("Milano");
        residenza1_2Request.setPersonaId(persona1.getId()); // personaId è già impostato nel DTO
        ResidenzaResponse residenza1_2 = residenzaService.addResidenza(residenza1_2Request);
        System.out.println("Aggiunta seconda residenza a " + persona1.getNome() + ": " + residenza1_2.getIndirizzo());

        // --- Creazione della Persona 2 ---
        PersonaRequest persona2Request = new PersonaRequest();
        persona2Request.setNome("Giulia");
        persona2Request.setCognome("Verdi");
        persona2Request.setCodiceFiscale("VRDGUL92B02F205K");
        persona2Request.setDataNascita(LocalDate.of(1992, 2, 20));
        PersonaResponse persona2 = personaService.addPersona(persona2Request);
        System.out.println("Creata Persona: " + persona2.getNome() + " " + persona2.getCognome() + " (ID: " + persona2.getId() + ")");

        // --- Aggiunta di Residenza per Persona 2 ---
        ResidenzaRequest residenza2_1Request = new ResidenzaRequest();
        residenza2_1Request.setIndirizzo("Via Milano, 20");
        residenza2_1Request.setCap("10100");
        residenza2_1Request.setCitta("Torino");
        residenza2_1Request.setPersonaId(persona2.getId()); // personaId è già impostato nel DTO
        ResidenzaResponse residenza2_1 = residenzaService.addResidenza(residenza2_1Request);
        System.out.println("Aggiunta residenza a " + persona2.getNome() + ": " + residenza2_1.getIndirizzo());

        // --- Persona 3 (senza residenza iniziale, la aggiungeremo dopo) ---
        PersonaRequest persona3Request = new PersonaRequest();
        persona3Request.setNome("Luca");
        persona3Request.setCognome("Bianchi");
        persona3Request.setCodiceFiscale("BNCLCU85C03L219Z");
        persona3Request.setDataNascita(LocalDate.of(1985, 3, 25));
        PersonaResponse persona3 = personaService.addPersona(persona3Request);
        System.out.println("Creata Persona: " + persona3.getNome() + " " + persona3.getCognome() + " (ID: " + persona3.getId() + ")");

        System.out.println("Inizializzazione dati completata.");
    }
}