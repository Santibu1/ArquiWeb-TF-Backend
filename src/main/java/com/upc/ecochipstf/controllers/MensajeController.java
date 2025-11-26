package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.MensajeDTO;
import com.upc.ecochipstf.interfaces.IMensajeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/api/mensajes")
public class MensajeController {
    @Autowired
    private IMensajeService mensajeService;

    public MensajeController(IMensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    @PreAuthorize("hasAnyRole('MODERADOR','CLIENTE')")
    @PostMapping("/enviar")
    public ResponseEntity<MensajeDTO> enviarMensaje(@RequestBody MensajeDTO mensajeDTO, Authentication authentication) {
        String email = authentication.getName();
        MensajeDTO creado = mensajeService.enviarMensaje(mensajeDTO, email);
        return ResponseEntity.ok(creado);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','MODERADOR','CLIENTE')")
    @GetMapping("/comunidad/{comunidadId}")
    public ResponseEntity<List<MensajeDTO>> listar(@PathVariable Long comunidadId) {
        return ResponseEntity.ok(mensajeService.listarMensajesPorComunidad(comunidadId));
    }
}
