package com.apiCaixaFinanceiro.apicaixa.services;

import com.apiCaixaFinanceiro.apicaixa.entities.CaixaEntity;
import com.apiCaixaFinanceiro.apicaixa.models.DTO.DadosTransacaoDTO;
import com.apiCaixaFinanceiro.apicaixa.models.DTO.GerarRelatorioDTO;
import com.apiCaixaFinanceiro.apicaixa.models.DTO.RetornaSaldoDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

@Service
public class RelatorioCaixaService {


      @Value("${caminho.Relatorio}")
      public static String caminhoRelatorio;

      private final CaixaService caixaService;

      private final JdbcTemplate jdbcTemplate;


      public RelatorioCaixaService(CaixaService caixaService, JdbcTemplate jdbcTemplate) {
            this.caixaService = caixaService;
            this.jdbcTemplate = jdbcTemplate;
      }


      public final ByteArrayOutputStream gerarRelatorio(GerarRelatorioDTO query) throws DocumentException {
            var caixa = caixaService.retornaSaldoCaixa(1L).get();
            var Totaltransacoes = obtemSaldoTransacao(query.dataInicial(), query.dataFinal(), query.tipoTransacao());
            var transacoes = obtemDadosTransacoes(query.dataInicial(), query.dataFinal(), query.tipoTransacao());

            return generatePdfStream(transacoes, caixa, Totaltransacoes);
      }


      private static ByteArrayOutputStream generatePdfStream(List<DadosTransacaoDTO> dadosTransacoes, CaixaEntity dadosCaixa, List<RetornaSaldoDTO> saldoTotalTransacoes) throws DocumentException {
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);

            try {
                  document.open();

                  document.add(new Paragraph("Saldo do Caixa:"));
                  var paragraphSaldoCaixa = new Paragraph(dadosCaixa.getSaldo().toString());
                  document.add(paragraphSaldoCaixa);

                 document.add(new Paragraph("\n"));

                  document.add(new Paragraph("Dados Transações:"));
                  for (var row : dadosTransacoes) {

                        var paragraphTipo = new Paragraph(row.tipoTransacao());
                        document.add(paragraphTipo);
                        document.add(new Paragraph("\n"));

                        var paragraphData = new Paragraph(row.dataTransacao().toString());
                        document.add(paragraphData);
                        document.add(new Paragraph("\n"));

                        var paragraphSaldo = new Paragraph(row.saldoTotal().toString());
                        document.add(paragraphSaldo);
                        document.add(new Paragraph("\n"));
                  }

                  document.add(new Paragraph("Saldo Total"));
                  var paragraphSaldoTotal = new Paragraph(String.valueOf(saldoTotalTransacoes.get(0).Saldo()));
                  document.add(paragraphSaldoTotal);


            } finally {
                  document.close();
            }

            return outputStream;
      }


      private List<RetornaSaldoDTO> obtemSaldoTransacao(LocalDate dataInicial, LocalDate dataFinal, String tipoTransacao) {
            var sqlTransacao = new StringBuilder();
            sqlTransacao.append("SELECT SUM(t.valor_transacao) AS saldo ");
            sqlTransacao.append("FROM transacoes t ");
            sqlTransacao.append("WHERE 1=1 ");

            var listaParametros = new ArrayList<Object>();

            if (dataInicial != null) {
                  sqlTransacao.append("AND t.data_transacao >= ? ");
                  listaParametros.add(dataInicial);
            }

            if (dataFinal != null) {
                  sqlTransacao.append("AND t.data_transacao <= ? ");
                  listaParametros.add(dataFinal);
            }

            if (!Objects.equals(tipoTransacao, "")) {
                  sqlTransacao.append("AND t.tipo_transacao = ? ");
                  listaParametros.add(tipoTransacao);
            }

            return jdbcTemplate.query(sqlTransacao.toString(), listaParametros.toArray(), transacaoRowMapper);
      }


      private List<DadosTransacaoDTO> obtemDadosTransacoes(LocalDate dataInicial, LocalDate dataFinal, String tipoTransacao) {
            return  new ArrayList<>();
      }

      private final RowMapper<RetornaSaldoDTO> transacaoRowMapper =
              (rs, rowNum) -> new RetornaSaldoDTO(
                      rs.getBigDecimal("Saldo")
              );
}
