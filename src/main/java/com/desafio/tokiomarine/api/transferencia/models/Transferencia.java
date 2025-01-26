package com.desafio.tokiomarine.api.transferencia.models;

import com.desafio.tokiomarine.api.transferencia.dtos.TransferenciaDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id", nullable = false)
    private Usuario contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id", nullable = false)
    private Usuario contaDestino;

    private BigDecimal valor;
    private BigDecimal taxa;
    private LocalDate dataTransferencia;
    private LocalDate dataAgendamento;

    public Transferencia() {
    }

    public Transferencia(TransferenciaDTO dto, Usuario contaOrigem, Usuario contaDestino) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = dto.getValor();
        this.dataTransferencia = dto.getDataTransferencia();
        this.dataAgendamento = LocalDate.now();
    }
}