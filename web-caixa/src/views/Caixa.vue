<template>
    <header class="h-screen">
        <div class="flex justify-center w-full">
            <img
                src="../assets/FANS.png"
                class="h-32 w-50 object-center object-contain" />
        </div>
        <div
            class="flex text-main justify-start text-2xl font-bold mx-auto max-w-7xl">
            <h1>CONTROLE DE CAIXA</h1>
        </div>
        <div
            class="pagina bg-zinc-200 border border-zinc-300 h-full mx-auto rounded-t-xl">
            <form @submit.prevent="onRealizarTransacao">
                <div class="flex justify-center items-center">
                    <div
                        class="mx-auto text-xl border border-zinc-300 text-main bg-white px-5 py-5 rounded-xl">
                        <h2 class="font-bold">Saldo Total</h2>
                        <p class="text-center text-2xl">R$ {{ 20 }}</p>
                    </div>
                    <div
                        class="mx-auto text-xl border border-zinc-300 text-main bg-white px-5 py-5 rounded-xl">
                        <h2 class="text-center font-bold">Data</h2>
                        <p class="text-center text-xl">{{ "02/12/2024" }}</p>
                    </div>
                </div>
                <div class="flex justify-between w-full gap-10">
                    <div
                        class="flex flex-col w-full gap-3 mt-10 bg-zinc-100 border border-zinc-300 p-5 rounded-md">
                        <label for="valor" class="font-bold text-sm"
                            >VALOR DA TRANSAÇÃO</label
                        >
                        <div class="w-1/2">
                            <input
                                type="text"
                                class="bg-white w-full text-main border border-gray-300 rounded-lg p-3 font-semibold focus:outline-none"
                                id="valor"
                                v-model="valor"
                                required />
                        </div>
                        <label for="tipo" class="font-bold text-sm">TIPO</label>
                        <div class="w-1/2">
                            <select
                                class="bg-white w-full text-main border border-gray-300 rounded-lg p-3 focus:outline-none"
                                id="tipo"
                                v-model="tipo"
                                required>
                                <option value="entrada">Entrada</option>
                                <option value="saida">Saída</option>
                            </select>
                        </div>
                        <div class="flex justify-end">
                            <button
                                class="flex font-bold p-5 bg-red-800 text-white hover:bg-red-700"
                                type="submit">
                                Realizar Transação
                            </button>
                        </div>
                    </div>
                    <div
                        class="flex flex-col w-full gap-3 mt-10 bg-zinc-100 border border-zinc-300 p-5 rounded-md">
                        <label for="valor" class="font-bold text-sm"
                            >QUANTIDADE MOEDAS</label
                        >
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
                                type="submit">
                                Alterar quantidade
                            </button>
                        </div>
                    </div>
                </div>
                <table>
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Idade</th>
                            <th>Cidade</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>João</td>
                            <td>25</td>
                            <td>São Paulo</td>
                        </tr>
                        <tr>
                            <td>Maria</td>
                            <td>30</td>
                            <td>Rio de Janeiro</td>
                        </tr>
                        <tr>
                            <td>Carlos</td>
                            <td>20</td>
                            <td>Brasília</td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </header>
</template>

<script setup lang="ts">
import { ref } from "vue";

const valor = ref(0);
const tipo = ref("");

async function onRealizarTransacao() {
    return await 0;
}
</script>
