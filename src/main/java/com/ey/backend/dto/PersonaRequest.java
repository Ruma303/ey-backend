package com.ey.backend.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class PersonaRequest {
    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;

    private String cognome;

    @NotBlank(message = "Il codice fiscale è obbligatorio")
    private String codiceFiscale;

    @NotNull(message = "La data di nascita è obbligatoria")
    private LocalDate dataNascita;
}