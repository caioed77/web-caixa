package com.apiCaixaFinanceiro.apicaixa.application.services.Transacoes;

import com.apiCaixaFinanceiro.apicaixa.domain.dto.FiltroTransacoesDTO;
import com.apiCaixaFinanceiro.apicaixa.domain.entities.CaixaEntity;
import com.apiCaixaFinanceiro.apicaixa.domain.entities.TransacoesEntity;
import com.apiCaixaFinanceiro.apicaixa.domain.repositories.CaixaRepository;
import com.apiCaixaFinanceiro.apicaixa.domain.repositories.TransacoesRepository;
import com.apiCaixaFinanceiro.apicaixa.infra.exceptions.BadRequestException;
import com.apiCaixaFinanceiro.apicaixa.infra.exceptions.ResouceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransacoesService {
    private final CaixaRepository caixaRepository;
    private final TransacoesRepository transacoesRepository;
    private final PlatformTransactionManager transactionManager;

    public TransacoesService(CaixaRepository caixaRepository, TransacoesRepository transacoesRepository, PlatformTransactionManager transactionManager) {
        this.caixaRepository = caixaRepository;
        this.transacoesRepository = transacoesRepository;
        this.transactionManager = transactionManager;
    }

    @Transactional
    public void gravarTransacao(TransacoesEntity transacoesEntity) {
        var saldoCaixa = caixaRepository.findById(1L).orElseThrow(() -> new ResouceNotFoundException(1L));
        var transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            validaSaidaSaldo(transacoesEntity, saldoCaixa);
            transacoesRepository.save(transacoesEntity);
            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            throw new BadRequestException("Erro ao salvar transação " + e);
        }
    }

    private void validaSaidaSaldo(TransacoesEntity transacoesEntity, CaixaEntity saldoCaixa) {
        BigDecimal novoSaldo = BigDecimal.ZERO;

        switch (transacoesEntity.getTipoTransacao()) {
            case "S" -> {
                if (saldoCaixa.getSaldo().compareTo(transacoesEntity.getValorTransacao()) < 0) {
                    throw new BadRequestException("Você não possui saldo para essa transação");
                }
                novoSaldo = saldoCaixa.getSaldo().subtract(transacoesEntity.getValorTransacao());
            }
            case "E" -> {
                novoSaldo = saldoCaixa.getSaldo().add(transacoesEntity.getValorTransacao());
            }
            case "T" -> {
                novoSaldo = saldoCaixa.getSaldo().add(transacoesEntity.getValorTransacao());

                var newSaldoEstoque = saldoCaixa.getSaldoEstoque().subtract(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldoEstoque(newSaldoEstoque);
            }
            case "R" -> {
                var newSaldoEstoque = saldoCaixa.getSaldoEstoque().add(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldoEstoque(newSaldoEstoque);

                novoSaldo = saldoCaixa.getSaldo().subtract(transacoesEntity.getValorTransacao());
            }
            case "X" -> {
                if (transacoesEntity.getValorTransacao().compareTo(saldoCaixa.getSaldo()) > 0) {
                    throw new BadRequestException("O Valor informado e maior que o saldo disponivel.");
                }

                novoSaldo = saldoCaixa.getSaldo().subtract(transacoesEntity.getValorTransacao());
            }
            default -> {
            }
        }

        saldoCaixa.setSaldo(novoSaldo);
        caixaRepository.save(saldoCaixa);
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
