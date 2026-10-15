package com.recetaya.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.recetaya.auth.model.Usuario;
import java.util.List;


public interface UsuarioRepository extends JpaRepository <Usuario, Long> { 
    Optional<Usuario> findByEmail(String Email);
}