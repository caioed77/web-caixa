package com.apiCaixaFinanceiro.apicaixa.controllers;

import com.apiCaixaFinanceiro.apicaixa.entities.TransacoesEntity;
import com.apiCaixaFinanceiro.apicaixa.services.TransacoesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacoesController {

    private final TransacoesService transacoesService;

    public TransacoesController(TransacoesService transacoesService){
        this.transacoesService = transacoesService;
    }

    @PostMapping(value = "/gravar")
    public ResponseEntity<Void> gravarTransacao(@RequestBody TransacoesEntity transacoesEntity) {
        transacoesService.gravarTransacao(transacoesEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/retornarTransacoes")
    public ResponseEntity<List<TransacoesEntity>> retonarTransacoes() {
        return ResponseEntity.ok(transacoesService.retornaTransacao());
    }
}
