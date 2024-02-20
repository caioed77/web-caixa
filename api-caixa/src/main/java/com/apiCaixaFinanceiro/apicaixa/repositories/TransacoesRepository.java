package com.apiCaixaFinanceiro.apicaixa.repositories;

import com.apiCaixaFinanceiro.apicaixa.entities.TransacoesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;


public interface TransacoesRepository extends JpaRepository<TransacoesEntity, Long> {

    @Query(nativeQuery = true, value = """
            SELECT ID_TRANSACAO, DATA_TRANSACAO, TIPO_TRANSACAO, VALOR_TRANSACAO
            FROM TRANSACOES
            WHERE (DATA_TRANSACAO IS NULL) OR (DATA_TRANSACAO = :data)                                     
            """)
    Page<TransacoesEntity> retornarTransacoes(Pageable pageable, LocalDate data);


}
