package com.desafio.tokiomarine.api.transferencia.repositories;

import com.desafio.tokiomarine.api.transferencia.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNumeroConta(String numeroConta);

    Optional<Usuario> findByEmail(String email);
}