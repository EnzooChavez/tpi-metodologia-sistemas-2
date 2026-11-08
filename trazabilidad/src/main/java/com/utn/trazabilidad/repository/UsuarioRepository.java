package com.utn.trazabilidad.repository;
import com.utn.trazabilidad.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByDni(String dni);
}