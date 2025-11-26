package com.upc.ecochipstf.services;

import com.upc.ecochipstf.dto.ComunidadDTO;
import com.upc.ecochipstf.dto.MiembroDTO;
import com.upc.ecochipstf.entities.Comunidad;
import com.upc.ecochipstf.entities.Solicitud;
import com.upc.ecochipstf.entities.Usuario;
import com.upc.ecochipstf.interfaces.IComunidadService;
import com.upc.ecochipstf.repositorios.ComunidadRepository;
import com.upc.ecochipstf.repositorios.SolicitudRepository;
import com.upc.ecochipstf.repositorios.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComunidadService implements IComunidadService {

    @Autowired
    private ComunidadRepository comunidadRepository;
    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public ComunidadDTO crearComunidad(ComunidadDTO comunidadDTO) {
        Comunidad comunidad = modelMapper.map(comunidadDTO, Comunidad.class);

        // Vincular solicitud (ya aprobada)
        Solicitud solicitud = solicitudRepository.findById(comunidadDTO.getIdSolicitud())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        comunidad.setSolicitud(solicitud);
        comunidad.setEstado("Activa");

        comunidad = comunidadRepository.save(comunidad);
        return modelMapper.map(comunidad, ComunidadDTO.class);
    }

    @Override
    public ComunidadDTO obtenerComunidadPorId(Long id) {
        Comunidad comunidad = comunidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comunidad no encontrada"));
        return modelMapper.map(comunidad, ComunidadDTO.class);
    }

    @Override
    public List<ComunidadDTO> listarComunidades() {
        return comunidadRepository.findAll()
                .stream()
                .map(comunidad -> modelMapper.map(comunidad, ComunidadDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ComunidadDTO actualizarComunidad(ComunidadDTO comunidadDTO) {
        Comunidad comunidad = modelMapper.map(comunidadDTO, Comunidad.class);
        comunidad = comunidadRepository.save(comunidad);
        return modelMapper.map(comunidad, ComunidadDTO.class);
    }

    @Override
    public void eliminarComunidad(Long id) {
        comunidadRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MiembroDTO unirUsuarioAComunidad(MiembroDTO miembroDTO) {
        Comunidad comunidad = comunidadRepository.findById(miembroDTO.getIdComunidad())
                .orElseThrow(() -> new RuntimeException("Comunidad no encontrada"));

        Usuario usuario = usuarioRepository.findById(miembroDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean perteneceOtraComunidad = comunidadRepository.findAll()
                .stream()
                .anyMatch(c -> c.getMiembros() != null &&
                        c.getMiembros().stream()
                                .anyMatch(u -> u.getUsuarioId().equals(usuario.getUsuarioId()))
                );

        if (perteneceOtraComunidad) {
            throw new RuntimeException("El usuario ya pertenece a una comunidad, no puede unirse a otra.");
        }

        if (comunidad.getMiembros() == null) {
            comunidad.setMiembros(new ArrayList<>());
        }

        boolean yaMiembro = comunidad.getMiembros().stream()
                .anyMatch(u -> u.getUsuarioId().equals(usuario.getUsuarioId()));

        if (!yaMiembro) {
            comunidad.getMiembros().add(usuario);
            comunidadRepository.save(comunidad);
        }

        MiembroDTO respuesta = new MiembroDTO();
        respuesta.setIdComunidad(comunidad.getIdComunidad());
        respuesta.setNombreComunidad(comunidad.getNombre());
        respuesta.setUbicacion(comunidad.getUbicacion());
        respuesta.setDescripcion(comunidad.getDescripcion());
        respuesta.setEstado(comunidad.getEstado());
        respuesta.setIdUsuario(usuario.getUsuarioId());
        respuesta.setNombreUsuario(usuario.getNombreUsuario());

        return respuesta;
    }

    @Override
    public List<MiembroDTO> listarComunidadesPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Comunidad> comunidades = comunidadRepository.findAll()
                .stream()
                .filter(c -> c.getMiembros() != null &&
                        c.getMiembros().stream().anyMatch(u -> u.getUsuarioId().equals(usuarioId)))
                .collect(Collectors.toList());

        return comunidades.stream().map(c -> {
            MiembroDTO dto = new MiembroDTO();
            dto.setIdComunidad(c.getIdComunidad());
            dto.setNombreComunidad(c.getNombre());
            dto.setUbicacion(c.getUbicacion());
            dto.setDescripcion(c.getDescripcion());
            dto.setEstado(c.getEstado());
            dto.setIdUsuario(usuario.getUsuarioId());
            dto.setNombreUsuario(usuario.getNombreUsuario());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public ComunidadDTO obtenerComunidadPorMiembro(Long userId) {
        Comunidad comunidad = comunidadRepository.findByMiembroId(userId);
        return modelMapper.map(comunidad, ComunidadDTO.class);
    }
}
