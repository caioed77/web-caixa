<template>
  <div
    class="flex w-1/2 flex-col items-center px-6 py-4 text-xl border border-zinc-300 text-white bg-red-800 rounded-lg"
  >
    <PhMoney :size="40" />
    <h2 class="font-bold">CAIXA</h2>
    <p class="text-2xl font-bold">R$ {{ props.valorCaixa.toFixed(2) }}</p>
  </div>
  <div
    class="flex w-1/2 flex-col items-center px-6 py-4 text-xl border border-zinc-300 text-white bg-red-800 rounded-xl"
  >
    <PhWallet :size="40" />
    <h2 class="font-bold">RESERVA</h2>
    <p class="text-2xl font-bold">R$ {{ props.valorCarteira.toFixed(2) }}</p>
  </div>
  <div
    class="flex w-1/2 flex-col items-center px-6 py-4 text-xl border border-zinc-300 text-white bg-red-800 rounded-xl"
  >
    <PhCalendarBlank :size="40" />
    <h2 class="font-bold">Data</h2>
    <p class="text-2xl font-bold">{{ props.data }}</p>
  </div>
</template>

<script setup lang="ts">
import { PhCalendarBlank, PhMoney, PhWallet } from "@phosphor-icons/vue";

interface IProps {
  data: string;
  valorCaixa: number;
  valorCarteira: number;
}

const props = defineProps<IProps>();
</script>
