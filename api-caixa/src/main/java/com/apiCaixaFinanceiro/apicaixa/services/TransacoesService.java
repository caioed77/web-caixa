package com.apiCaixaFinanceiro.apicaixa.services;

import com.apiCaixaFinanceiro.apicaixa.entities.TransacoesEntity;
import com.apiCaixaFinanceiro.apicaixa.exceptions.BadRequestException;
import com.apiCaixaFinanceiro.apicaixa.exceptions.ResouceNotFoundException;
import com.apiCaixaFinanceiro.apicaixa.repositories.CaixaRepository;
import com.apiCaixaFinanceiro.apicaixa.repositories.TransacoesRepository;
import jakarta.transaction.Transactional;
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
        if (transacoesEntity.getTipoTransacao().equals("S")) {

            if (saldoCaixa.getSaldo().compareTo(transacoesEntity.getValorTransacao()) < 0) {
                throw new BadRequestException("Você não possui saldo para essa transação");
            }

            var newSaldo = saldoCaixa.getSaldo().subtract(transacoesEntity.getValorTransacao());
            saldoCaixa.setSaldo(newSaldo);
            caixaRepository.save(saldoCaixa);
        } else {

            var newSaldo = saldoCaixa.getSaldo().add(transacoesEntity.getValorTransacao());
            saldoCaixa.setSaldo(newSaldo);
            caixaRepository.save(saldoCaixa);
        }
        transacoesRepository.save(transacoesEntity);
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
            throw new ResouceNotFoundException(id);
        }
    }

}
