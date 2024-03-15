package com.apiCaixaFinanceiro.apicaixa.infra.repositories;

import com.apiCaixaFinanceiro.apicaixa.domain.entities.CaixaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaRepository extends JpaRepository<CaixaEntity, Long> {
}
