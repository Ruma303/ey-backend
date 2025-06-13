package com.ey.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="residenza")
public class Residenza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="indirizzo", nullable = false)
    private String indirizzo;

    @Column(name="cap", nullable = false)
    private Short cap;

    @Column(name="citta", nullable = false)
    private String citta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_anagrafica", nullable = false)
    private Persona persona;
}