package com.apiCaixaFinanceiro.apicaixa.infra.exceptions;

public class BadRequestException extends RuntimeException {
      public BadRequestException(String msg){
            super(msg);
      }
}
