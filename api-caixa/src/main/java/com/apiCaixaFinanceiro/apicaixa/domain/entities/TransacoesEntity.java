package com.apiCaixaFinanceiro.apicaixa.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRANSACOES")
public class TransacoesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACAO")
    private Long idTransacao;

    @Column(name = "DATA_TRANSACAO")
    private LocalDate dataTransacao;

    @Column(name = "VALOR_TRANSACAO")
    private BigDecimal valorTransacao = BigDecimal.ZERO;

    @Column(name = "TIPO_TRANSACAO")
    private String tipoTransacao;
}
