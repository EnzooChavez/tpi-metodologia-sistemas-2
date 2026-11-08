package com.utn.trazabilidad.service;

import com.utn.trazabilidad.dto.RegistroMaterialDTO;
import com.utn.trazabilidad.entities.Material;
import com.utn.trazabilidad.entities.Usuario;
import com.utn.trazabilidad.repository.MaterialRepository;
import com.utn.trazabilidad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Material registrarMaterial(RegistroMaterialDTO dto) {
        // 1. Clean Code: Validaciones claras (Fail Fast)
        if (dto.getCodigoLote() == null || dto.getCodigoLote().length() < 5) {
            throw new IllegalArgumentException("Error: Lote inválido o muy corto.");
        }
        if (dto.getCantidad() <= 0) {
            throw new IllegalArgumentException("Error: La cantidad debe ser mayor a 0.");
        }

        // 2. Desacoplamiento de la base de datos
        Usuario operario = usuarioRepository.findByDni(dto.getDniOperario());
        if (operario == null) {
            throw new IllegalArgumentException("Error: Operario no encontrado en el sistema.");
        }

        // 3. Mapeo y aplicación de reglas de negocio
        Material material = new Material();
        material.setNombre(dto.getNombrePieza());
        material.setCantidad(dto.getCantidad());
        material.setResponsable(operario.getNombreCompleto());
        material.setEstado("INGRESADO");
        material.setPrioridad(dto.getPrioridad());
        material.setRequiereInspeccion(dto.isRequiereInspeccion());

        // Delegamos la generación del código a la propia entidad (Move Method)
        material.generarCodigoBase(dto.getCodigoLote());

        // 4. Persistencia
        return materialRepository.save(material);
    }
}