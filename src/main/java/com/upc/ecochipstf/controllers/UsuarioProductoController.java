package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.UsuarioProductoResponseDTO;
import com.upc.ecochipstf.dto.UsuarioProductoRequestDTO;
import com.upc.ecochipstf.interfaces.IUsuarioProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario-producto")
public class UsuarioProductoController {
    @Autowired
    private IUsuarioProductoService usuarioProductoService;

    //@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @PostMapping("/canjear")
    public ResponseEntity<?> canjear(@RequestBody UsuarioProductoRequestDTO requestDTO) {
        try {
            UsuarioProductoResponseDTO respuesta = usuarioProductoService.canjear(requestDTO);
            return ResponseEntity.ok(respuesta);
        }
        catch (RuntimeException ex) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    //@PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<?> listar(@PathVariable Long usuarioId) {
        List<UsuarioProductoResponseDTO> historial = usuarioProductoService.historial(usuarioId);
        if(historial.isEmpty()){
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("message", "El usuario no tiene canjes");
            respuesta.put("historial", historial);
            return ResponseEntity.ok(respuesta);
        }
        return ResponseEntity.ok(historial);
    }
}

