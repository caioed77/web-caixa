<template>
	<header>
		<div class="flex justify-center">
			<img src="../assets/FANS.png" class="h-32 object-center object-contain" />
		</div>
		<div class="flex px-10 gap-10 justify-between mx-auto mb-1">
			<AppCards :valor-caixa="saldoDisplay" :valor-carteira="saldoEstoqueDisplay" v-model="dataAtual" />
		</div>

		<div class="pagina mx-auto rounded-xl px-10 mb-5 mt-5">
			<div class="flex text-main justify-start text-2xl font-bold mx-auto max-w-7xl">
				<h1>CAIXA REPROGRAFIA</h1>
			</div>
			<form class="p-5">
				<div class="flex justify-between w-full gap-10">
					<div class="flex flex-col w-full gap-3 bg-zinc-100 border border-zinc-300 p-3 rounded-md">
						<label for="valor" class="font-bold text-sm">VALOR DA TRANSAÇÃO</label>
						<div class="w-1/2">
							<input type="text"
								class="bg-white w-full text-main border border-gray-300 rounded-lg p-2 font-semibold focus:outline-none"
								id="valor" v-model="dadosTransacaoGravar.valorTransacao" />
						</div>
						<label for="tipo" class="font-bold text-sm">TIPO</label>
						<div class="w-1/2">
							<select class="bg-white w-full text-main border border-gray-300 rounded-lg p-2 focus:outline-none" id="tipo"
								v-model="dadosTransacaoGravar.tipoTransacao">
								<option value="E">Entrada</option>
								<option value="S">Saída</option>
								<option value="R">Transferência Reserva</option>
								<option value="T">Transferência Caixa</option>
								<option value="X">Sangria</option>
							</select>
						</div>
						<div class="flex justify-end">
							<button @click="onRealizarTransacao" type="submit"
								class="flex items-center justify-center w-32 gap-2 font-bold text-sm p-3 rounded-xl bg-red-800 text-white hover:bg-red-700">
								Gerar
								<PhPaperPlaneRight :size="24" />
							</button>
						</div>
					</div>
					<div class="w-full bg-zinc-100 border border-zinc-300 p-5 rounded-md">
						<div class="flex w-full">
							<div class="w-full mx-auto">
								<div class="flex justify-between w-full">
									<AppPaginacao :orientacao-botton="true" :pagina="dadosTransacao?.number ?? 0" :ultima-pagina="dadosTransacao?.last ?? false
										" :primeira-pagina="dadosTransacao?.first ?? false
										" :total-paginas="dadosTransacao?.totalPages ?? 0
										" :total-registros="dadosTransacao?.totalElements ?? 0
										" @on-click-back="onPaginaMudou" />
									<div class="flex justify-between md:space-x-36">
										<div class="font-bold">Tipo</div>
										<div class="font-bold">Valor</div>
										<div class="font-bold">Data</div>
									</div>
									<AppPaginacao :orientacao-botton="false" :pagina="dadosTransacao?.number ?? 0" :ultima-pagina="dadosTransacao?.last ?? false
										" :primeira-pagina="dadosTransacao?.first ?? false
										" :total-paginas="dadosTransacao?.totalPages ?? 0
										" :total-registros="dadosTransacao?.totalElements ?? 0
										" @on-click-next="onPaginaMudou" />
								</div>

								<div v-for="(
                                        item, index
                                    ) in dadosTransacao?.content" class="grid grid-cols-3 text-center" :key="index">
									<div>
										{{
											onFormatarTipoTransacao(
												item?.tipoTransacao ?? ""
											)
										}}
									</div>
									<div>
										{{ item?.valorTransacao.toFixed(2) }}
									</div>
									<div>
										{{
											onFormatarData(
												item?.dataTransacao ?? ""
											)
										}}
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="flex w-full justify-between mt-2 bg-zinc-100 border border-zinc-300 p-5 rounded-md">
					<div class="w-2/5">
						<label for="valor" class="font-bold text-sm px-2">QUANTIDADE MOEDAS</label>
						<div class="flex flex-col border p-5 rounded-xl">
							<div class="grid grid-cols-3">
								<div class="font-bold text-sm">Descrição</div>
								<div class="font-bold text-sm">Quantidade</div>
								<div class="font-bold text-sm">Saldo</div>
							</div>
							<AppModalAlteracao :abre-modal="exibirModalAlteracaoMoeda" @on-close="onCloseModalMoeda"
								:item="itemMoeda" />
							<AppRelatorioModal :abre-modal="exibirModalGerarRelatorio" @on-close="onCloseModalRelatorio" />
							<div v-for="item in dadosMoedas" :key="item.id" class="grid grid-cols-4 gap-32 mt-2">
								<div>{{ item.descricao }}</div>
								<div>{{ item.quantidade }}</div>
								<div>{{ onCalculaTotalMoedas(item.quantidade, item.descricao).toFixed(2) }}</div>
								<div class="flex w-full justify-end">
									<button class="border rounded-full px-3 bg-red-800 hover:bg-red-700 text-white"
										@click="onAlterarQuantidadeMoeda(item)">
										<PhArrowsLeftRight :size="24" />
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="w-1/2 px-5">
						<label for="valor" class="font-bold text-sm">FERRAMENTAS</label>
						<div class="flex justify-end items-start gap-3 mt-2">
							<button @click="onGerarRelatorio"
								class="font-bold rounded-xl flex items-center justify-center gap-3 w-1/2 px-5 py-5 bg-red-800 hover:bg-red-700 text-white">
								<PhFloppyDisk :size="24" />
								Relatórios
							</button>
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
import { retornaMoedas } from "../services/MoedaService";
import {
	PhArrowsLeftRight,
	PhFloppyDisk,
	PhPaperPlaneRight,
} from "@phosphor-icons/vue";
import AppModalAlteracao from "../components/AppModalAlteracao.vue";
import AppRelatorioModal from "../components/AppRelatorioModal.vue";

