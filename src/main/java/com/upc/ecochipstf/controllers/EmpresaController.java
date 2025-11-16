package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.EmpresaDTO;
import com.upc.ecochipstf.interfaces.IEmpresaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
@RequestMapping("/api")
public class EmpresaController {
    @Autowired
    private IEmpresaService empresaService;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/Registrar-Empresa")
    public EmpresaDTO crearEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) {
        return empresaService.crearEmpresa(empresaDTO);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/Listar-Empresas-Activas")
    public List<EmpresaDTO> listarEmpresasActivas() {
        return empresaService.listarEmpresasActivas();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/Listar-Empresa-Por/{id}")
    public EmpresaDTO obtenerEmpresaPorId(@PathVariable Long id) {
        return empresaService.obtenerEmpresaPorId(id);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/Actualizar-Empresa/{id}")
    public EmpresaDTO actualizarEmpresa(@PathVariable Long id, @RequestBody EmpresaDTO empresaDTO) {
        empresaDTO.setEmpresaId(id);
        return empresaService.actualizarEmpresa(empresaDTO);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/Eliminar-Empresa/{id}")
    public void eliminarEmpresa(@PathVariable Long id) {
        empresaService.eliminarEmpresa(id);
    }
}
