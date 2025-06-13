package com.ey.backend.service;

import com.ey.backend.dto.PersonaRequest;
import com.ey.backend.dto.PersonaResponse;
import com.ey.backend.mapper.PersonaMapper;
import com.ey.backend.entity.Persona;
import com.ey.backend.repository.PersonaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonaService {

    private PersonaRepository personaRepository;
    private PersonaMapper personaMapper;

    @Transactional(readOnly = true)
    public List<PersonaResponse> getAllPersone() {
        return personaRepository.findAll().stream()
                .map(personaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PersonaResponse getPersonaById(Long id) {
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Persona con ID " + id + " non trovata."));
        return personaMapper.toResponseDto(persona);
    }

    @Transactional
    public PersonaResponse addPersona(PersonaRequest personaRequest) {
        Persona persona = personaMapper.toEntity(personaRequest);
        Persona savedPersona = personaRepository.save(persona);
        return personaMapper.toResponseDto(savedPersona);
    }

    @Transactional
    public PersonaResponse updatePersona(Long id, PersonaRequest personaRequest) {
        Persona personaToUpdate = personaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Persona con ID " + id + " non trovata per l'aggiornamento."));
        personaMapper.updateEntityFromDto(personaRequest, personaToUpdate);// 3. Salva la Persona aggiornata nel database
        Persona updatedPersona = personaRepository.save(personaToUpdate);
        return personaMapper.toResponseDto(updatedPersona);
    }
    @Transactional
    public void deletePersona(Long id) {
        if (!personaRepository.existsById(id)) {
            throw new NoSuchElementException("Persona con ID " + id + " non trovata per l'eliminazione.");
        }
        personaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PersonaResponse> getPersoneByIndirizzo(String indirizzo) {
        List<Persona> persone = personaRepository.findPersoneByIndirizzo(indirizzo);
        return persone.stream()
                .map(personaMapper::toResponseDto)
                .collect(Collectors.toList());
    }
}