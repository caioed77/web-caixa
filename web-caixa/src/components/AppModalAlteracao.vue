<template>
  <TransitionRoot as="template" :show="props.abreModal">
    <Dialog as="div" class="relative z-10" @close="onFecharModal">
      <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0" enter-to="opacity-100"
        leave="ease-in duration-200" leave-from="opacity-100" leave-to="opacity-0">
        <div class="fixed inset-0 hidden bg-gray-500 bg-opacity-75 transition-opacity md:block"></div>
      </TransitionChild>

      <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
        <div class="flex min-h-full items-stretch justify-center text-center md:items-center md:px-2 lg:px-4">
          <TransitionChild as="template" enter="ease-out duration-300"
            enter-from="opacity-0 translate-y-4 md:translate-y-0 md:scale-95"
            enter-to="opacity-100 translate-y-0 md:scale-100" leave="ease-in duration-200"
            leave-from="opacity-100 translate-y-0 md:scale-100"
            leave-to="opacity-0 translate-y-4 md:translate-y-0 md:scale-95">
            <DialogPanel class="flex w-full transform text-left text-base transition md:my-8 max-w-xl">
              <div
                class="relative flex w-full items-center overflow-hidden bg-zinc-100 px-4 pb-8 h-52 shadow-2xl sm:px-6 sm:pt-8 md:p-6 lg:p-8">
                <button type="button"
                  class="absolute right-4 top-4 text-gray-400 hover:text-gray-500 sm:right-6 sm:top-8 md:right-6 md:top-6 lg:right-8 lg:top-8"
                  @click="onFecharModal">
                  <span class="sr-only">Close</span>
                  <PhX class="h-6 w-6" aria-hidden="true" />
                </button>
                <div class="flex flex-col gap-2 w-full">
                  <label class="font-bold text-md">Alterar quantidade moeda : {{ props.item.descricao }}</label>
                  <input v-model="props.item.quantidade" class="border px-2 py-3 rounded-lg focus:outline-none" />
                  <button type="button" class="border py-2 bg-red-800 text-white font-bold rounded-xl" @click="onAlterarMoeda(props.item.id, props.item.quantidade)">Alterar</button>
                </div>
              </div>
            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>
  
<script setup lang="ts">
import {
  Dialog,
  DialogPanel,
  TransitionChild,
  TransitionRoot,
} from '@headlessui/vue'
import { PhX } from '@phosphor-icons/vue';
import { IMoedas } from '../types/MoedaType';
import { alterarQuantidade } from '../services/MoedaService';

interface IProps {
  abreModal: boolean
  item: IMoedas
}

const emit = defineEmits<{
  (e: 'onClose'): void
}>()

const props = defineProps<IProps>()

function onFecharModal() {
  emit('onClose')
}

async function onAlterarMoeda(id: number, qte: number) {
  await alterarQuantidade(id, qte)
  onFecharModal()
}


</script>