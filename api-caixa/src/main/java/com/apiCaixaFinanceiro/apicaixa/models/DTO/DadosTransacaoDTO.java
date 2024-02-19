package com.apiCaixaFinanceiro.apicaixa.models.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosTransacaoDTO(String tipoTransacao, LocalDate dataTransacao, BigDecimal valorTransferencia) {
}
