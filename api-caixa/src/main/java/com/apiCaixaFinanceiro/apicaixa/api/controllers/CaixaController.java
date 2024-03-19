package com.apiCaixaFinanceiro.apicaixa.api.controllers;

import com.apiCaixaFinanceiro.apicaixa.domain.entities.CaixaEntity;
import com.apiCaixaFinanceiro.apicaixa.application.services.Caixa.CaixaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/caixa")
public class CaixaController {

      private final CaixaService caixaService;

      public CaixaController(CaixaService caixaService) {
            this.caixaService = caixaService;
      }

      @GetMapping(value = "/{id}")
      public ResponseEntity<Optional<CaixaEntity>> retonarSaldos(@PathVariable Long id) {
            return ResponseEntity.ok(caixaService.retornaSaldoCaixa(id));
      }

}
