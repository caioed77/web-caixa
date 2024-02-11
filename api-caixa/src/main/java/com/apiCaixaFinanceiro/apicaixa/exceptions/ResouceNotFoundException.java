package com.apiCaixaFinanceiro.apicaixa.exceptions;

public class ResouceNotFoundException extends RuntimeException {
      public ResouceNotFoundException(Object id){
            super("não foi encontrado dados do registro código "+ id);
      }
}