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
    private String descripcion; // breve descripción del evento
    private String ubicacion;   // lugar del evento
    private LocalDate fecha;    // fecha del evento
    private String organizador; // quién lo organiza
    private String beneficios;  // puntos o beneficios por participar
    private Integer recompensa;  // recompensa adicional, si aplica
    private String estado;      // “Próximo”, “Finalizado”, etc.
    @ManyToOne
    @JoinColumn(name = "comunidad_id")
    private Comunidad comunidad; //
    //
}
