package com.apiCaixaFinanceiro.apicaixa.domain.dto;

import java.time.LocalDate;

public record GerarRelatorioDTO(LocalDate dataInicial, LocalDate dataFinal, String tipoTransacao) {

}
