import { IMoedas } from "../types/MoedaType";
import { api } from "./ApiService";

export async function retornaMoedas(): Promise<IMoedas[]> {
    const response = await api.get<IMoedas[]>('/moedas/retornarMoedas')
    return response.data

}

export async function alterarQuantidade(id: number, quantidade: number) {
    await api.put<IMoedas>(`/moedas/alterarQuantidade/${id}?quantidade=${quantidade}`)
}

export async function buscarMoedaPorID(id: number) {
    const response = await api.get<IMoedas>(`/moedas/${id}`)
    return response.data            
}