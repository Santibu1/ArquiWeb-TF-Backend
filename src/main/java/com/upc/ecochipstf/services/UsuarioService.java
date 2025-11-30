package com.upc.ecochipstf.services;

import com.upc.ecochipstf.dto.LoginDTO;
import com.upc.ecochipstf.dto.UsuarioDTO;
import com.upc.ecochipstf.entities.Plan;
import com.upc.ecochipstf.entities.Usuario;
import com.upc.ecochipstf.interfaces.IUsuarioService;
import com.upc.ecochipstf.repositorios.PlanRepository;
import com.upc.ecochipstf.repositorios.UsuarioRepository;
import com.upc.ecochipstf.security.services.CustomUserDetailsService;
import com.upc.ecochipstf.security.util.JwtUtil;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


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

    /* ---------------------MÉTODOS AUXILIARES--------------------- */

    public UsuarioDTO obtenerEstadoPlan(String email) {
        Usuario usuario = usuarioRepository.findByEmailUsuario(email);
        UsuarioDTO dto = mapearUsuarioDTO(usuario);
        return dto;
    }

    private void inicializarFechasPlan(Usuario usuario, Plan plan) {
        LocalDate hoy = LocalDate.now();
        usuario.setFechaInicioPlan(hoy);
        usuario.setFechaFinPlan(hoy.plusDays(plan.getDuracionDias()));
    }

    private boolean esPlanActivo(Usuario usuario) {
        if (usuario.getPlan() == null || usuario.getFechaFinPlan() == null) return false;
        return !LocalDate.now().isAfter(usuario.getFechaFinPlan());
    }

    private long calcularDiasRestantes(Usuario usuario) {
        if (!esPlanActivo(usuario)) return 0L;
        return ChronoUnit.DAYS.between(LocalDate.now(), usuario.getFechaFinPlan());
    }

    private UsuarioDTO mapearUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);

        if (usuario.getRol() != null) dto.setRolId(usuario.getRol().getRolId());
        if (usuario.getPlan() != null) dto.setPlanId(usuario.getPlan().getPlanId());

        dto.setFechaInicioPlan(usuario.getFechaInicioPlan());
        dto.setFechaFinPlan(usuario.getFechaFinPlan());
        dto.setPlanActivo(esPlanActivo(usuario));
        dto.setDiasRestantes(calcularDiasRestantes(usuario));

        return dto;
    }



    @Override
    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {

        Usuario usuarioExistente = usuarioRepository.findByEmailUsuario(usuarioDTO.getEmailUsuario());

        if (usuarioExistente != null) {
            if ("Desactivado".equalsIgnoreCase(usuarioExistente.getEstado())) {

                usuarioExistente.setEstado("Activo");
                usuarioExistente.setEcobits(0L);
                usuarioExistente.setNombreUsuario(usuarioDTO.getNombreUsuario());
                usuarioExistente.setApellidoUsuario(usuarioDTO.getApellidoUsuario());

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

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> listarModeradores() {
        return usuarioRepository.findByRol_TipoRol("MODERADOR")
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTO> listarClientes() {
        return usuarioRepository.findByRol_TipoRol("CLIENTE")
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO reactivarUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setEstado("Activo");
        usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioDTO.class);
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

            // Si el usuario NO tenía plan antes → inicializar fechas
            if (usuario.getPlan() == null) {
                Plan plan = planRepository.findById(usuarioDTO.getPlanId())
                        .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
                usuario.setPlan(plan);
                usuario.setFechaInicioPlan(LocalDate.now());
                usuario.setFechaFinPlan(LocalDate.now().plusDays(plan.getDuracionDias()));
            }
        }

        Usuario actualizado = usuarioRepository.save(usuario);

        UsuarioDTO dto = modelMapper.map(actualizado, UsuarioDTO.class);
        dto.setRolId(usuario.getRol() != null ? usuario.getRol().getRolId() : null);
        dto.setPlanId(usuario.getPlan() != null ? usuario.getPlan().getPlanId() : null);

        return dto;
    }

    @Override
    @Transactional
    public UsuarioDTO asignarPlan(Long usuarioId, Long planId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        // Asignar fechas del plan
        LocalDate hoy = LocalDate.now();

        usuario.setPlan(plan);
        usuario.setFechaInicioPlan(hoy);
        usuario.setFechaFinPlan(hoy.plusDays(plan.getDuracionDias())); // EJ: 7 días
        // Guardar
        Usuario actualizado = usuarioRepository.save(usuario);

        // Calcular
        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), usuario.getFechaFinPlan());
        boolean activo = !LocalDate.now().isAfter(usuario.getFechaFinPlan());

        UsuarioDTO dto = modelMapper.map(actualizado, UsuarioDTO.class);
        dto.setPlanId(plan.getPlanId());
        dto.setFechaInicioPlan(usuario.getFechaInicioPlan());
        dto.setFechaFinPlan(usuario.getFechaFinPlan());
        dto.setDiasRestantes(diasRestantes);
        dto.setPlanActivo(activo);

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

        if (usuario.getEstado() != null && usuario.getEstado().equalsIgnoreCase("Desactivado")) {
            LoginDTO respuesta = new LoginDTO();
            respuesta.setMensaje("Tu cuenta está suspendida. Contacta al administrador.");
            return respuesta;
        }

        var userDetails = customUserDetailsService.loadUserByUsername(usuario.getEmailUsuario());
        String token = jwtUtil.generateToken(userDetails);

        LoginDTO respuesta = new LoginDTO();
        respuesta.setMensaje("Inicio de sesión exitoso");
        respuesta.setUsuarioId(usuario.getUsuarioId());
        respuesta.setNombreCompleto(usuario.getNombreUsuario() + " " + usuario.getApellidoUsuario());
        respuesta.setRol(usuario.getRol().getTipoRol());
        respuesta.setToken(token);

        return respuesta;
    }

    @Override
    public UsuarioDTO buscarPorEmail(String email) {

        Usuario usuario = usuarioRepository.findByEmailUsuario(email);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con email: " + email);
        }

        // Si el plan ya expiró → eliminar plan automáticamente
        if (usuario.getPlan() != null && usuario.getFechaFinPlan() != null) {
            if (LocalDate.now().isAfter(usuario.getFechaFinPlan())) {

                usuario.setPlan(null);
                usuario.setFechaInicioPlan(null);
                usuario.setFechaFinPlan(null);

                usuarioRepository.save(usuario);
            }
        }

        return mapearUsuarioDTO(usuario);
    }

}
