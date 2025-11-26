package com.upc.ecochipstf.services;

import com.upc.ecochipstf.dto.MensajeDTO;
import com.upc.ecochipstf.entities.Comunidad;
import com.upc.ecochipstf.entities.Mensaje;
import com.upc.ecochipstf.entities.Usuario;
import com.upc.ecochipstf.interfaces.IMensajeService;
import com.upc.ecochipstf.repositorios.ComunidadRepository;
import com.upc.ecochipstf.repositorios.MensajeRepository;
import com.upc.ecochipstf.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensajeService implements IMensajeService {
    @Autowired
    private MensajeRepository mensajeRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ComunidadRepository comunidadRepository;
    @Autowired
    private ModelMapper modelMapper;

    public MensajeService(MensajeRepository mensajeRepository,
                          UsuarioRepository usuarioRepository,
                          ComunidadRepository comunidadRepository,
                          ModelMapper modelMapper) {
        this.mensajeRepository = mensajeRepository;
        this.usuarioRepository = usuarioRepository;
        this.comunidadRepository = comunidadRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public MensajeDTO enviarMensaje(MensajeDTO dto, String usuarioEmail) {
        Usuario usuario = usuarioRepository.findByEmailUsuario(usuarioEmail);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Comunidad comunidad = comunidadRepository.findById(dto.getComunidadId())
                .orElseThrow(() -> new RuntimeException("Comunidad no encontrada"));

        boolean miembro = comunidad.getMiembros()
                .stream()
                .anyMatch(u -> u.getUsuarioId().equals(usuario.getUsuarioId()));

        if (!miembro) {
            throw new RuntimeException("El usuario no es miembro de la comunidad");
        }

        Mensaje mensaje = new Mensaje();
        mensaje.setContenido(dto.getContenido());
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensaje.setUsuario(usuario);
        mensaje.setComunidad(comunidad);

        Mensaje guardado = mensajeRepository.save(mensaje);
        MensajeDTO res = modelMapper.map(guardado, MensajeDTO.class);
        res.setUsuarioId(usuario.getUsuarioId());
        res.setUsuarioNombre(usuario.getNombreUsuario());
        return res;
    }

    @Override
    public List<MensajeDTO> listarMensajesPorComunidad(Long comunidadId) {
        List<Mensaje> mensajes = mensajeRepository.findByComunidad_IdComunidadOrderByFechaEnvioAsc(comunidadId);
        return mensajes.stream()
                .map(m -> {
                    MensajeDTO dto = modelMapper.map(m, MensajeDTO.class);
                    dto.setUsuarioId(m.getUsuario().getUsuarioId());
                    dto.setUsuarioNombre(m.getUsuario().getNombreUsuario());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
