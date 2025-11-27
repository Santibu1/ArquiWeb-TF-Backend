package com.upc.ecochipstf.interfaces;

import com.upc.ecochipstf.dto.ComunidadDTO;
import com.upc.ecochipstf.dto.MiembroDTO;

import java.util.List;

public interface IComunidadService {
    public ComunidadDTO crearComunidad(ComunidadDTO comunidadDTO); // Admin
    public ComunidadDTO obtenerComunidadPorId(Long id);
    public List<ComunidadDTO> listarComunidades();
    public ComunidadDTO actualizarComunidad(ComunidadDTO comunidadDTO);
    public void eliminarComunidad(Long id);

    public MiembroDTO unirUsuarioAComunidad(MiembroDTO miembroDTO);
    public List<MiembroDTO> listarComunidadesPorUsuario(Long usuarioId);
    public ComunidadDTO obtenerComunidadPorMiembro(Long userId);

    public List<MiembroDTO> listarMiembrosPorComunidad(Long idComunidad);
}
