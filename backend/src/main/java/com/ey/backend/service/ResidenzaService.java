package com.ey.backend.service;

import com.ey.backend.dto.PersonaResponse;
import com.ey.backend.dto.ResidenzaRequest;
import com.ey.backend.dto.ResidenzaResponse;
import com.ey.backend.mapper.PersonaMapper;
import com.ey.backend.mapper.ResidenzaMapper;
import com.ey.backend.entity.Persona;
import com.ey.backend.entity.Residenza;
import com.ey.backend.repository.PersonaRepository;
import com.ey.backend.repository.ResidenzaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResidenzaService {

    private ResidenzaRepository residenzaRepository;
    private PersonaRepository personaRepository;
    private ResidenzaMapper residenzaMapper;
    private PersonaMapper personaMapper;

    /**
     * Aggiunge una nuova residenza e la associa a una Persona esistente.
     * L'ID della persona è contenuto nel ResidenzaRequest.
     *
     * @param residenzaRequest Il DTO con i dati della nuova residenza e l'ID della persona.
     * @return Il DTO della residenza creata.
     * @throws NoSuchElementException se la Persona non viene trovata.
     */
    @Transactional
    public ResidenzaResponse addResidenza(ResidenzaRequest residenzaRequest) {
        Persona persona = personaRepository.findById(residenzaRequest.getPersonaId())
                .orElseThrow(() -> new NoSuchElementException("Persona con ID " + residenzaRequest.getPersonaId() + " non trovata."));

        Residenza residenza = residenzaMapper.toEntity(residenzaRequest);
        persona.addResidenza(residenza); // Assicurati che Persona abbia addResidenza
        personaRepository.save(persona); // Questo salverà anche la nuova residenza grazie a cascade=ALL

        return residenzaMapper.toResidenzaResponse(residenza);
    }

    /**
     * Modifica un indirizzo esistente.
     *
     * @param residenzaId L'ID della residenza da modificare.
     * @param residenzaRequest Il DTO con i dati aggiornati della residenza.
     * @return Il DTO della residenza modificata.
     * @throws NoSuchElementException se la Residenza non viene trovata.
     * @throws IllegalArgumentException se il personaId nel DTO non corrisponde alla persona della residenza esistente,
     * indicando un tentativo di cambiare l'associazione (non permesso in questo update).
     */
    @Transactional
    public ResidenzaResponse updateResidenza(Long residenzaId, ResidenzaRequest residenzaRequest) {
        Residenza residenzaToUpdate = residenzaRepository.findById(residenzaId)
                .orElseThrow(() -> new NoSuchElementException("Residenza con ID " + residenzaId + " non trovata."));

        // Opzionale: Verificare che l'ID della persona nel DTO corrisponda all'ID della persona associata
        // per prevenire la riassegnazione di una residenza ad un'altra persona tramite update.
        if (residenzaRequest.getPersonaId() != null && !residenzaToUpdate.getPersona().getId().equals(residenzaRequest.getPersonaId())) {
            throw new IllegalArgumentException("Impossibile riassegnare la residenza a un'altra persona tramite update. " +
                    "La residenza con ID " + residenzaId + " appartiene alla Persona ID " +
                    residenzaToUpdate.getPersona().getId() + ", ma è stato fornito Persona ID " +
                    residenzaRequest.getPersonaId() + ".");
        }

        residenzaMapper.updateEntityFromDto(residenzaRequest, residenzaToUpdate);
        Residenza updatedResidenza = residenzaRepository.save(residenzaToUpdate);
        return residenzaMapper.toResidenzaResponse(updatedResidenza);
    }

    /**
     * Recupera la lista delle persone che vivono presso un certo indirizzo.
     * Questa logica si occupa dell'endpoint "dato un indirizzo restituisca la lista delle persone che vivono presso un certo indirizzo".
     *
     * @param indirizzo L'indirizzo da cercare.
     * @return Una lista di DTO PersonaResponse.
     */
    @Transactional(readOnly = true)
    public List<PersonaResponse> getPersoneByIndirizzo(String indirizzo) { // Modificato per prendere solo indirizzo
        List<Residenza> residenze = residenzaRepository.findByIndirizzo(indirizzo); // Usa il metodo corretto del repository

        return residenze.stream()
                .map(Residenza::getPersona)
                .distinct()
                .map(personaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Elimina una residenza dal DB.
     *
     * @param residenzaId L'ID della residenza da eliminare.
     * @throws NoSuchElementException se la Residenza non viene trovata.
     */
    @Transactional
    public void deleteResidenza(Long residenzaId) {
        Residenza residenzaToDelete = residenzaRepository.findById(residenzaId)
                .orElseThrow(() -> new NoSuchElementException("Residenza con ID " + residenzaId + " non trovata per l'eliminazione."));

        // Rimuovi la residenza dalla lista della persona associata per mantenere la coerenza
        if (residenzaToDelete.getPersona() != null) {
            residenzaToDelete.getPersona().removeResidenza(residenzaToDelete);
            // Salva la persona per propagare il cambiamento se non è già gestito da CascadeType.ALL + orphanRemoval
            // personaRepository.save(residenzaToDelete.getPersona()); // Potrebbe non essere necessario con orphanRemoval=true
        }
        residenzaRepository.deleteById(residenzaId);
    }
}
