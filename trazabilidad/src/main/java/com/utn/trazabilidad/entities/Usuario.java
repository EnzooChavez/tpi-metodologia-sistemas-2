package com.utn.trazabilidad.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    private String dni;
    private String nombreCompleto;
    private String rol;
}