<template>
    <div class="flex flex-col items-start w-full">
      <label class="font-bold text-md pb-2">{{ props.label }}</label>
      <VueDatePicker
        v-model="dataSelecionada"
        :enable-time-picker="false"
        :day-names="['D', 'S', 'T', 'Q', 'Q', 'S', 'S']"
        locale="pt"
        :format="format"
        :auto-position="false"
        @update:model-value="handleDate"
        auto-apply />
    </div>
  </template>
  
  <script setup lang="ts">
  import moment from 'moment'
  import { ref } from 'vue'
  
  interface IProps {
    label: string
    modelValue: string
    placeHolder?: string | undefined
    type?: string
    mensagemErro?: string | undefined
    tamanhoCampo?: number
    lowCase?: boolean
    padding?: string
  }
  
  const handleDate = (modelData: any) => {
    if (modelData !== null) {
      const data = moment(modelData).format('MM/DD/yyyy')
      emit('update:modelValue', data)
    } else {
      emit('update:modelValue', '')
    }
  }
  
  const emit = defineEmits<{
    (event: 'update:modelValue', payload: string): void
  }>()
  
  const props = defineProps<IProps>()
  
  const dataSelecionada = ref<Date>()
  
  const format = (date: Date) => {
    return moment(date).format('DD/MM/yyyy')
  }
  </script>
  
  <style>
  :root {
    --dp-font-family: -apple-system, blinkmacsystemfont, 'Ubuntu', roboto, oxygen,
      ubuntu, cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
    --dp-border-radius: 12px;
    --dp-input-padding: 12px 30px 12px 12px;        
  }
  </style>
  