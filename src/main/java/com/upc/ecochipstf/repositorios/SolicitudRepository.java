package com.upc.ecochipstf.repositorios;

import com.upc.ecochipstf.entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstado(String estado);
    List<Solicitud> findByModerador_UsuarioId(Long moderadorId);
    List<Solicitud> findByAdministrador_UsuarioId(Long adminId);
    @Query("SELECT s FROM Solicitud s WHERE s.moderador.usuarioId = :idModerador AND s.estado IN ('Pendiente', 'Aprobada')")
    List<Solicitud> findSolicitudesActivas(Long idModerador);
}
