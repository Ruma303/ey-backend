package com.ey.backend.repository;

import com.ey.backend.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    @Query("SELECT p FROM Persona p JOIN p.residenze r WHERE r.indirizzo = :indirizzo")
    List<Persona> findPersoneByIndirizzo(@Param("indirizzo") String indirizzo);
}
