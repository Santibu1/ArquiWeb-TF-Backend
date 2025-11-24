package com.upc.ecochipstf.controllers;
import com.upc.ecochipstf.dto.UsuarioActividadDTO;
import com.upc.ecochipstf.interfaces.IUsuarioActividadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
@RequestMapping("/api/usuario-actividad")
public class UsuarioActividadController {
    @Autowired
    private IUsuarioActividadService usuarioActividadService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','MODERADOR', 'CLIENTE')")
    @PostMapping("/completar/{usuarioId}/{actividadId}")
    public UsuarioActividadDTO completarActividad(@PathVariable Long usuarioId, @PathVariable Long actividadId) {
        return usuarioActividadService.completarActividad(actividadId, usuarioId);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/usuario/{usuarioId}")
    public List<UsuarioActividadDTO> listarPorUsuario(@PathVariable Long usuarioId) {
        return usuarioActividadService.listarActividadesPorUsuario(usuarioId);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/actividad/{actividadId}")
    public List<UsuarioActividadDTO> listarPorActividad(@PathVariable Long actividadId) {
        return usuarioActividadService.listarUsuariosPorActividad(actividadId);
    }
}
