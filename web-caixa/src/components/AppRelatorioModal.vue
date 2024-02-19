<template>
    <TransitionRoot as="template" :show="props.abreModal">
        <Dialog as="div" class="relative z-10">
            <TransitionChild
                as="template"
                enter="ease-out duration-300"
                enter-from="opacity-0"
                enter-to="opacity-100"
                leave="ease-in duration-200"
                leave-from="opacity-100"
                leave-to="opacity-0">
                <div
                    class="fixed inset-0 hidden bg-gray-500 bg-opacity-75 transition-opacity md:block" />
            </TransitionChild>

            <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
                <div
                    class="flex min-h-full items-stretch justify-center text-center md:items-center md:px-2 lg:px-4">
                    <TransitionChild
                        as="template"
                        enter="ease-out duration-300"
                        enter-from="opacity-0 translate-y-4 md:translate-y-0 md:scale-95"
                        enter-to="opacity-100 translate-y-0 md:scale-100"
                        leave="ease-in duration-200"
                        leave-from="opacity-100 translate-y-0 md:scale-100"
                        leave-to="opacity-0 translate-y-4 md:translate-y-0 md:scale-95">
                        <DialogPanel
                            class="flex w-full transform text-left text-base transition md:my-8 md:max-w-2xl md:px-4 lg:max-w-4xl">
                            <div
                                class="relative flex flex-col w-full items-center overflow-y-auto bg-white px-4 pb-8 pt-14 shadow-2xl sm:px-6 sm:pt-8 md:p-6 lg:p-8">
                                <button
                                    type="button"
                                    class="absolute right-4 top-4 text-gray-400 hover:text-gray-500 sm:right-6 sm:top-8 md:right-6 md:top-6 lg:right-8 lg:top-8"
                                    @click="onFecharModal">
                                    <span class="sr-only">Close</span>
                                    <PhX class="h-6 w-6" aria-hidden="true" />
                                </button>
                                <h1 class="font-bold text-xl">
                                    Emissão de relatorio
                                </h1>
                                <div
                                    class="w-1/2 flex flex-col items-start gap-5 mb-5 mt-5">
                                    <AppDateBox
                                        label="Data inicial"
                                        v-model="dataInicial" />
                                    <AppDateBox
                                        label="Data final"
                                        v-model="dataFinal" />

                                    <div class="flex flex-col gap-2 w-full">
                                        <label class="font-bold"
                                            >Tipo transação</label
                                        >
                                        <select
                                            class="bg-white w-full text-back border border-gray-300 rounded-lg p-2 focus:outline-none"
                                            id="tipo">
                                            <option value="E">Entrada</option>
                                            <option value="S">Saída</option>
                                            <option value="R">
                                                Transferência Reserva
                                            </option>
                                            <option value="T">
                                                Transferência Caixa
                                            </option>
                                            <option value="X">Sangria</option>
                                        </select>
                                    </div>
                                    <button
                                        type="button"
                                        class="border w-full py-2 bg-red-800 hover:bg-red-700 text-white font-bold rounded-xl"
                                        @click="onGerarRelatorio()">
                                        Gerar Relatorio
                                    </button>
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
} from "@headlessui/vue";
import { PhX } from "@phosphor-icons/vue";
import AppDateBox from "./AppDateBox.vue";
import { ref } from "vue";

interface IProps {
    abreModal: boolean;
}

const emit = defineEmits<{
    (e: "onClose"): void;
}>();

const props = defineProps<IProps>();
const dataInicial = ref(Date);
const dataFinal = ref(Date);

function onFecharModal() {
    emit("onClose");
}

async function onGerarRelatorio() {
    onFecharModal();
}
</script>
