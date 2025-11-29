package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.ReporteParticipacionDTO;
import com.upc.ecochipstf.dto.UsuarioEventoDTO;
import com.upc.ecochipstf.interfaces.IUsuarioEventoService;
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
@RequestMapping("/api/usuario-eventos")
public class UsuarioEventoController {

    @Autowired
    private IUsuarioEventoService usuarioEventoService;

    @PreAuthorize("hasAnyRole('MODERADOR', 'CLIENTE')")
    @PutMapping("/inscribirse/{usuarioId}/{eventoId}")
    public UsuarioEventoDTO inscribirse(@PathVariable Long usuarioId, @PathVariable Long eventoId) {
        return usuarioEventoService.inscribirseEnEvento(usuarioId, eventoId);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/usuario/{usuarioId}")
    public List<UsuarioEventoDTO> listarEventosPorUsuario(@PathVariable Long usuarioId) {
        return usuarioEventoService.listarEventosPorUsuario(usuarioId);
    }

    @PreAuthorize("hasAnyRole('MODERADOR', 'CLIENTE')")
    @DeleteMapping("/cancelar/{usuarioId}/{eventoId}")
    public void cancelar(@PathVariable Long usuarioId, @PathVariable Long eventoId) {
        usuarioEventoService.cancelarInscripcion(usuarioId, eventoId);
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/evento/{eventoId}") // Nuevo Endpoint
    public List<UsuarioEventoDTO> listarParticipantes(@PathVariable Long eventoId) {
        return usuarioEventoService.listarParticipantesPorEvento(eventoId);
    }

    @PreAuthorize("hasAnyRole('CLIENTE','MODERADOR')")
    @GetMapping("/{usuarioId}/reportes")
    public ReporteParticipacionDTO obtenerReporteParticipacion(
            @PathVariable Long usuarioId,
            @RequestParam int mes,
            @RequestParam int anio) {
        return usuarioEventoService.obtenerReporteMensual(usuarioId, mes, anio);
    }

    @CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
    @PreAuthorize("hasRole('MODERADOR')")
    @PostMapping("/confirmar-asistencia/{eventoId}/{usuarioId}")
    public UsuarioEventoDTO confirmarAsistencia(
            @PathVariable Long eventoId,
            @PathVariable Long usuarioId) {
        return usuarioEventoService.confirmarAsistenciaEvento(eventoId, usuarioId);
    }
}

