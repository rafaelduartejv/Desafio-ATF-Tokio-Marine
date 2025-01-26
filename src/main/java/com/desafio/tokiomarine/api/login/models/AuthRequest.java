package com.desafio.tokiomarine.api.login.models;

import com.desafio.tokiomarine.api.transferencia.dtos.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private UsuarioDTO usuarioDTO;
}