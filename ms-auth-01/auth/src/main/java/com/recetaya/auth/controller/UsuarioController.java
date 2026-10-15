package com.recetaya.auth.controller;

import com.recetaya.auth.AuthApplication;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.recetaya.auth.model.Usuario;
import com.recetaya.auth.service.UsuarioService;



@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final AuthApplication authApplication;
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService, AuthApplication authApplication) {
        this.usuarioService = usuarioService;
        this.authApplication = authApplication;
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/validacion")
    public ResponseEntity<String> validarRol(
        @RequestHeader("X-ROLE") String rol){
        if (!rol.equals("MEDICO") && !rol.equals("FARMACEUTICO")){
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Rol no autorizado");
            
        }

        return ResponseEntity.ok("Rol autorizado: " + rol);
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario datosLogin) {
        Usuario usuario = usuarioService.login(
           datosLogin.getEmail(),
           datosLogin.getContrasena() 
        );

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
         return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
        @PathVariable Long id,
        @RequestBody Usuario usuarioActualizado) {

        Usuario usuario = usuarioService.actualizarUsuario(id, usuarioActualizado);

        if (usuario == null) {
           return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }

    
}
