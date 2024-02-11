package com.apiCaixaFinanceiro.apicaixa.controllers.exception;


import com.apiCaixaFinanceiro.apicaixa.exceptions.BadRequestException;
import com.apiCaixaFinanceiro.apicaixa.exceptions.ResouceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {
      @ExceptionHandler(BadRequestException.class)
      public ResponseEntity<StandardError> objectNotFound(BadRequestException e, HttpServletRequest request) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Recurso Já Existe", e.getMessage(), request.getRequestURI());

            return ResponseEntity.status(status).body(err);
      }


      @ExceptionHandler(ResouceNotFoundException.class)
      public ResponseEntity<StandardError> resourceAlreadyExists(ResouceNotFoundException e, HttpServletRequest request) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Não Encontrado", e.getMessage(), request.getRequestURI());

            return ResponseEntity.status(status).body(err);

      }
}
