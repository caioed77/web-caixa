import { ITransacoes } from "../types/TransacoesType";
import { api } from "./ApiService";
import { useErroStore } from "../store/errorStore";
import { AxiosError } from "axios";

export async function onGravarTransacao(dados: ITransacoes) {
  const errorStore = useErroStore();
  try {
    await api.post<ITransacoes>("/transacoes/gravar", dados);
  } catch (error) {
    if (error instanceof AxiosError) {
      errorStore.addErro(error.response?.data.title);
    }
  }
}

export async function onRetornarTransacoes(): Promise<
  ITransacoes[] | undefined
> {
  const errorStore = useErroStore();
  try {
    const response = await api.get<ITransacoes[]>(
      "/transacoes/retornarTransacoes"
    );
    return response.data;
  } catch (error) {
    if (error instanceof AxiosError) {
      errorStore.addErro(error.response?.data.title);
    }
    return undefined;
  }
}
