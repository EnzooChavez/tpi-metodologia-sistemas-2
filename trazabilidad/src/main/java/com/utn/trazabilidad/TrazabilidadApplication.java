package com.utn.trazabilidad;

import com.utn.trazabilidad.entities.Usuario;
import com.utn.trazabilidad.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrazabilidadApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrazabilidadApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UsuarioRepository usuarioRepository) {
        return args -> {
            // Creamos un operario de prueba si no existe
            if (usuarioRepository.findByDni("12345678") == null) {
                Usuario u = new Usuario();
                u.setDni("12345678");
                u.setNombreCompleto("Juan Perez");
                u.setRol("OPERARIO_TERMINACION");
                usuarioRepository.save(u);
            }
        };
    }
}