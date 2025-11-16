package com.upc.ecochipstf.services;

import com.upc.ecochipstf.dto.LoginDTO;
import com.upc.ecochipstf.dto.UsuarioDTO;
import com.upc.ecochipstf.entities.Plan;
import com.upc.ecochipstf.entities.Rol;
import com.upc.ecochipstf.entities.Usuario;
import com.upc.ecochipstf.interfaces.IUsuarioService;
import com.upc.ecochipstf.repositorios.PlanRepository;
import com.upc.ecochipstf.repositorios.RolRepository;
import com.upc.ecochipstf.repositorios.UsuarioRepository;
import com.upc.ecochipstf.security.services.CustomUserDetailsService;
import com.upc.ecochipstf.security.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO){
        Usuario usuarioExistente = usuarioRepository.findByEmailUsuario(usuarioDTO.getEmailUsuario());
        if (usuarioExistente != null) {
            if ("Desactivado".equalsIgnoreCase(usuarioExistente.getEstado())) {
                usuarioExistente.setEstado("Activo");
                usuarioExistente.setEcobits(0L);
                usuarioExistente.setNombreUsuario(usuarioDTO.getNombreUsuario());
                usuarioExistente.setApellidoUsuario(usuarioDTO.getApellidoUsuario());

                // Actualiza otros campos necesarios
                usuarioExistente = usuarioRepository.save(usuarioExistente);
                return modelMapper.map(usuarioExistente, UsuarioDTO.class);
            } else {
                throw new RuntimeException("El correo ya está registrado y activo.");
            }
        }
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        usuario.setEstado("Activo");
        usuario.setPasswordUsuario(passwordEncoder.encode(usuarioDTO.getPasswordUsuario()));
        usuario = usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setEstado("Desactivado");
            usuarioRepository.save(usuario);
        });
    }

    @Override //si es que hay administrador se hara la lista
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioDTO modificarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));

        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setApellidoUsuario(usuarioDTO.getApellidoUsuario());
        usuario.setEmailUsuario(usuarioDTO.getEmailUsuario());

        if (usuarioDTO.getPasswordUsuario() != null && !usuarioDTO.getPasswordUsuario().isEmpty()) {
            usuario.setPasswordUsuario(passwordEncoder.encode(usuarioDTO.getPasswordUsuario()));
        }

        usuario.setEdadUsuario(usuarioDTO.getEdadUsuario());
        usuario.setEcobits(usuarioDTO.getEcobits());

        if (usuarioDTO.getPlanId() != null) {
            Plan plan = planRepository.findById(usuarioDTO.getPlanId())
                    .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
            usuario.setPlan(plan);
        }

        Usuario actualizado = usuarioRepository.save(usuario);

        UsuarioDTO dto = modelMapper.map(actualizado, UsuarioDTO.class);
        dto.setRolId(usuario.getRol() != null ? usuario.getRol().getRolId() : null);
        dto.setPlanId(usuario.getPlan() != null ? usuario.getPlan().getPlanId() : null);

        return dto;
    }

    @Override
    public UsuarioDTO asignarPlan(Long usuarioId, Long planId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        if (usuario.getPlan() != null) {
            throw new RuntimeException("El usuario ya tiene un plan asignado. Debe cancelarlo o cambiarlo.");
        }

        usuario.setPlan(plan);
        Usuario actualizado = usuarioRepository.save(usuario);


        UsuarioDTO dto = modelMapper.map(actualizado, UsuarioDTO.class);
        dto.setPlanId(plan.getPlanId());
        return dto;
    }

    @Override
    public LoginDTO login(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmailUsuario(loginDTO.getEmail());

        if (usuario == null || !passwordEncoder.matches(loginDTO.getPassword(), usuario.getPasswordUsuario())) {
            LoginDTO respuesta = new LoginDTO();
            respuesta.setMensaje("Correo o contraseña incorrectos");
            return respuesta;
        }

        var userDetails = customUserDetailsService.loadUserByUsername(usuario.getEmailUsuario());

        String token = jwtUtil.generateToken(userDetails);

        LoginDTO respuesta = new LoginDTO();
        respuesta.setMensaje("Inicio de sesión exitoso");
        respuesta.setUsuarioId(usuario.getUsuarioId());
        respuesta.setNombreCompleto(usuario.getNombreUsuario() + " " + usuario.getApellidoUsuario());
        respuesta.setRol(usuario.getRol().getTipoRol());
        respuesta.setToken(token); // 👉 necesitas agregar este campo

        return respuesta;
    }
    @Override
    public UsuarioDTO buscarPorEmail(String email) {
        // 1. Buscamos al usuario en la base de datos usando el repositorio
        Usuario usuario = usuarioRepository.findByEmailUsuario(email);

        // 2. Verificamos si existe (debería, si el token es válido)
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con email: " + email);
        }

        // 3. Convertimos la entidad Usuario a UsuarioDTO
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);

        // 4. Asignamos los IDs de las relaciones (buena práctica que ya usas en modificarUsuario)
        if (usuario.getRol() != null) {
            dto.setRolId(usuario.getRol().getRolId());
        }
        if (usuario.getPlan() != null) {
            dto.setPlanId(usuario.getPlan().getPlanId());
        }

        // 5. Devolvemos el DTO con la información del perfil
        return dto;
    }
}
