package com.upc.ecochipstf.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Long usuarioId;
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombreUsuario;
    @NotNull(message = "El apellido no puede ser nulo")
    @Size(min = 2, max = 50, message = "El Apellido debe tener entre 2 y 50 caracteres")
    private String apellidoUsuario;
    @Email(message = "El email debe ser válido")
    private String emailUsuario;
    private String passwordUsuario;
    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 14, message = "La edad mínima es 14 años")
    @Max(value = 60, message = "La edad máxima es 59 años")
    private Integer edadUsuario;
    private String estado;
    private Long ecobits;

    private LocalDate fechaInicioPlan;
    private LocalDate fechaFinPlan;
    private Boolean planActivo;
    private Long diasRestantes;


    private Long rolId;
    private Long planId;
}
