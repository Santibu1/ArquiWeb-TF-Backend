package com.upc.ecochipstf.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoDTO {
    private Long eventoId;
    @NotNull(message = "El nombre no puede ser nula")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private LocalDate fecha;
    private String organizador;
    private String beneficios;
    private Integer recompensa;
    private String estado;
    private Long comunidadId;
}
