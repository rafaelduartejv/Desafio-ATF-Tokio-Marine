package com.desafio.tokiomarine.api.login.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private Long id;
    private String email;
    private String numeroConta;
}