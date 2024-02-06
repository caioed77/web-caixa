package com.apiCaixaFinanceiro.apicaixa.services;

import com.apiCaixaFinanceiro.apicaixa.Entities.CaixaEntity;
import com.apiCaixaFinanceiro.apicaixa.Entities.TransacoesEntity;
import com.apiCaixaFinanceiro.apicaixa.repositories.CaixaRepository;
import com.apiCaixaFinanceiro.apicaixa.repositories.TransacoesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        if (transacoesEntity.getTipoTransacao().equals("S")) {

            if (saldoCaixa.getSaldo().compareTo(transacoesEntity.getValorTransacao()) < 0) {
                throw new RuntimeException("Você não possui saldo para essa transação");
            }

            var newSaldo = saldoCaixa.getSaldo().subtract(transacoesEntity.getValorTransacao());
            saldoCaixa.setSaldo(newSaldo);
            caixaRepository.save(saldoCaixa);
        } else {

            var newSaldo = saldoCaixa.getSaldo().add(transacoesEntity.getValorTransacao());
            saldoCaixa.setSaldo(newSaldo);
            caixaRepository.save(saldoCaixa);
        }
    }

    public List<TransacoesEntity> retornaTransacao() {
       return transacoesRepository.findAll();
    }

    public TransacoesEntity retornarTransacoesPorId(Long id){
        var transacoes = Optional.of(transacoesRepository.findById(id)).get();
        if (transacoes.isPresent()) {
            return transacoes.get();
        }
        else {
            throw new RuntimeException("Transação não encontrada para o id"+ id);
        }
    }

}
