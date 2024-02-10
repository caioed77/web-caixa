<template>
  <ErrorModal />
  <LoadingModal />
  <RouterView></RouterView>
</template>

<script setup lang="ts">
import ErrorModal from './components/errorModal.vue';
import LoadingModal from './components/loadingModal.vue';
import './style.css'
</script>