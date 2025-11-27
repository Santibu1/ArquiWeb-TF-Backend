package com.upc.ecochipstf.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MiembroDTO {
    private Long idComunidad;
    private Long idUsuario;
    private String nombreUsuario;
    private String rol;
    private String nombreComunidad;
    private String ubicacion;
    private String descripcion;
    private String estado;

    public MiembroDTO(Long idUsuario, String nombreUsuario, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }
}
