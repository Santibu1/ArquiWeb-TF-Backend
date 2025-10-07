package com.upc.ecochipstf.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventoId;

    private String nombre;
    private String descripcion;
    private String ubicacion;
    private LocalDate fecha;
    private String organizador;
    private String beneficios;
    private Integer recompensa;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "comunidad_id")
    private Comunidad comunidad;
}
