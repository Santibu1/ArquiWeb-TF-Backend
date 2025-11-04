package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.MetricasEmpresaDTO;
import com.upc.ecochipstf.dto.ReporteEmpresaDTO;
import com.upc.ecochipstf.interfaces.IReporteEmpresaService;
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
@RequestMapping("/api/reportes")
public class ReporteEmpresaController {

    @Autowired
    private IReporteEmpresaService reporteEmpresaService;

    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/empresas")
    public List<ReporteEmpresaDTO> obtenerRankingEmpresas() {
        return reporteEmpresaService.obtenerRankingEmpresas();
    }

    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/empresas/{empresaId}")
    public List<MetricasEmpresaDTO> obtenerMetricas(@PathVariable Long empresaId) {
        return reporteEmpresaService.obtenerMetricas(empresaId);
    }
}
