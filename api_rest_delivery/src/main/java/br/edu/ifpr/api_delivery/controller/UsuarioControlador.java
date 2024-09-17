package br.edu.ifpr.api_delivery.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpr.api_delivery.model.Usuario;
import br.edu.ifpr.api_delivery.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

    @PostMapping("/registrar")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody Usuario usuario) {
        //ver se email ja esta cadastrado
        if (usuarioRepositorio.findByEmail(usuario.getEmail()).isPresent()) {
            return new ResponseEntity<>("Usuário já cadastrado", HttpStatus.BAD_REQUEST);
        }
    
        // ver se é no mino 8 caracteres
        if (usuario.getSenha() == null || usuario.getSenha().length() < 8) {
            return new ResponseEntity<>("A senha deve ter no mínimo 8 caracteres", HttpStatus.BAD_REQUEST);
        }
    
        // codificacao de senha
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepositorio.save(usuario);
        
        return new ResponseEntity<>("Usuário cadastrado com sucesso!", HttpStatus.CREATED);
    }
    

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {

        Optional<Usuario> usuarioExistente = usuarioRepositorio.findByEmail(usuario.getEmail());


        if (usuarioExistente.isPresent() && encoder.matches(usuario.getSenha(), usuarioExistente.get().getSenha())) {
            String token = UUID.randomUUID().toString(); 
            usuarioExistente.get().setChave(token);      
            usuarioRepositorio.save(usuarioExistente.get());
            return new ResponseEntity<>(token, HttpStatus.OK);  
        } else {
            return new ResponseEntity<>("Login ou senha inválidos", HttpStatus.UNAUTHORIZED);
        }
    }
}
