package com.apiCaixaFinanceiro.apicaixa.services;

import com.apiCaixaFinanceiro.apicaixa.entities.CaixaEntity;
import com.apiCaixaFinanceiro.apicaixa.repositories.CaixaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CaixaService {

      private final CaixaRepository caixaRepository;

      public CaixaService(CaixaRepository caixaRepository) {
            this.caixaRepository = caixaRepository;
      }


      public Optional<CaixaEntity> retornaSaldoCaixa(Long id) {
            return Optional.of(caixaRepository.findById(id).get());
      }

}
