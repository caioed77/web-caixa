package com.apiCaixaFinanceiro.apicaixa.infra.repositoriesJpa;

import com.apiCaixaFinanceiro.apicaixa.domain.entities.MoedasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoedasRepositoryJpa extends JpaRepository<MoedasEntity, Long> {

}
