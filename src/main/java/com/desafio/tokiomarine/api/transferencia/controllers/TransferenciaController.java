package com.desafio.tokiomarine.api.transferencia.controllers;

import com.desafio.tokiomarine.api.transferencia.dtos.TransferenciaDTO;
import com.desafio.tokiomarine.api.transferencia.models.Transferencia;
import com.desafio.tokiomarine.api.transferencia.services.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService service;

    @PostMapping
    public ResponseEntity<TransferenciaDTO> agendarTransferencia(@RequestBody TransferenciaDTO dto) {
        Transferencia transferencia = service.agendarTransferencia(dto);

        TransferenciaDTO resposta = new TransferenciaDTO(
                transferencia.getId(),
                transferencia.getValor(),
                transferencia.getTaxa(),
                transferencia.getDataTransferencia(),
                transferencia.getDataAgendamento(),
                transferencia.getContaOrigem().getNumeroConta(),
                transferencia.getContaDestino().getNumeroConta()
        );

        return ResponseEntity.ok(resposta);
    }

    @GetMapping
    public ResponseEntity<List<TransferenciaDTO>> listarTransferencias() {
        List<Transferencia> transferencias = service.listarTransferencias();

        List<TransferenciaDTO> transferenciasDTO = transferencias.stream()
                .map(transferencia -> new TransferenciaDTO(
                        transferencia.getId(),
                        transferencia.getValor(),
                        transferencia.getTaxa(),
                        transferencia.getDataTransferencia(),
                        transferencia.getDataAgendamento(),
                        transferencia.getContaOrigem().getNumeroConta(),
                        transferencia.getContaDestino().getNumeroConta()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(transferenciasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaDTO> buscarTransferenciaPorId(@PathVariable Long id) {
        Optional<Transferencia> transferencia = service.buscarTransferenciaPorId(id);

        if (transferencia.isPresent()) {
            TransferenciaDTO resposta = new TransferenciaDTO(
                    transferencia.get().getId(),
                    transferencia.get().getValor(),
                    transferencia.get().getTaxa(),
                    transferencia.get().getDataTransferencia(),
                    transferencia.get().getDataAgendamento(),
                    transferencia.get().getContaOrigem().getNumeroConta(),
                    transferencia.get().getContaDestino().getNumeroConta()
            );
            return ResponseEntity.ok(resposta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/extrato/{numeroConta}")
    public ResponseEntity<List<TransferenciaDTO>> obterExtrato(@PathVariable String numeroConta) {
        List<TransferenciaDTO> extrato = service.obterExtratoPorConta(numeroConta);
        return ResponseEntity.ok(extrato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTransferencia(@PathVariable Long id) {
        service.deletarTransferencia(id);
        return ResponseEntity.noContent().build();
    }
}