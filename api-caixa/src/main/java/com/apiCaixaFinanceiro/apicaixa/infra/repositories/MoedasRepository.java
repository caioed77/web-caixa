package com.apiCaixaFinanceiro.apicaixa.infra.repositories;

import com.apiCaixaFinanceiro.apicaixa.domain.entities.MoedasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoedasRepository extends JpaRepository<MoedasEntity, Long> {

}
