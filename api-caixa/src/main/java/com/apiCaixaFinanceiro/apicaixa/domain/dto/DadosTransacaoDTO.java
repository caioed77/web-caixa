package com.apiCaixaFinanceiro.apicaixa.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosTransacaoDTO(String tipoTransacao, LocalDate dataTransacao, BigDecimal valorTransferencia) {
}
