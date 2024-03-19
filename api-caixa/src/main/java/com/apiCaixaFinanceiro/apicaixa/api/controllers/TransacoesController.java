package com.apiCaixaFinanceiro.apicaixa.api.controllers;

import com.apiCaixaFinanceiro.apicaixa.domain.entities.TransacoesEntity;
import com.apiCaixaFinanceiro.apicaixa.domain.dto.GerarRelatorioDTO;
import com.apiCaixaFinanceiro.apicaixa.application.Transacoes.RelatorioTransacoesService;
import com.apiCaixaFinanceiro.apicaixa.application.Transacoes.TransacoesService;
import com.itextpdf.text.DocumentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacoesController  {

    private final TransacoesService transacoesService;

    private final RelatorioTransacoesService relatorioCaixaService;

    public TransacoesController(TransacoesService transacoesService, RelatorioTransacoesService relatorioCaixaService){
        this.transacoesService = transacoesService;
        this.relatorioCaixaService = relatorioCaixaService;
    }

    @PostMapping(value = "/gravar")
    public ResponseEntity<Void> gravarTransacao(@RequestBody TransacoesEntity transacoesEntity) {
        transacoesService.gravarTransacao(transacoesEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/retornarTransacoes")
    public ResponseEntity<Page<TransacoesEntity>> retonarTransacoes(Pageable pageable, @RequestParam LocalDate data) {
        return ResponseEntity.ok(transacoesService.retornaTransacao(pageable, data));
    }

    @PostMapping(value = "/gerarRelatorio")
    public ResponseEntity<Void> gerarRelatorioTransacao(@RequestBody GerarRelatorioDTO gerarRelatorioDTO) throws DocumentException {
        relatorioCaixaService.gerarRelatorio(gerarRelatorioDTO);
        return ResponseEntity.ok().build();
    }

}
