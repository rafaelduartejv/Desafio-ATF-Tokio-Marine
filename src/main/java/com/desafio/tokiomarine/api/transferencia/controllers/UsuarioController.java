package com.desafio.tokiomarine.api.transferencia.controllers;

import com.desafio.tokiomarine.api.transferencia.models.Usuario;
import com.desafio.tokiomarine.api.transferencia.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        String numeroConta = UUID.randomUUID().toString().replaceAll("[^0-9]", "");

        if (numeroConta.length() > 9) {
            numeroConta = numeroConta.substring(0, 9);
            StringBuilder sb = new StringBuilder(numeroConta);
            while (sb.length() < 9) {
                sb.insert(0, "0");
            }
            numeroConta = sb.toString();
        }
        usuario.setNumeroConta(numeroConta);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario novoUsuario = usuarioRepository.save(usuario);

        return ResponseEntity.ok(novoUsuario);
    }
}
