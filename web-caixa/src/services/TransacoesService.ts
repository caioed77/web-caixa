import { ITransacoes } from "../types/TransacoesType";
import { api } from "./ApiService";

export async function onGravarTransacao(dados: ITransacoes) {    
    await api.post<ITransacoes>('/transacoes/gravar', dados)
}

export async function onRetornarTransacoes(): Promise<ITransacoes[]> {
    const response = await api.get<ITransacoes[]>('/transacoes/retornarTransacoes')    
    return response.data
}