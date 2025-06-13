package com.ey.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResidenzaResponse {
    private Long id;
    private String indirizzo;
    private Short cap;
    private String citta;
    private Long personaId;
}
