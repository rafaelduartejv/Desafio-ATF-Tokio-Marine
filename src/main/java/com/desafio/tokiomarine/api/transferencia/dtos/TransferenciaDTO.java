package com.desafio.tokiomarine.api.transferencia.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransferenciaDTO {
    private Long id;
    private BigDecimal valor;
    private BigDecimal taxa;
    private LocalDate dataTransferencia;
    private LocalDate dataAgendamento;
    private String contaOrigem;
    private String contaDestino;

    public TransferenciaDTO() {
    }

    public TransferenciaDTO(Long id, BigDecimal valor, BigDecimal taxa, LocalDate dataTransferencia,
                            LocalDate dataAgendamento, String contaOrigem, String contaDestino) {
        this.id = id;
        this.valor = valor;
        this.taxa = taxa;
        this.dataTransferencia = dataTransferencia;
        this.dataAgendamento = dataAgendamento;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
    }
}
