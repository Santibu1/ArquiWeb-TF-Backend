package com.upc.ecochipstf.services;

import com.upc.ecochipstf.dto.ReporteParticipacionDTO;
import com.upc.ecochipstf.dto.UsuarioEventoDTO;
import com.upc.ecochipstf.entities.Evento;
import com.upc.ecochipstf.entities.Usuario;
import com.upc.ecochipstf.entities.UsuarioEvento;
import com.upc.ecochipstf.interfaces.IUsuarioEventoService;
import com.upc.ecochipstf.repositorios.EventoRepository;
import com.upc.ecochipstf.repositorios.UsuarioEventoRepository;
import com.upc.ecochipstf.repositorios.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UsuarioEventoService implements IUsuarioEventoService {
    @Autowired
    private UsuarioEventoRepository usuarioEventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioEventoDTO inscribirseEnEvento(Long usuarioId, Long eventoId) {
        usuarioEventoRepository.findByUsuarioUsuarioIdAndEventoEventoId(usuarioId, eventoId)
                .ifPresent(p -> { throw new RuntimeException("El usuario ya está inscrito en este evento"); });

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

        //Validar si el usuario pertenece a la comunidad del evento
        boolean pertenece = evento.getComunidad().getMiembros().stream()
                .anyMatch(miembro -> miembro.getUsuarioId().equals(usuarioId));

        if (!pertenece) {
            throw new RuntimeException("El usuario no pertenece a la comunidad del evento");
        }

        UsuarioEvento inscripcion = new UsuarioEvento();
        inscripcion.setUsuario(usuario);
        inscripcion.setEvento(evento);
        inscripcion.setEstado("Inscrito");
        inscripcion.setPuntosGanados(0);

        UsuarioEvento guardada = usuarioEventoRepository.save(inscripcion);
        return modelMapper.map(guardada, UsuarioEventoDTO.class);
    }

    @Override
    public List<UsuarioEventoDTO> listarEventosPorUsuario(Long usuarioId) {
        List<UsuarioEvento> lista = usuarioEventoRepository.findByUsuarioUsuarioId(usuarioId);

        return lista.stream()
                .map(e -> {
                    UsuarioEventoDTO dto = modelMapper.map(e, UsuarioEventoDTO.class);
                    // --- AGREGA ESTA LÍNEA MANUALMENTE ---
                    // A veces el mapper no conecta 'evento.eventoId' con 'eventoId' del DTO
                    if(e.getEvento() != null) {
                        dto.setEventoId(e.getEvento().getEventoId());
                        dto.setNombreUsuario(e.getUsuario().getNombreUsuario());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void cancelarInscripcion(Long usuarioId, Long eventoId) {
        UsuarioEvento inscripcion = usuarioEventoRepository
                .findByUsuarioUsuarioIdAndEventoEventoId(usuarioId, eventoId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));

        inscripcion.setEstado("Cancelado");
        usuarioEventoRepository.save(inscripcion);
    }
    @Override
    public ReporteParticipacionDTO obtenerReporteMensual(Long usuarioId, int mes, int anio) {
        List<UsuarioEvento> participaciones = usuarioEventoRepository.findParticipacionesMensuales(usuarioId, mes, anio);

        int totalEventos = participaciones.size();
        int totalPuntos = participaciones.stream()
                .mapToInt(UsuarioEvento::getPuntosGanados)
                .sum();

        List<String> nombresEventos = participaciones.stream()
                .map(p -> p.getEvento().getNombre())
                .collect(Collectors.toList());

        return new ReporteParticipacionDTO(
                usuarioId,
                mes,
                anio,
                totalEventos,
                totalPuntos,
                nombresEventos
        );
    }

    @Override
    public List<UsuarioEventoDTO> listarParticipantesPorEvento(Long eventoId) {
        List<UsuarioEvento> inscripciones = usuarioEventoRepository.findByEventoEventoId(eventoId);

        return inscripciones.stream()
                .map(ue -> {
                    UsuarioEventoDTO dto = modelMapper.map(ue, UsuarioEventoDTO.class);
                    // Mapeo manual del nombre porque modelMapper a veces falla en relaciones profundas
                    dto.setNombreUsuario(ue.getUsuario().getNombreUsuario());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    //
}