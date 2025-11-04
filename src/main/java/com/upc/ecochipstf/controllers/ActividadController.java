package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.ActividadDTO;
import com.upc.ecochipstf.interfaces.IActividadService;
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
@RequestMapping("/api")
public class ActividadController {
    @Autowired
    private IActividadService actividadService;


    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/registrarActividad")
    public ResponseEntity<ActividadDTO> crearActividad(@Valid @RequestBody ActividadDTO actividadDTO) {
        return ResponseEntity.ok(actividadService.crearActividad(actividadDTO));
    }


    //@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/listarActividades")
    public ResponseEntity<List<ActividadDTO>> listarActividadesActivas() {
        return ResponseEntity.ok(actividadService.listarActividadesActivas());
    }


    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/listarActividadesPorID/{id}")
    public ResponseEntity<ActividadDTO> obtenerActividadPorId(@PathVariable Long id) {
        return ResponseEntity.ok(actividadService.obtenerActividadPorId(id));
    }   


    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/actualizarActividades/{id}")
    public ResponseEntity<ActividadDTO> actualizarActividad(@PathVariable Long id, @RequestBody ActividadDTO actividadDTO) {
        actividadDTO.setActividadId(id);
        return ResponseEntity.ok(actividadService.actualizarActividad(actividadDTO));
    }


    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/eliminar-Actividad/{id}")
    public ResponseEntity<Void> eliminarActividad(@PathVariable Long id) {
        actividadService.eliminarActividad(id);
        return ResponseEntity.noContent().build();
    }
}
