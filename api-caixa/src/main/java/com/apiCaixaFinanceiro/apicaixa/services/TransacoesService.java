package com.apiCaixaFinanceiro.apicaixa.services;

import com.apiCaixaFinanceiro.apicaixa.entities.TransacoesEntity;
import com.apiCaixaFinanceiro.apicaixa.exceptions.BadRequestException;
import com.apiCaixaFinanceiro.apicaixa.exceptions.ResouceNotFoundException;
import com.apiCaixaFinanceiro.apicaixa.repositories.CaixaRepository;
import com.apiCaixaFinanceiro.apicaixa.repositories.TransacoesRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacoesService {
    private final CaixaRepository caixaRepository;
    private final TransacoesRepository transacoesRepository;
    public TransacoesService(CaixaRepository caixaRepository, TransacoesRepository transacoesRepository) {
        this.caixaRepository = caixaRepository;
        this.transacoesRepository = transacoesRepository;
    }

    @Transactional
    public void gravarTransacao(TransacoesEntity transacoesEntity) {
        var saldoCaixa = caixaRepository.findById(1L).get();

        switch(transacoesEntity.getTipoTransacao()) {
            case "S": {
                if (saldoCaixa.getSaldo().compareTo(transacoesEntity.getValorTransacao()) < 0) {
                    throw new BadRequestException("Você não possui saldo para essa transação");
                }

                var newSaldo = saldoCaixa.getSaldo().subtract(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldo(newSaldo);
                caixaRepository.save(saldoCaixa);
                break;
            }
            case "E": {
                var newSaldo = saldoCaixa.getSaldo().add(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldo(newSaldo);
                caixaRepository.save(saldoCaixa);
                break;
            }
            case "T": {
                var newSaldo = saldoCaixa.getSaldo().subtract(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldo(newSaldo);

                var newSaldoEstoque = saldoCaixa.getSaldoEstoque().add(transacoesEntity.getValorTransacao());
                saldoCaixa.setSaldoEstoque(newSaldoEstoque);
                 caixaRepository.save(saldoCaixa);
            }
            default: {
                break;
            }
        }
        transacoesRepository.save(transacoesEntity);
    }

    public Page<TransacoesEntity> retornaTransacao(Pageable pageable) {
       return transacoesRepository.findAll(pageable);
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

}
