package com.apiCaixaFinanceiro.apicaixa.infra.repositoriesJpa;

import com.apiCaixaFinanceiro.apicaixa.domain.entities.TransacoesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;


public interface TransacoesRepositoryJpa extends JpaRepository<TransacoesEntity, Long> {
}
