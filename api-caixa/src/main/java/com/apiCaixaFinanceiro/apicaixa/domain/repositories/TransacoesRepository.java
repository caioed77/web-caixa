package com.apiCaixaFinanceiro.apicaixa.domain.repositories;

import com.apiCaixaFinanceiro.apicaixa.domain.entities.TransacoesEntity;
import com.apiCaixaFinanceiro.apicaixa.infra.repositoriesJpa.TransacoesRepositoryJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransacoesRepository extends TransacoesRepositoryJpa {

    @Query(nativeQuery = true, value = """
            SELECT ID_TRANSACAO, DATA_TRANSACAO, TIPO_TRANSACAO, VALOR_TRANSACAO
            FROM TRANSACOES
            WHERE (DATA_TRANSACAO IS NULL) OR (DATA_TRANSACAO = :data)                                     
            """)
    Page<TransacoesEntity> retornarTransacoes(Pageable pageable, LocalDate data);
}
