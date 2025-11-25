package com.upc.ecochipstf.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioProductoResponseDTO {
    private Long id;
    private String producto;
    private String urlImagen;
    private String estado;
    private Integer ecobitsUtilizados;
    private LocalDateTime fecha;
}
