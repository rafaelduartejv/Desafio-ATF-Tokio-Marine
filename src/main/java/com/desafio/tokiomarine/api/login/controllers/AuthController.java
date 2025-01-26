package com.desafio.tokiomarine.api.login.controllers;

import com.desafio.tokiomarine.api.login.models.AuthRequest;
import com.desafio.tokiomarine.api.login.models.AuthResponse;
import com.desafio.tokiomarine.api.login.security.JwtUtil;
import com.desafio.tokiomarine.api.transferencia.dtos.UsuarioDTO;
import com.desafio.tokiomarine.api.transferencia.models.Usuario;
import com.desafio.tokiomarine.api.transferencia.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        UsuarioDTO usuarioDTO = authRequest.getUsuarioDTO();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(usuarioDTO.getEmail());
        if (optionalUsuario.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        Usuario usuario = optionalUsuario.get();
        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getNumeroConta(), usuario.getId());

        AuthResponse response = new AuthResponse(token, usuario.getId(), usuario.getEmail(), usuario.getNumeroConta());
        return ResponseEntity.ok(response);
    }
}