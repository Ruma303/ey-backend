package com.ey.backend.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class ResidenzaRequest {
    @NotBlank(message = "L'indirizzo è obbligatorio")
    private String indirizzo;

    @NotNull(message = "Il CAP è obbligatorio")
    @Min(value = 10000, message = "Il CAP deve essere un numero di 5 cifre")
    private Short cap;

    @NotBlank(message = "La città è obbligatoria")
    private String citta;

    @NotNull(message = "L'ID della persona è obbligatorio")
    private Long idAnagrafica;
}