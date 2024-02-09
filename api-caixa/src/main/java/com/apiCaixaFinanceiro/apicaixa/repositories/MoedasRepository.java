package com.apiCaixaFinanceiro.apicaixa.repositories;

import com.apiCaixaFinanceiro.apicaixa.Entities.MoedasEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoedasRepository extends JpaRepository<MoedasEntity, Long> {

}
