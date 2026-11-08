package com.utn.trazabilidad.controller;

import com.utn.trazabilidad.dto.RegistroMaterialDTO;
import com.utn.trazabilidad.entities.Material;
import com.utn.trazabilidad.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/terminacion")
public class MaterialTrazabilidadController {

    @Autowired
    private MaterialService materialService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarMaterialTerminacion(@RequestBody RegistroMaterialDTO requestDto) {
        try {
            // El controlador solo delega al servicio (Aca apliqué Extract Method)
            Material materialRegistrado = materialService.registrarMaterial(requestDto);

            return ResponseEntity.ok("Material registrado con éxito. Código generado: " + materialRegistrado.getCodigoBase());

        } catch (IllegalArgumentException e) {
            // Aca mejoré con Clean Code: Manejo específico de errores de validación
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Y aca manejo de errores no controlados
            return ResponseEntity.internalServerError().body("Error interno en el servidor al registrar el material.");
        }
    }
}