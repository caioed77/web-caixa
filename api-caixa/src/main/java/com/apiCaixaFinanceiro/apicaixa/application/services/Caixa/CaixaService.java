package com.apiCaixaFinanceiro.apicaixa.application.services.Caixa;

import com.apiCaixaFinanceiro.apicaixa.domain.entities.CaixaEntity;
import com.apiCaixaFinanceiro.apicaixa.domain.repositories.CaixaRepository;
import com.apiCaixaFinanceiro.apicaixa.infra.exceptions.ResouceNotFoundException;
import com.apiCaixaFinanceiro.apicaixa.infra.repositoriesJpa.CaixaRepositoryJpa;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CaixaService {
      private final CaixaRepository caixaRepository;

      public CaixaService(CaixaRepository caixaRepository) {
            this.caixaRepository = caixaRepository;
      }

      public Optional<CaixaEntity> retornaSaldoCaixa(Long id) {
            return Optional.of(caixaRepository.findById(id)
                    .orElseThrow(() -> new ResouceNotFoundException(id)));
      }
}
