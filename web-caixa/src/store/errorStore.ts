import { defineStore } from "pinia";
import { ref } from "vue";

export interface IAppError {
  exibir: boolean;
  mensagemErro: string;
}

export const useErroStore = defineStore("useAppStore", () => {
  const stateErro = ref<IAppError>({ mensagemErro: "", exibir: false });

  function addErro(mensagemErro: string) {
    stateErro.value = {
      mensagemErro,
      exibir: mensagemErro !== "" && mensagemErro !== undefined,
    };
  }

  function ocultarError() {
    stateErro.value.exibir = false;

    setTimeout(() => {
      stateErro.value = {
        ...stateErro.value,
        mensagemErro: "",
      };
    }, 300);
  }
  return { stateErro, addErro, ocultarError };
});
