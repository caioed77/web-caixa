package com.apiCaixaFinanceiro.apicaixa.application.Transacoes;

import com.apiCaixaFinanceiro.apicaixa.domain.dto.FiltroTransacoesDTO;
import com.apiCaixaFinanceiro.apicaixa.domain.entities.CaixaEntity;
import com.apiCaixaFinanceiro.apicaixa.domain.entities.TransacoesEntity;
import com.apiCaixaFinanceiro.apicaixa.domain.repositories.CaixaRepository;
import com.apiCaixaFinanceiro.apicaixa.domain.repositories.TransacoesRepository;
import com.apiCaixaFinanceiro.apicaixa.infra.exceptions.BadRequestException;
import com.apiCaixaFinanceiro.apicaixa.infra.exceptions.ResouceNotFoundException;
import com.apiCaixaFinanceiro.apicaixa.infra.repositoriesJpa.CaixaRepositoryJpa;
import com.apiCaixaFinanceiro.apicaixa.infra.repositoriesJpa.TransacoesRepositoryJpa;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransacoesService {
    private final CaixaRepository caixaRepository;
    private final TransacoesRepository transacoesRepository;
    private final RelatorioTransacoesService relatorioCaixaService;

    public TransacoesService(CaixaRepository caixaRepository, TransacoesRepository transacoesRepository, RelatorioTransacoesService relatorioCaixaService) {
        this.caixaRepository = caixaRepository;
        this.transacoesRepository = transacoesRepository;
        this.relatorioCaixaService = relatorioCaixaService;
    }

    @Transactional
    public void gravarTransacao(TransacoesEntity transacoesEntity) {
        var saldoCaixa = caixaRepository.findById(1L).get();

        validaSaidaSaldo(transacoesEntity, saldoCaixa);
        transacoesRepository.save(transacoesEntity);
    }

    private void validaSaidaSaldo(TransacoesEntity transacoesEntity, CaixaEntity saldoCaixa) {
        switch (transacoesEntity.getTipoTransacao()) {
            case "S" -> {
                if (saldoCaixa.getSaldo().compareTo(transacoesEntity.getValorTransacao()) < 0) {
                    throw new BadRequestException("Você não possui saldo para essa transação");
                }

                var newSaldo = saldoCaixa.getSaldo().subtract(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldo(newSaldo);
                caixaRepository.save(saldoCaixa);
            }
            case "E" -> {
                var newSaldo = saldoCaixa.getSaldo().add(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldo(newSaldo);
                caixaRepository.save(saldoCaixa);
            }
            case "T" -> {
                var newSaldo = saldoCaixa.getSaldo().add(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldo(newSaldo);

                var newSaldoEstoque = saldoCaixa.getSaldoEstoque().subtract(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldoEstoque(newSaldoEstoque);
                caixaRepository.save(saldoCaixa);
            }
            case "R" -> {
                var newSaldoEstoque = saldoCaixa.getSaldoEstoque().add(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldoEstoque(newSaldoEstoque);

                var newSaldo = saldoCaixa.getSaldo().subtract(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldo(newSaldo);
                caixaRepository.save(saldoCaixa);
            }
            case "X" -> {
                if (transacoesEntity.getValorTransacao().compareTo(saldoCaixa.getSaldo()) > 0) {
                    throw new BadRequestException("O Valor informado e maior que o saldo disponivel.");
                }

                var newSaldo = saldoCaixa.getSaldo().subtract(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldo(newSaldo);
                caixaRepository.save(saldoCaixa);
            }
            default -> {
            }
        }
    }

    public Page<TransacoesEntity> retornaTransacao(Pageable pageable, LocalDate data) {
       return transacoesRepository.retornarTransacoes(pageable, data);
    }

    public TransacoesEntity retornarTransacoesPorId(Long id){
        var transacoes = Optional.of(transacoesRepository.findById(id)).get();
        if (transacoes.isPresent()) {
            return transacoes.get();
        }
        else {
            throw new ResouceNotFoundException(id);
        }
    }

    public List<TransacoesEntity> retornarPesquisaPorFiltro(FiltroTransacoesDTO filtros) {
        ExampleMatcher.matching().getDefaultStringMatcher();
        var matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var example = Example.of(filtros, matcher);
        return transacoesRepository.findAll((Sort) example);
    }


}
