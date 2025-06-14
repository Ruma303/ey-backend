package com.ey.backend.controller;

import com.ey.backend.dto.PersonaRequest;
import com.ey.backend.dto.PersonaResponse;
import com.ey.backend.dto.ResidenzaRequest;
import com.ey.backend.dto.ResidenzaResponse;
import com.ey.backend.service.PersonaService;
import com.ey.backend.service.ResidenzaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persone")
@AllArgsConstructor
public class PersonaController {

    private final PersonaService personaService;
    private final ResidenzaService residenzaService;

    // === CRUD PERSONE ===

    // controller che recupera la lista delle persone presenti nel db
    @GetMapping
    public ResponseEntity<List<PersonaResponse>> getAllPersone() {
        List<PersonaResponse> persone = personaService.getAllPersone();
        return ResponseEntity.ok(persone);
    }

    // Recupera una singola persona per ID (Aggiunto per completezza)
    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponse> getPersonaById(@PathVariable Long id) {
        PersonaResponse persona = personaService.getPersonaById(id);
        return ResponseEntity.ok(persona);
    }

    @PostMapping
    public ResponseEntity<PersonaResponse> createPersona(@Valid @RequestBody PersonaRequest personaRequest) {
        PersonaResponse nuovaPersona = personaService.addPersona(personaRequest);
        return new ResponseEntity<>(nuovaPersona, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaResponse> updatePersona(@PathVariable Long id, @Valid @RequestBody PersonaRequest personaRequest) {
        PersonaResponse personaAggiornata = personaService.updatePersona(id, personaRequest);
        return ResponseEntity.ok(personaAggiornata);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        personaService.deletePersona(id);
        return ResponseEntity.noContent().build();
    }

    // === Operazioni RESIDENZE (nel PersonaController) ===

    // controller per che dato un indirizzo restituisca la lista delle persone che vivono presso un certo indirizzo
    @GetMapping("/cerca-per-indirizzo")
    public ResponseEntity<List<PersonaResponse>> getPersoneByIndirizzo(@RequestParam String indirizzo) {
        List<PersonaResponse> persone = personaService.getPersoneByIndirizzo(indirizzo);
        return ResponseEntity.ok(persone);
    }

    // controller che aggiunge l’indirizzo per un Persona presente nel DB
    // URL: POST /api/persone/{personaId}/residenze
    @PostMapping("/{personaId}/residenze")
    public ResponseEntity<ResidenzaResponse> addResidenzaToPersona(
            @PathVariable Long personaId, // L'ID della persona è nel path
            @Valid @RequestBody ResidenzaRequest residenzaRequest) {

        // Controllo di coerenza: l'ID nel path deve corrispondere all'ID nel body della richiesta
        if (!personaId.equals(residenzaRequest.getPersonaId())) {
            throw new IllegalArgumentException("L'ID della persona nel percorso (" + personaId +
                    ") non corrisponde all'ID della persona nel corpo della richiesta (" +
                    residenzaRequest.getPersonaId() + ").");
        }
        ResidenzaResponse nuovaResidenza = residenzaService.addResidenza(residenzaRequest);
        return new ResponseEntity<>(nuovaResidenza, HttpStatus.CREATED);
    }

    // controller per modificare l’indirizzo per una Persona presente nel DB
    // URL: PUT /api/persone/residenze/{id} <- Modificato per evitare conflitto
    @PutMapping("/residenze/{id}")
    public ResponseEntity<ResidenzaResponse> updateResidenza(
            @PathVariable Long id, // Questo 'id' ora si riferisce all'ID della Residenza
            @Valid @RequestBody ResidenzaRequest residenzaRequest) {
        ResidenzaResponse residenzaAggiornata = residenzaService.updateResidenza(id, residenzaRequest);
        return ResponseEntity.ok(residenzaAggiornata);
    }

    // controller per cancellare una Residenza nel DB
    // URL: DELETE /api/persone/residenze/{id} <- Modificato per evitare conflitto
    @DeleteMapping("/residenze/{id}")
    public ResponseEntity<Void> deleteResidenza(@PathVariable Long id) { // Questo 'id' ora si riferisce all'ID della Residenza
        residenzaService.deleteResidenza(id);
        return ResponseEntity.noContent().build();
    }

    // --- Gestione delle Eccezioni Globali per questo controller ---

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFoundException(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
