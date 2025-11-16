package com.upc.ecochipstf.controllers;

import com.upc.ecochipstf.dto.LoginDTO;
import com.upc.ecochipstf.dto.UsuarioDTO;
import com.upc.ecochipstf.interfaces.IUsuarioService;
import com.upc.ecochipstf.repositorios.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200",
        allowCredentials = "true",
        exposedHeaders = "Authorization")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/registrarUsuario")
    public ResponseEntity<UsuarioDTO> registrar(@Valid @RequestBody UsuarioDTO dto) {
        UsuarioDTO registrado = usuarioService.registrarUsuario(dto);
        return ResponseEntity.ok(registrado);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<UsuarioDTO> lista = usuarioService.listarUsuarios();
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/eliminarUsuario/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @PutMapping("/editarUsuario/{id}")
    public ResponseEntity<UsuarioDTO> editar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        UsuarioDTO actualizado = usuarioService.modificarUsuario(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @PutMapping("/Asignar-Plan-Usuario/{usuarioId}/plan/{planId}")
    public ResponseEntity<UsuarioDTO> asignarPlan(
            @PathVariable Long usuarioId,
            @PathVariable Long planId) {
        UsuarioDTO actualizado = usuarioService.asignarPlan(usuarioId, planId);
        return ResponseEntity.ok(actualizado);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(@RequestBody LoginDTO loginDTO) {
        LoginDTO response = usuarioService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'MODERADOR', 'CLIENTE')")
    @GetMapping("/mi-perfil")
    public ResponseEntity<UsuarioDTO> obtenerPerfilUsuario() {
        // Spring Security sabe quién es el usuario gracias al token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        // Usas tu servicio para buscar al usuario por su email (que es el username)
        UsuarioDTO usuarioDTO = usuarioService.buscarPorEmail(userEmail);
        return ResponseEntity.ok(usuarioDTO);
    }
}
