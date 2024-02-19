package com.apiCaixaFinanceiro.apicaixa.models.DTO;

import java.time.LocalDate;

public record GerarRelatorioDTO(LocalDate dataInicial, LocalDate dataFinal, String tipoTransacao) {

}
