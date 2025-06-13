package com.ey.backend.service;

import com.ey.backend.dto.ResidenzaRequest;
import com.ey.backend.dto.ResidenzaResponse;
import com.ey.backend.entity.Persona;
import com.ey.backend.entity.Residenza;
import com.ey.backend.mapper.ResidenzaMapper;
import com.ey.backend.repository.PersonaRepository;
import com.ey.backend.repository.ResidenzaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ResidenzaService {

    private final ResidenzaRepository residenzaRepository;
    private final PersonaRepository personaRepository;
    private final ResidenzaMapper residenzaMapper;

    @Transactional
    public ResidenzaResponse addResidenzaForPersona(Long personaId, ResidenzaRequest residenzaRequest) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new NoSuchElementException("Persona con ID " + personaId + " non trovata."));

        Residenza residenza = residenzaMapper.toEntity(residenzaRequest);
        persona.addResidenza(residenza);

        Persona savedPersona = personaRepository.save(persona);
        Residenza savedResidenza = savedPersona.getResidenze().get(savedPersona.getResidenze().size() - 1);
        return residenzaMapper.toResidenzaResponse(savedResidenza);
    }

    @Transactional
    public ResidenzaResponse updateResidenza(Long residenzaId, ResidenzaRequest residenzaRequest) {
        Residenza residenzaToUpdate = residenzaRepository.findById(residenzaId)
                .orElseThrow(() -> new NoSuchElementException("Residenza con ID " + residenzaId + " non trovata."));

        residenzaMapper.updateEntityFromDto(residenzaRequest, residenzaToUpdate);
        Residenza updatedResidenza = residenzaRepository.save(residenzaToUpdate);
        return residenzaMapper.toResidenzaResponse(updatedResidenza);
    }

    @Transactional
    public void deleteResidenza(Long residenzaId) {
        if (!residenzaRepository.existsById(residenzaId)) {
            throw new NoSuchElementException("Residenza con ID " + residenzaId + " non trovata.");
        }
        residenzaRepository.deleteById(residenzaId);
    }

    @Transactional(readOnly = true)
    public ResidenzaResponse getResidenzaById(Long residenzaId) {
        Residenza residenza = residenzaRepository.findById(residenzaId)
                .orElseThrow(() -> new NoSuchElementException("Residenza con ID " + residenzaId + " non trovata."));
        return residenzaMapper.toResidenzaResponse(residenza);
    }
}
