package com.upc.ecochipstf.repositorios;

import com.upc.ecochipstf.entities.Comunidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComunidadRepository extends JpaRepository<Comunidad, Long> {
    List<Comunidad> findByEstado(String estado);
    List<Comunidad> findByNombreContainingIgnoreCase(String nombre);
    @Query("SELECT c FROM Comunidad c JOIN c.miembros m WHERE m.usuarioId = :userId")
    Comunidad findByMiembroId(@Param("userId") Long userId);
}
