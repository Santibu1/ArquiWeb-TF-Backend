package com.upc.ecochipstf.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudDTO {
    private Long idSolicitud;
    private Long idModerador;
    private Long idAdministrador;
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombreComunidad;
    private String ubicacion;
    @NotNull(message = "La descripción no puede ser nula")
    @Size(min = 2, max = 50, message = "La descripción debe tener entre 2 y 50 caracteres")
    private String descripcion;
    private String estado;
    private LocalDate fechaCreacion;
    private LocalDate fechaRevision;
    private String mensaje;
    private boolean success;
}
