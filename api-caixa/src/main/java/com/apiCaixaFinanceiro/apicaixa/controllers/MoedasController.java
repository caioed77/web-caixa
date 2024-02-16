package com.apiCaixaFinanceiro.apicaixa.controllers;

import com.apiCaixaFinanceiro.apicaixa.entities.MoedasEntity;
import com.apiCaixaFinanceiro.apicaixa.services.MoedasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/moedas")
public class MoedasController {

    private final MoedasService moedasService;

    public MoedasController(MoedasService moedasService) {
        this.moedasService = moedasService;
    }

    @GetMapping(value = "/retornarMoedas")
    public ResponseEntity<List<MoedasEntity>> retornaMoedas() {
        return ResponseEntity.ok(moedasService.listarMoedas());
    }

    @PutMapping(value = "/alterarQuantidade/{id}")
    public ResponseEntity<Void> alterarQuantidade(@PathVariable Long id, @RequestParam Integer quantidade) {
        moedasService.somarMoeda(quantidade, id);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<MoedasEntity>> buscarMoedaId(@PathVariable Long id) {
        return ResponseEntity.ok(moedasService.retonarMoedaId(id));
    }
}