package com.ey.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome", nullable = false)
    private String nome;

    @Column(name="cognome")
    private String cognome;

    @Column(name="codice_fiscale", unique = true)
    private String codiceFiscale;

    @Column(name="data_nascita")
    private LocalDate dataNascita;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Residenza> residenze = new ArrayList<>();

    // Metodi helper per mantenere la consistenza della relazione bidirezionale
    public void addResidenza(Persona persona, Residenza residenza) {
        persona.getResidenze().add(residenza);
        residenza.setPersona(persona);
    }

    public void removeResidenza(Persona persona, Residenza residenza) {
        persona.getResidenze().remove(residenza);
        residenza.setPersona(null);
    }
}