package com.upc.ecochipstf.services;

import com.upc.ecochipstf.dto.EventoDTO;
import com.upc.ecochipstf.entities.Comunidad;
import com.upc.ecochipstf.entities.Evento;
import com.upc.ecochipstf.interfaces.IEventoService;
import com.upc.ecochipstf.repositorios.ComunidadRepository;
import com.upc.ecochipstf.repositorios.EventoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService implements IEventoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ComunidadRepository comunidadRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public EventoDTO registrarEvento(EventoDTO eventoDTO) {
        Evento evento = modelMapper.map(eventoDTO, Evento.class);
        Comunidad comunidad = comunidadRepository.findById(eventoDTO.getComunidadId())
                .orElseThrow(() -> new RuntimeException("Comunidad no encontrada"));
        evento.setComunidad(comunidad);
        evento.setUbicacion(comunidad.getUbicacion()); // mismo lugar que la comunidad
        evento.setOrganizador(comunidad.getModerador().getNombreUsuario() + " " + comunidad.getModerador().getApellidoUsuario());
        evento.setEstado("Próximo");
        evento = eventoRepository.save(evento);
        return modelMapper.map(evento, EventoDTO.class);
    }

    @Override
    public List<EventoDTO> listarEventosPorComunidad(Long comunidadId) {
        List<Evento> eventos = eventoRepository.findByComunidadIdComunidad(comunidadId);
        return eventos.stream()
                .map(evento -> modelMapper.map(evento, EventoDTO.class))
                .collect(Collectors.toList());
    }
}