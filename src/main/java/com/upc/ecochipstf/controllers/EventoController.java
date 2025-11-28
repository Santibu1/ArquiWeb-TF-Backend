package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.EventoDTO;
import com.upc.ecochipstf.interfaces.IEventoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
@RequestMapping("/api/eventos")
public class EventoController {
    @Autowired
    private IEventoService eventoService;

    @PreAuthorize("hasRole('MODERADOR')")
    @PostMapping("/registrar")
    public EventoDTO registrarEvento(@Valid @RequestBody EventoDTO eventoDTO) {
        return eventoService.registrarEvento(eventoDTO);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/comunidad/{comunidadId}")
    public List<EventoDTO> listarEventosPorComunidad(@PathVariable Long comunidadId) {
        return eventoService.listarEventosPorComunidad(comunidadId);
    }
}//