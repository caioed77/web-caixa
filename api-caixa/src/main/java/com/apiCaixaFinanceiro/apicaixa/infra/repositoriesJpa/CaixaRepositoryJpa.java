package com.apiCaixaFinanceiro.apicaixa.infra.repositoriesJpa;

import com.apiCaixaFinanceiro.apicaixa.domain.entities.CaixaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaRepositoryJpa extends JpaRepository<CaixaEntity, Long> {
}
