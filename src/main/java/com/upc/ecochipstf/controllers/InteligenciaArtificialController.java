package com.upc.ecochipstf.controllers;
import com.upc.ecochipstf.services.InteligenciaArtificialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true",
                exposedHeaders = "Authorization")

public class InteligenciaArtificialController {

    @Autowired
    private InteligenciaArtificialService iaService;

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @PostMapping("/recomendar")
    public String obtenerRecomendacion(@RequestBody Map<String, String> payload) {
        String datos = payload.get("datos");
        if(datos == null) return "Sin datos.";

        return iaService.generarRecomendacion(datos);
    }
}