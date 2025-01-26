package com.desafio.tokiomarine.api.transferencia.repositories;

import com.desafio.tokiomarine.api.transferencia.models.Transferencia;
import com.desafio.tokiomarine.api.transferencia.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    List<Transferencia> findByContaOrigem(Usuario contaOrigem);

    List<Transferencia> findByContaDestino(Usuario contaDestino);
}