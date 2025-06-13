package com.ey.backend.controller;

import com.ey.backend.dto.PersonaResponse;
import com.ey.backend.entity.Persona;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/persone")
@AllArgsConstructor
public class PersonaController {

    @GetMapping
    public ResponseEntity<List<PersonaResponse>> getAll(){
        // return null;
    }


}
