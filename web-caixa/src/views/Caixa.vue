<template>
  <header class="">
    <div class="flex justify-center w-full">
      <img src="../assets/FANS.png" class="h-32 object-center object-contain" />
    </div>
    <div class="flex w-full px-10 gap-10 justify-between mx-auto mb-5">
      <AppCards :valor-caixa="saldoDisplay" :valor-carteira="20" :data="'20/02/2024'" />
    </div>

    <div class="pagina mx-auto rounded-xl px-10 mb-10">
      <div class="flex text-main justify-start text-2xl font-bold mx-auto max-w-7xl">
        <h1>CONTROLE DE CAIXA</h1>
      </div>
      <form @submit.prevent="" class="bg-zinc-200 border p-5 rounded-xl">
        <div class="flex justify-between w-full gap-10">
          <div class="flex flex-col w-full gap-3 mt-10 bg-zinc-100 border border-zinc-300 p-5 rounded-md">
            <label for="valor" class="font-bold text-sm">VALOR DA TRANSAÇÃO</label>
            <div class="w-1/2">
              <input type="text"
                class="bg-white w-full text-main border border-gray-300 rounded-lg p-3 font-semibold focus:outline-none"
                id="valor" v-model="dadosTransacaoGravar.valorTransacao" />
            </div>
            <label for="tipo" class="font-bold text-sm">TIPO</label>
            <div class="w-1/2">
              <select class="bg-white w-full text-main border border-gray-300 rounded-lg p-3 focus:outline-none" id="tipo"
                v-model="dadosTransacaoGravar.tipoTransacao">
                <option value="E">Entrada</option>
                <option value="S">Saída</option>
              </select>
            </div>
            <div class="flex justify-end">
              <button @click="onRealizarTransacao"
                class="flex font-bold p-5 rounded-xl bg-red-800 text-white hover:bg-red-700" type="submit">
                Realizar Transação
              </button>
            </div>
          </div>
          <div class="w-full mt-10 bg-zinc-100 border border-zinc-300 p-5 rounded-md">
            <div class="flex w-full">
              <AppPaginacao :orientacao-botton="true" :pagina="dadosTransacao?.number ?? 0"
                :ultima-pagina="dadosTransacao?.last ?? false" :primeira-pagina="dadosTransacao?.first ?? false"
                :total-paginas="dadosTransacao?.totalPages ?? 0" :total-registros="dadosTransacao?.totalElements ?? 0"
                @on-click-back="onPaginaMudou" />
              <div class="w-full px-3">
                <div class="grid grid-cols-3">
                  <div class="font-bold">Tipo</div>
                  <div class="font-bold">Valor</div>
                  <div class="font-bold">Data</div>
                </div>

                <div v-for="(item, index) in dadosTransacao?.content" class="grid grid-cols-3 gap-y-3" :key="index">
                  <div>
                    {{ onFormatarTipoTransacao(item?.tipoTransacao ?? "") }}
                  </div>
                  <div>{{ item?.valorTransacao.toFixed(2) }}</div>
                  <div>{{ onFormatarData(item?.dataTransacao ?? "") }}</div>
                </div>
              </div>
              <AppPaginacao :orientacao-botton="false" :pagina="dadosTransacao?.number ?? 0"
                :ultima-pagina="dadosTransacao?.last ?? false" :primeira-pagina="dadosTransacao?.first ?? false"
                :total-paginas="dadosTransacao?.totalPages ?? 0" :total-registros="dadosTransacao?.totalElements ?? 0"
                @on-click-next="onPaginaMudou" />
            </div>
          </div>
        </div>
        <div class="flex flex-col w-full gap-3 mt-5 bg-zinc-100 border border-zinc-300 p-5 rounded-md">
          <label for="valor" class="font-bold text-sm">QUANTIDADE MOEDAS</label>
          <div class="w-1/2">
            <div class="flex flex-col border p-5 rounded-xl">
              <div class="grid grid-cols-3">
                <div class="font-bold">Descrição</div>
                <div class="font-bold">Quantidade</div>
              </div>

              <div v-for="(item, index) in dadosMoedas" class="grid grid-cols-3 gap-y-3" :key="index">
                <div>
                  {{ item.descricao }}
                </div>
                <div>{{ item.quantidade.toFixed(1) }}</div>
                <div class="flex w-full justify-end">
                  <button class="border px-5 bg-red-800 text-white">
                    <PhSwap :size="24"/>
                  </button>
                </div>
              </div>
            </div>
          </div>          
        </div>
      </form>
    </div>
  </header>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted } from "vue";
import {
  onGravarTransacao,
  onRetornarTransacoes,
} from "../services/TransacoesService";
import { ITransacoes } from "../types/TransacoesType";
import { ICaixa } from "../types/CaixaType";
import { buscarSaldos } from "../services/CaixaService";
import { IPaginacao } from "../types/Paginacao";
import AppCards from "../components/AppCards.vue";
import moment from "moment";
import AppPaginacao from "../components/AppPaginacao.vue";
import { IMoedas } from "../types/MoedaType";
import { retornaMoedas } from '../services/MoedaService'
import { PhSwap } from "@phosphor-icons/vue";

const dadosTransacaoGravar = reactive<ITransacoes>({
  dataTransacao: "",
  tipoTransacao: "",
  valorTransacao: 0,
});

const dadosTransacao = ref<IPaginacao<ITransacoes | undefined>>();
const dadosMoedas = ref<IMoedas[]>([])
const dadosSaldo = ref<ICaixa>();
const saldoDisplay = ref(0);

async function onRealizarTransacao() {
  const data = new Date();
  const dataFormatada = data.toISOString().split("T")[0];

  dadosTransacaoGravar.dataTransacao = dataFormatada;

  if (
    dadosTransacaoGravar.valorTransacao > 0 &&
    dadosTransacaoGravar.tipoTransacao !== ""
  ) {
    await onGravarTransacao(dadosTransacaoGravar);
    await onDadosTransacao(0);
    await onRetonarSaldoCaixa(1);
  }
}

async function onDadosTransacao(pagina: number) {
  const dados = await onRetornarTransacoes(pagina);

  if (dados !== undefined && "number" in dados) {
    dadosTransacao.value = dados;
  }
}

function onPaginaMudou(novaPagina: number) {
  onDadosTransacao(novaPagina);
}

async function onRetornarMoedas() {
  dadosMoedas.value = await retornaMoedas();
}

async function onRetonarSaldoCaixa(id: number) {
  dadosSaldo.value = await buscarSaldos(id);
  saldoDisplay.value = Number.parseFloat(dadosSaldo.value.saldo);
}

function onFormatarTipoTransacao(nome: string) {
  switch (nome) {
    case "E":
      return "Entrada";
    case "S":
      return "Saída";
    default:
      break;
  }
}

function onFormatarData(data: string) {
  var dataMoment = moment(data);

  if (!dataMoment.isValid()) {
    console.error("Data de entrada inválida");
  }

  return dataMoment.format("DD/MM/YYYY");
}

onMounted(() => {
  onDadosTransacao(0);
  onRetonarSaldoCaixa(1);
  onRetornarMoedas()
});
</script>
