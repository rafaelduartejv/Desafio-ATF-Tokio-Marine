package com.desafio.tokiomarine.api.transferencia.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
}
