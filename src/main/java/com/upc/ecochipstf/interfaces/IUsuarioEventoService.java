package com.upc.ecochipstf.interfaces;

import com.upc.ecochipstf.dto.ReporteParticipacionDTO;
import com.upc.ecochipstf.dto.UsuarioEventoDTO;

import java.util.List;

public interface IUsuarioEventoService {
    public UsuarioEventoDTO inscribirseEnEvento(Long usuarioId, Long eventoId);
    public List<UsuarioEventoDTO> listarEventosPorUsuario(Long usuarioId);
    public void cancelarInscripcion(Long usuarioId, Long eventoId);
    public ReporteParticipacionDTO obtenerReporteMensual(Long usuarioId, int mes, int anio);
    public List<UsuarioEventoDTO> listarParticipantesPorEvento(Long eventoId);
}//
//
