package com.apiCaixaFinanceiro.apicaixa.repositories;

import com.apiCaixaFinanceiro.apicaixa.Entities.CaixaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaRepository extends JpaRepository<CaixaEntity, Long> {
}
