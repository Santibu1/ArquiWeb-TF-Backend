package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.ReporteParticipacionDTO;
import com.upc.ecochipstf.dto.UsuarioEventoDTO;
import com.upc.ecochipstf.interfaces.IUsuarioEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @PreAuthorize("hasAnyRole('CLIENTE','MODERADOR')")
    @GetMapping("/{usuarioId}/reportes")
    public ReporteParticipacionDTO obtenerReporteParticipacion(
            @PathVariable Long usuarioId,
            @RequestParam int mes,
            @RequestParam int anio) {
        return usuarioEventoService.obtenerReporteMensual(usuarioId, mes, anio);
    }
}

