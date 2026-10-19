package com.recetaya.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.recetaya.auth.model.Usuario;
import com.recetaya.auth.repository.UsuarioRepository;


import com.recetaya.auth.exception.UsuarioException;
import com.recetaya.auth.exception.CredencialException;

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
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioException(id));
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
        throw new UsuarioException(id);
        }

        usuarioRepository.deleteById(id);
    }   

    public Usuario actualizarUsuario (Long id, Usuario usuarioActualizado){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioException(id));

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setContrasena(usuarioActualizado.getContrasena());
        usuario.setRol(usuarioActualizado.getRol());

        return usuarioRepository.save(usuario);
    }

    public Usuario login(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioException(email));

        if (!usuario.getContrasena().equals(password)) {
            throw new CredencialException();
        }

        return usuario;
    }
}