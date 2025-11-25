package com.upc.ecochipstf.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;
    private String nombre;
    private String categoria;
    @Column(length = 500) // Un poco largo por si la URL es extensa
    private String urlImagen;
    private double precio;
    private Integer stock;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

}
