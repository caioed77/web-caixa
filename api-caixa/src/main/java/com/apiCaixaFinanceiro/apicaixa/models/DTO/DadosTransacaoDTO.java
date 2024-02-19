package com.apiCaixaFinanceiro.apicaixa.models.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosTransacaoDTO(BigDecimal saldoTotal, String tipoTransacao, LocalDate dataTransacao) {
}
