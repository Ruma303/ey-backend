package com.ey.backend.repository;

import com.ey.backend.entity.Residenza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidenzaRepository extends JpaRepository<Long, Residenza> {
}
