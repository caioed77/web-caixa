<template>
  <button
    v-if="orientacaoBotton"
    @click="emit('onClickBack', props.pagina - 1)"
    class="hover:text-red-800"
  >
    <PhCaretLeft weight="bold" :size="30" />
  </button>
  <button
    v-else
    @click="emit('onClickNext', props.pagina + 1)"
    class="hover:text-red-800"
  >
    <PhCaretRight weight="bold" :size="30" />
  </button>
</template>

<script setup lang="ts">
import { PhCaretLeft, PhCaretRight } from "@phosphor-icons/vue";

interface IProps {
  orientacaoBotton: boolean;
  pagina: number;  
  totalRegistros: number;
  totalPaginas: number;
  ultimaPagina: boolean;
  primeiraPagina: boolean;
}

const props = defineProps<IProps>();

const emit = defineEmits<{
  (event: "onClickNext", pagina: number): void;
  (event: "onClickBack", pagina: number): void;
}>();
</script>
