<template>
  <header>
    <div class="pagina">
      <h1>Controle de Caixa</h1>
      <div class="mx-auto text-xl">
        <h2>Saldo do Caixa:</h2>
        <p>{{ 20 }}</p>
      </div>

      <form @submit.prevent="realizarTransacao">
        <div class="flex">
          <label for="valor">Valor:</label>
          <input type="number" id="valor" v-model="valor" required>
          <label for="tipo">Tipo:</label>
          <select id="tipo" v-model="tipo" required>
            <option value="entrada">Entrada</option>
            <option value="saida">Saída</option>
          </select>
          <button type="submit">Realizar Transação</button>
        </div>
      </form>
    </div>

  </header>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const valor = ref(0)
const tipo = ref('')

function realizarTransacao() {
  return 0
}
</script>