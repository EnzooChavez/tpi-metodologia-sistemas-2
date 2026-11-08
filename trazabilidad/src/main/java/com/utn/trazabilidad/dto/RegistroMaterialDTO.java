package com.utn.trazabilidad.dto;

import lombok.Data;

@Data
public class RegistroMaterialDTO {
    private String codigoLote;
    private String nombrePieza;
    private int cantidad;
    private String dniOperario;
    private String prioridad;
    private boolean requiereInspeccion;
}