package com.ey.backend.repository;

import com.ey.backend.entity.Residenza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidenzaRepository extends JpaRepository<Residenza, Long> {
    List<Residenza> findByIndirizzo(String indirizzo);
}
