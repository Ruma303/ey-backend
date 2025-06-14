//package com.ey.backend.controller;
//
//import com.ey.backend.dto.ResidenzaRequest;
//import com.ey.backend.dto.ResidenzaResponse;
//import com.ey.backend.service.ResidenzaService;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/residenze")
//@AllArgsConstructor
//public class ResidenzaController {
//
//    private final ResidenzaService residenzaService;
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ResidenzaResponse> updateResidenza(
//            @PathVariable Long id,
//            @Valid @RequestBody ResidenzaRequest residenzaRequest) {
//        ResidenzaResponse residenzaAggiornata = residenzaService.updateResidenza(id, residenzaRequest);
//        return ResponseEntity.ok(residenzaAggiornata);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteResidenza(@PathVariable Long id) {
//        residenzaService.deleteResidenza(id);
//        return ResponseEntity.noContent().build();
//    }
//}