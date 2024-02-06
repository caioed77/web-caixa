package com.apiCaixaFinanceiro.apicaixa.repositories;

import com.apiCaixaFinanceiro.apicaixa.Entities.TransacoesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacoesRepository extends JpaRepository<TransacoesEntity, Long> {

}
