package com.desafio.tokiomarine.api.transferencia.services;

import com.desafio.tokiomarine.api.transferencia.dtos.TransferenciaDTO;
import com.desafio.tokiomarine.api.transferencia.models.Transferencia;
import com.desafio.tokiomarine.api.transferencia.models.Usuario;
import com.desafio.tokiomarine.api.transferencia.repositories.TransferenciaRepository;
import com.desafio.tokiomarine.api.transferencia.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Transferencia agendarTransferencia(TransferenciaDTO dto) {
        Usuario contaOrigem = usuarioRepository.findByNumeroConta(dto.getContaOrigem())
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada."));
        Usuario contaDestino = usuarioRepository.findByNumeroConta(dto.getContaDestino())
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada."));
        validarDados(dto);
        Transferencia transferencia = new Transferencia(dto, contaOrigem, contaDestino);
        transferencia = calcularTaxa(transferencia);

        return repository.save(transferencia);
    }

    public List<Transferencia> listarTransferencias() {
        return repository.findAll();
    }

    public Optional<Transferencia> buscarTransferenciaPorId(Long id) {
        return repository.findById(id);
    }

    public void deletarTransferencia(Long id) {
        repository.deleteById(id);
    }

    private void validarDados(TransferenciaDTO dto) {
        if (dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }

        if (!dto.getDataTransferencia().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de transferência deve ser futura.");
        }
    }

    private Transferencia calcularTaxa(Transferencia transferencia) {
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), transferencia.getDataTransferencia());
        BigDecimal taxa;

        if (dias == 0) {
            taxa = transferencia.getValor().multiply(BigDecimal.valueOf(0.025)).add(BigDecimal.valueOf(3));
        } else if (dias <= 10) {
            taxa = BigDecimal.valueOf(12);
        } else if (dias <= 20) {
            taxa = transferencia.getValor().multiply(BigDecimal.valueOf(0.082));
        } else if (dias <= 30) {
            taxa = transferencia.getValor().multiply(BigDecimal.valueOf(0.069));
        } else if (dias <= 40) {
            taxa = transferencia.getValor().multiply(BigDecimal.valueOf(0.047));
        } else if (dias <= 50) {
            taxa = transferencia.getValor().multiply(BigDecimal.valueOf(0.017));
        } else {
            throw new IllegalArgumentException("Intervalo de dias inválido.");
        }

        transferencia.setTaxa(taxa);
        return transferencia;
    }

    public List<TransferenciaDTO> obterExtratoPorConta(String numeroConta) {
        Usuario usuario = usuarioRepository.findByNumeroConta(numeroConta)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada."));

        List<Transferencia> transferenciasRealizadas = repository.findByContaOrigem(usuario);
        List<Transferencia> transferenciasRecebidas = repository.findByContaDestino(usuario);

        List<TransferenciaDTO> extrato = new ArrayList<>();
        extrato.addAll(transferenciasRealizadas.stream()
                .map(transferencia -> new TransferenciaDTO(
                        transferencia.getId(),
                        transferencia.getValor(),
                        transferencia.getTaxa(),
                        transferencia.getDataTransferencia(),
                        transferencia.getDataAgendamento(),
                        transferencia.getContaOrigem().getNumeroConta(),
                        transferencia.getContaDestino().getNumeroConta()
                ))
                .collect(Collectors.toList()));

        extrato.addAll(transferenciasRecebidas.stream()
                .map(transferencia -> new TransferenciaDTO(
                        transferencia.getId(),
                        transferencia.getValor(),
                        transferencia.getTaxa(),
                        transferencia.getDataTransferencia(),
                        transferencia.getDataAgendamento(),
                        transferencia.getContaOrigem().getNumeroConta(),
                        transferencia.getContaDestino().getNumeroConta()
                ))
                .collect(Collectors.toList()));

        return extrato;
    }
}