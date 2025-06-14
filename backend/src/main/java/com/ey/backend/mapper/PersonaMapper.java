package com.ey.backend.mapper;

import com.ey.backend.dto.PersonaRequest;
import com.ey.backend.dto.PersonaResponse;import com.ey.backend.dto.ResidenzaResponse;
import com.ey.backend.entity.Persona;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonaMapper {

    private final ResidenzaMapper residenzaMapper;

    public PersonaMapper(ResidenzaMapper residenzaMapper) {
        this.residenzaMapper = residenzaMapper;
    }

    public Persona toEntity(PersonaRequest personaRequest) {
        Persona persona = new Persona();
        persona.setNome(personaRequest.getNome());
        persona.setCognome(personaRequest.getCognome());
        persona.setCodiceFiscale(personaRequest.getCodiceFiscale());
        persona.setDataNascita(personaRequest.getDataNascita());
        return persona;
    }

    public PersonaResponse toResponseDto(Persona persona) {
        PersonaResponse personaResponse = new PersonaResponse();
        personaResponse.setId(persona.getId()); // Mappa l'ID
        personaResponse.setNome(persona.getNome());
        personaResponse.setCognome(persona.getCognome());
        personaResponse.setCodiceFiscale(persona.getCodiceFiscale());
        personaResponse.setDataNascita(persona.getDataNascita());

        if (persona.getResidenze() != null && !persona.getResidenze().isEmpty()) {
            List<ResidenzaResponse> residenzeResponse = persona.getResidenze().stream()
                    .map(residenzaMapper::toResidenzaResponse)
                    .collect(Collectors.toList());
            personaResponse.setResidenze(residenzeResponse);
        }
        return personaResponse;
    }

    public void updateEntityFromDto(PersonaRequest personaRequest, Persona persona) {
        persona.setNome(personaRequest.getNome());
        persona.setCognome(personaRequest.getCognome());
        persona.setCodiceFiscale(personaRequest.getCodiceFiscale());
        persona.setDataNascita(personaRequest.getDataNascita());
    }
}