package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.services.EcoChatService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true",
        exposedHeaders = "Authorization")
public class EcoChatController {

    private final EcoChatService ecoChatService;

    public EcoChatController(EcoChatService ecoChatService) {
        this.ecoChatService = ecoChatService;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','MODERADOR','CLIENTE')")
    @GetMapping("/eco-tip")
    public String obtenerEcoTip() {
        return ecoChatService.obtenerConsejo();
    }
}