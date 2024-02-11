<template>
  <header class="h-screen">
    <div class="flex justify-center w-full">
      <img
        src="../assets/FANS.png"
        class="h-32 w-50 object-center object-contain"
      />
    </div>
    <div class="flex w-2/5 justify-between mx-auto mb-5">
      <div
        class="flex flex-col px-6 py-3 shadow-xl text-xl border border-zinc-300 text-white bg-red-800 rounded-lg"
      >
        <h2 class="font-bold">Saldo Caixa</h2>
        <p class="text-center">R$ {{ saldoDisplay }}</p>
      </div>
      <div
        class="flex flex-col px-5 py-3 shadow-xl text-xl border border-zinc-300 text-white bg-red-800 rounded-xl"
      >
        <h2 class="text-center font-bold">Saldo Estoque</h2>
        <p class="text-center">R$ {{ 20 }}</p>
      </div>
      <div
        class="flex flex-col px-5 py-3 shadow-xl text-xl border border-zinc-300 text-white bg-red-800 rounded-xl"
      >
        <h2 class="text-center font-bold">Data</h2>
        <p class="text-center ">{{ "12/02/2024" }}</p>
      </div>
    </div>

    <div
      class="pagina bg-zinc-200 border border-zinc-300 h-full mx-auto rounded-t-xl"
    >
      <div
        class="flex text-main justify-start text-2xl font-bold mx-auto max-w-7xl"
      >
        <h1>CONTROLE DE CAIXA</h1>
      </div>
      <form @submit.prevent="">
        <div class="flex justify-between w-full gap-10">
          <div
            class="flex flex-col w-full gap-3 mt-10 bg-zinc-100 border border-zinc-300 p-5 rounded-md"
          >
            <label for="valor" class="font-bold text-sm"
              >VALOR DA TRANSAÇÃO</label
            >
            <div class="w-1/2">
              <input
                type="text"
                class="bg-white w-full text-main border border-gray-300 rounded-lg p-3 font-semibold focus:outline-none"
                id="valor"
                v-model="dadosTransacaoGravar.valorTransacao"
                required
              />
            </div>
            <label for="tipo" class="font-bold text-sm">TIPO</label>
            <div class="w-1/2">
              <select
                class="bg-white w-full text-main border border-gray-300 rounded-lg p-3 focus:outline-none"
                id="tipo"
                v-model="dadosTransacaoGravar.tipoTransacao"
                required
              >
                <option value="E">Entrada</option>
                <option value="S">Saída</option>
              </select>
            </div>
            <div class="flex justify-end">
              <button
                @click="onRealizarTransacao"
                class="flex font-bold p-5 bg-red-800 text-white hover:bg-red-700"
                type="submit"
              >
                Realizar Transação
              </button>
            </div>
          </div>
          <div
            class="flex flex-col w-full gap-3 mt-10 bg-zinc-100 border border-zinc-300 p-5 rounded-md"
          >
            <table>
              <thead>
                <tr class="flex gap-5">
                  <th>Tipo transação</th>
                  <th>Valor transação</th>
                  <th>Data transação</th>
                </tr>
              </thead>
              <tbody v-for="item in dadosTransacao">
                <tr class="flex gap-10">
                  <td>{{ onFormatarTipoTransacao(item.tipoTransacao) }}</td>
                  <td>{{ item.valorTransacao.toFixed(1) }}</td>
                  <td>{{ item.dataTransacao }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div
          class="flex flex-col w-full gap-3 mt-10 bg-zinc-100 border border-zinc-300 p-5 rounded-md"
        >
          <label for="valor" class="font-bold text-sm">QUANTIDADE MOEDAS</label>
          <div class="w-1/2">
            <table class="flex flex-col border px-5">
              <tr>
                <td>{{ 0.5 }}</td>
              </tr>
              <tr>
                <td>{{ 0.1 }}</td>
              </tr>
              <tr>
                <td>{{ 0.3 }}</td>
              </tr>
              <tr>
                <td>{{ 0.25 }}</td>
              </tr>
            </table>
          </div>
          <div class="flex justify-end mt-8">
            <button
              class="flex font-bold p-5 bg-red-800 text-white hover:bg-red-700"
              type="submit"
            >
              Alterar quantidade
            </button>
          </div>
        </div>
      </form>
    </div>
  </header>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, onUpdated } from "vue";
import {
  onGravarTransacao,
  onRetornarTransacoes,
} from "../services/TransacoesService";
import { ITransacoes } from "../types/TransacoesType";
import { ICaixa } from "../types/CaixaType";
import { buscarSaldos } from "../services/CaixaService";

const dadosTransacaoGravar = reactive<ITransacoes>({
  dataTransacao: "",
  tipoTransacao: "",
  valorTransacao: 0,
});

const dadosTransacao = ref<ITransacoes[] | undefined>([]);
const dadosSaldo = ref<ICaixa>();
const saldoDisplay = ref("");

async function onRealizarTransacao() {
  const data = new Date();
  const dataFormatada = data.toISOString().split("T")[0];

  dadosTransacaoGravar.dataTransacao = dataFormatada;

  if (dadosTransacaoGravar) await onGravarTransacao(dadosTransacaoGravar);
}

async function onDadosTransacao() {
  dadosTransacao.value = await onRetornarTransacoes();
}

async function onRetonarSaldoCaixa(id: number) {
  dadosSaldo.value = await buscarSaldos(id);

  saldoDisplay.value = dadosSaldo.value.saldo;
}

function onFormatarTipoTransacao(nome: string) {
  if (nome === "E") {
    return "Entrada";
  } else if (nome === "S") {
    return "Saída";
  }
  return "";
}

onUpdated(() => {
  onRetonarSaldoCaixa(1);
});

onMounted(() => {
  onDadosTransacao();
});
</script>
