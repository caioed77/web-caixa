package com.apiCaixaFinanceiro.apicaixa.Entities;

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
    private Long idTransacao;

    private LocalDate dataTransacao;

    private BigDecimal valorTransacao = BigDecimal.ZERO;

    private String tipoTransacao;
}
