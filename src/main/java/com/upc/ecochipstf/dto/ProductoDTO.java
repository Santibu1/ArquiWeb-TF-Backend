package com.upc.ecochipstf.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ProductoDTO {

    private Long productoId;
    private String nombre;
    private String categoria;
    private String urlImagen;
    @NotNull(message = "El precio no puede ser nula")
    @Min(value = 1, message = "El precio mínimo es 1 sol")
    private Double precio;
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    private String estado;
    private Long empresaId;
}
