package com.upc.ecochipstf.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "idusuario", nullable = false)
    private Long usuarioId;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombreUsuario;

    @Column(name = "apellido", length = 100, nullable = false)
    private String apellidoUsuario;

    @Column(name = "correo", length = 100, nullable = false)
    private String emailUsuario;

    @Column(name = "password", nullable = false)
    private String passwordUsuario;

    @Column(name = "edad", length = 10, nullable = false)
    private int edadUsuario;

    @Column(name = "estado", length = 50, nullable = false)
    private String estado;

    @Column(name = "ecobits", nullable = false)
    private Long ecobits = 0L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "plan_id", nullable = true)
    private Plan plan;

    @Column(name = "fecha_inicio_plan")
    private LocalDate fechaInicioPlan;

    @Column(name = "fecha_fin_plan")
    private LocalDate fechaFinPlan;
    @ManyToMany(mappedBy = "miembros", fetch = FetchType.LAZY)
    private List<Comunidad> comunidades;//
}
