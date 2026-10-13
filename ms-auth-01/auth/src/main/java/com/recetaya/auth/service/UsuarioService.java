package com.recetaya.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.recetaya.auth.model.Usuario;
import com.recetaya.auth.repository.UsuarioRepository;

@Service 
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario (Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorId (Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public void eliminarUsuario (Long id){
        usuarioRepository.deleteById(id);
    }
}