package com.ey.backend.mapper;

import com.ey.backend.dto.ResidenzaRequest;
import com.ey.backend.dto.ResidenzaResponse;
import com.ey.backend.entity.Residenza;
import org.springframework.stereotype.Component;

@Component
public class ResidenzaMapper {

    public Residenza toEntity(ResidenzaRequest residenzaRequest) {
        Residenza residenza = new Residenza();
        residenza.setIndirizzo(residenzaRequest.getIndirizzo());
        residenza.setCap(residenzaRequest.getCap());
        residenza.setCitta(residenzaRequest.getCitta());
        return residenza;
    }

    public ResidenzaResponse toResidenzaResponse(Residenza residenza) {
        ResidenzaResponse residenzaResponse = new ResidenzaResponse();
        residenzaResponse.setId(residenza.getId());
        residenzaResponse.setIndirizzo(residenza.getIndirizzo());
        residenzaResponse.setCap(residenza.getCap());
        residenzaResponse.setCitta(residenza.getCitta());

        if (residenza.getPersona() != null) {
            residenzaResponse.setPersonaId(residenza.getPersona().getId());
        }
        return residenzaResponse;
    }

    // Metodo per aggiornare un'entità Residenza esistente con dati da ResidenzaRequest
    public void updateEntityFromDto(ResidenzaRequest residenzaRequest, Residenza residenza) {
        residenza.setIndirizzo(residenzaRequest.getIndirizzo());
        residenza.setCap(residenzaRequest.getCap());
        residenza.setCitta(residenzaRequest.getCitta());
    }
}