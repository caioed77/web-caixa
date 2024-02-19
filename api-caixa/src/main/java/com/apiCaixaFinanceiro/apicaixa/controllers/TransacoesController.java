package com.apiCaixaFinanceiro.apicaixa.controllers;

import com.apiCaixaFinanceiro.apicaixa.entities.TransacoesEntity;
import com.apiCaixaFinanceiro.apicaixa.models.DTO.DadosTransacaoDTO;
import com.apiCaixaFinanceiro.apicaixa.models.DTO.GerarRelatorioDTO;
import com.apiCaixaFinanceiro.apicaixa.services.RelatorioCaixaService;
import com.apiCaixaFinanceiro.apicaixa.services.TransacoesService;
import com.itextpdf.text.DocumentException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/transacoes")
public class TransacoesController {

    private final TransacoesService transacoesService;

    private final RelatorioCaixaService relatorioCaixaService;

    public TransacoesController(TransacoesService transacoesService, RelatorioCaixaService relatorioCaixaService){
        this.transacoesService = transacoesService;
        this.relatorioCaixaService = relatorioCaixaService;
    }

    @PostMapping(value = "/gravar")
    public ResponseEntity<Void> gravarTransacao(@RequestBody TransacoesEntity transacoesEntity) {
        transacoesService.gravarTransacao(transacoesEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/retornarTransacoes")
    public ResponseEntity<Page<TransacoesEntity>> retonarTransacoes(Pageable pageable) {
        return ResponseEntity.ok(transacoesService.retornaTransacao(pageable));
    }

    @PostMapping(value = "/gerarRelatorio")
    public ResponseEntity<Void> gerarRelatorioTransacao(@RequestBody GerarRelatorioDTO gerarRelatorioDTO) throws DocumentException {
        relatorioCaixaService.gerarRelatorio(gerarRelatorioDTO);
        return ResponseEntity.ok().build();
    }

}
