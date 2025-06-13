package com.ey.backend.service;

import com.ey.backend.dto.PersonaRequest;
import com.ey.backend.dto.PersonaResponse;
import com.ey.backend.repository.PersonaRepository;
import com.ey.backend.repository.ResidenzaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonaService {

    private PersonaRepository personaRepository;
    private ResidenzaRepository residenzaRepository;

    // recupera la lista delle persone presenti nel db
    public List<PersonaResponse> getAllPersone() {
    }

    // recupera una persona presente nel DB
    public PersonaResponse getPersonaById(Long id) {}

   // aggiungere nuova persona nel DB
    public PersonaResponse addPersona(PersonaRequest personaRequest) {}

    // aggiornare dettagli persona nel DB
    public PersonaResponse updatePersona(Long id, PersonaRequest personaRequest) {}

    // cancellare una Persona nel DB
    public void deletePersona(Long id) {}

}
