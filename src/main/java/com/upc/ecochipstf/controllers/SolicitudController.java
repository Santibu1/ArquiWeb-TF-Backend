package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.SolicitudDTO;
import com.upc.ecochipstf.interfaces.ISolicitudService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private ISolicitudService solicitudService;

    @PreAuthorize("hasRole('MODERADOR')")
    @PostMapping("/crear")
    public ResponseEntity<SolicitudDTO> crearSolicitud(@Valid @RequestBody SolicitudDTO solicitudDTO) {
        SolicitudDTO creada = solicitudService.crearSolicitud(solicitudDTO);
        return ResponseEntity.ok(creada);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}/aprobar/{adminId}")
    public ResponseEntity<SolicitudDTO> aprobarSolicitud(@PathVariable Long id, @PathVariable Long adminId) {
        SolicitudDTO aprobada = solicitudService.aprobarSolicitud(id, adminId);
        return ResponseEntity.ok(aprobada);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{id}/rechazar/{adminId}")
    public ResponseEntity<SolicitudDTO> rechazarSolicitud(@PathVariable Long id, @PathVariable Long adminId) {
        SolicitudDTO rechazada = solicitudService.rechazarSolicitud(id, adminId);
        return ResponseEntity.ok(rechazada);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/listar")
    public ResponseEntity<List<SolicitudDTO>> listarSolicitudes() {
        List<SolicitudDTO> lista = solicitudService.listarSolicitudes();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDTO> obtenerPorId(@PathVariable Long id) {
        SolicitudDTO solicitud = solicitudService.obtenerSolicitudPorId(id);
        return ResponseEntity.ok(solicitud);
    }
}
