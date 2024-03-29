package com.apiCaixaFinanceiro.apicaixa.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CAIXA")
public class CaixaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CAIXA")
    private Long id;

    @Column(name = "SALDO")
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "SALDO_ESTOQUE")
    private BigDecimal saldoEstoque = BigDecimal.ZERO;
}
