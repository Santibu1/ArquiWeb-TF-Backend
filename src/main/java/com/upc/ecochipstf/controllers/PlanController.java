package com.upc.ecochipstf.controllers;
import com.upc.ecochipstf.dto.PlanDTO;
import com.upc.ecochipstf.interfaces.IPlanService;
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
@RequestMapping("/api")
public class PlanController {
    @Autowired
    private IPlanService planService;

    @PostMapping("/Crear-Plan")
    public PlanDTO crearPlan(@Valid @RequestBody PlanDTO planDTO) {
        return planService.crearPlan(planDTO);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/Listar-Planes-Activos")
    public List<PlanDTO> listarPlanesActivos() {
        return planService.listarPlanesActivos();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/Listar-Planes-Por/{id}")
    public PlanDTO obtenerPlanPorId(@PathVariable Long id) {
        return planService.obtenerPlanPorId(id);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/Actualizar-Plan")
    public PlanDTO actualizarPlan(@RequestBody PlanDTO planDTO) {
        return planService.actualizarPlan(planDTO);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/Eliminar-Plan/{id}")
    public void eliminarPlan(@PathVariable Long id) {
        planService.eliminarPlan(id);
    }
}