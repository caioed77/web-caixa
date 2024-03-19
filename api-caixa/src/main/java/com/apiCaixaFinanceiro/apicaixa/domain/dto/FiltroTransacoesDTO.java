package com.apiCaixaFinanceiro.apicaixa.domain.dto;

import java.time.LocalDate;

public record FiltroTransacoesDTO(Long idTransacao, LocalDate dataTransacao, String tipoTransacao) {
}
