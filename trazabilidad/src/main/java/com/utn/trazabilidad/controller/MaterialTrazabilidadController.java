package com.utn.trazabilidad.controller;

import com.utn.trazabilidad.entities.Material;
import com.utn.trazabilidad.entities.Usuario;
import com.utn.trazabilidad.repository.MaterialRepository;
import com.utn.trazabilidad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/terminacion")
public class MaterialTrazabilidadController {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarMaterialTerminacion(
            @RequestParam String codigoLote,
            @RequestParam String nombrePieza,
            @RequestParam int cantidad,
            @RequestParam String dniOperario,
            @RequestParam String prioridad,
            @RequestParam boolean requiereInspeccion) {

        try {
            if (codigoLote == null || codigoLote.length() < 5) {
                return ResponseEntity.badRequest().body("Error: Lote inválido o muy corto.");
            }
            if (cantidad <= 0) {
                return ResponseEntity.badRequest().body("Error: La cantidad debe ser mayor a 0.");
            }

            Usuario operario = usuarioRepository.findByDni(dniOperario);
            if (operario == null) {
                return ResponseEntity.badRequest().body("Error: Operario no encontrado en el sistema.");
            }

            String prefijo = requiereInspeccion ? "TERM-INSP-" : "TERM-STD-";
            String codigoGenerado = prefijo + codigoLote.toUpperCase() + "-" + LocalDate.now().getYear();

            Material material = new Material();
            material.setCodigoBase(codigoGenerado);
            material.setNombre(nombrePieza);
            material.setCantidad(cantidad);
            material.setResponsable(operario.getNombreCompleto());
            material.setEstado("INGRESADO");
            material.setPrioridad(prioridad);
            material.setRequiereInspeccion(requiereInspeccion);

            materialRepository.save(material);

            return ResponseEntity.ok("Material registrado con éxito. Código generado: " + codigoGenerado);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error interno en el servidor.");
        }
    }
}