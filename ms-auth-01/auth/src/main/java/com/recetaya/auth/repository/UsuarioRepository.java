package com.recetaya.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.recetaya.auth.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> { 
}