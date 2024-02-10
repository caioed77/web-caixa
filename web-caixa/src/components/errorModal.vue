<template>
  <AppMensagemModal   
    :on-confirmar="closeModal"
    v-model="exibir"
    @on-close="closeModal" />
</template>

<script setup lang="ts">
import { storeToRefs } from 'pinia'
import { useErroStore } from '../store/errorStore'
import AppMensagemModal from './AppModalMensagem.vue'
import { computed } from 'vue'

const erroStore = useErroStore()
const { stateErro } = storeToRefs(erroStore)
const exibir = computed(() => stateErro.value.mensagemErro !== '')

const closeModal = () => {
  erroStore.addErro('')
}
</script>