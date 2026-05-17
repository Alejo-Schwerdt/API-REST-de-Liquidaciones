package com.transportista.liquidaciones.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transportista.liquidaciones.entity.Usuario;

import java.util.Optional;

    @Repository
    public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

        Optional<Usuario> findByEmail(String email);
    }   
