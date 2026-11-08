package com.utn.trazabilidad.repository;
import com.utn.trazabilidad.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MaterialRepository extends JpaRepository<Material, Long> {
}