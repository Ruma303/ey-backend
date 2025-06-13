package com.ey.backend.controller;

import com.ey.backend.dto.PersonaResponse;
import com.ey.backend.dto.ResidenzaRequest;
import com.ey.backend.dto.ResidenzaResponse;
import com.ey.backend.service.PersonaService;
import com.ey.backend.service.ResidenzaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persone")
@AllArgsConstructor
public class PersonaController {

    private final PersonaService personaService;
    private final ResidenzaService residenzaService;

    @GetMapping
    public ResponseEntity<List<PersonaResponse>> getAllPersone() {
        List<PersonaResponse> persone = personaService.getAllPersone();
        return ResponseEntity.ok(persone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        personaService.deletePersona(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{personaId}/residenze")
    public ResponseEntity<ResidenzaResponse> addResidenzaToPersona(
            @PathVariable Long personaId,
            @Valid @RequestBody ResidenzaRequest residenzaRequest) {
        ResidenzaResponse nuovaResidenza = residenzaService.addResidenzaForPersona(personaId, residenzaRequest);
        return new ResponseEntity<>(nuovaResidenza, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PersonaResponse>> getPersoneByIndirizzo(@RequestParam String indirizzo) {
        List<PersonaResponse> persone = personaService.getPersoneByIndirizzo(indirizzo);
        return ResponseEntity.ok(persone);
    }
}