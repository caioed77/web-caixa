package com.apiCaixaFinanceiro.apicaixa.exceptions;

public class BadRequestException extends RuntimeException {
      public BadRequestException(String msg){
            super(msg);
      }
}