const dadosTransacaoGravar = reactive<ITransacoes>({
	dataTransacao: "",
	tipoTransacao: "",
	valorTransacao: 0,
});

const dadosTransacao = ref<IPaginacao<ITransacoes | undefined>>();
const dadosMoedas = ref<IMoedas[]>([]);
const dadosSaldo = ref<ICaixa>();
const saldoDisplay = ref(0);
const saldoEstoqueDisplay = ref(0);
const exibirModalAlteracaoMoeda = ref(false);
const exibirModalGerarRelatorio = ref(false);
const itemMoeda = ref<IMoedas>({ id: 0, descricao: "", quantidade: 0 });
const dataAtual = ref<string>(new Date().toLocaleDateString());

async function onRealizarTransacao() {
	dadosTransacaoGravar.dataTransacao = onPreencheDataTransacao();

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
	const dados = await onRetornarTransacoes(pagina, dataAtual.value);

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
	saldoEstoqueDisplay.value = Number.parseFloat(
		dadosSaldo.value.saldoEstoque
	);
}

function onFormatarTipoTransacao(nome: string) {
	switch (nome) {
		case "E":
			return "Entrada";
		case "S":
			return "Saída";
		case "T":
			return "Transferência Caixa";
		case "R":
			return "Transferência Reserva";
		case "X":
			return "Sangria";
		default:
			break;
	}
}

function onCloseModalMoeda() {
	exibirModalAlteracaoMoeda.value = false;
}

function onCloseModalRelatorio() {
	exibirModalGerarRelatorio.value = false;
}

function onFormatarData(data: string) {
	var dataMoment = moment(data);

	if (!dataMoment.isValid()) {
		console.error("Data de entrada inválida");
	}

	return dataMoment.format("DD/MM/YYYY");
}

function onAlterarQuantidadeMoeda(item: IMoedas) {
	exibirModalAlteracaoMoeda.value = true;
	if (item !== undefined) itemMoeda.value = item ?? {};
}

function onGerarRelatorio() {
	exibirModalGerarRelatorio.value = true;
}

function onPreencheDataTransacao() {
	let data = new Date();
	let dia = String(data.getDate()).padStart(2, "0");
	let mes = String(data.getMonth() + 1).padStart(2, "0");
	let ano = data.getFullYear();
	let dataFormatada = `${ano}-${mes}-${dia}`;

	return dataFormatada;
}

function onCalculaTotalMoedas(qte: number, moeda: string) {
	var valor = (Number.parseFloat(moeda) * qte)
	return valor
}

onMounted(() => {
	onDadosTransacao(0);
	onRetonarSaldoCaixa(1);
	onRetornarMoedas();
});
</script>
