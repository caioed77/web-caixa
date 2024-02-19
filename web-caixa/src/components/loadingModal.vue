<template>
    <TransitionRoot
        as="div"
        :show="stateLoading.loading"
        className="fixed top-0 left-0 w-full h-full flex justify-center items-center z-40">
        <TransitionChild
            as="div"
            enter="ease-out duration-300"
            enter-from="opacity-0"
            enter-to="opacity-100"
            leave="ease-in duration-200"
            leave-from="opacity-100"
            leave-to="opacity-0"></TransitionChild>
        <div
            class="-z-50 fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"></div>
        <div
            class="rounded-2xl bg-red-800 p-8 shadow-md flex justify-center flex-col items-center">
            <div class="w-24">
                <img src="../assets/DualRing.svg" />
            </div>
            <div
                class="absolute left-32 bg-gray-100 animate-pulse-delay-1"></div>
            <div class="absolute left-50 bg-gray-200 animate-pulse"></div>
            <div class="font-bold text-white">AGUARDE...</div>
        </div>
    </TransitionRoot>
</template>

<script setup lang="ts">
import { TransitionChild, TransitionRoot } from "@headlessui/vue";
import { storeToRefs } from "pinia";
import { useLoadingStore } from "../store/loadingStore";
//import AppIconeSvg from './AppIconeSvg.vue'

const loadingStore = useLoadingStore();
const { stateLoading } = storeToRefs(loadingStore);
</script>

<style>
@keyframes animate-pulse {
    0%,
    100% {
        top: 30px;
        height: 40px;
    }
    50% {
        top: 18px;
        height: 64px;
    }
}

@keyframes animate-pulse-delay-1 {
    0%,
    100% {
        top: 30px;
        height: 40px;
    }
    50% {
        top: 41.999999;
    }
}
</style>
