package com.utn.trazabilidad.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoBase;
    private String nombre;
    private int cantidad;
    private String responsable;
    private String estado;
    private String prioridad;
    private boolean requiereInspeccion;

    // Método de dominio: El material sabe cómo generar su propio código
    public void generarCodigoBase(String loteAnio) {
        String prefijo = this.requiereInspeccion ? "TERM-INSP-" : "TERM-STD-";
        this.codigoBase = prefijo + loteAnio.toUpperCase() + "-" + java.time.LocalDate.now().getYear();

}