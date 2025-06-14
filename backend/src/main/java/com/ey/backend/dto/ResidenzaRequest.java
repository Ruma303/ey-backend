package com.ey.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResidenzaRequest {

    @NotBlank(message = "L'indirizzo è obbligatorio")
    @Size(max = 255, message = "L'indirizzo non può superare i 255 caratteri")
    private String indirizzo;

    @NotNull(message = "Il CAP è obbligatorio")
    @Pattern(regexp = "\\d{5}", message = "Il CAP deve essere un numero di 5 cifre")
    private String cap;

    @NotBlank(message = "La città è obbligatoria")
    @Size(max = 100, message = "La città non può superare i 100 caratteri")
    private String citta;

    @NotNull(message = "L'ID della persona è obbligatorio")
    private Long personaId;
}
