import { ICaixa } from "../types/CaixaType";
import { api } from "./ApiService";

export async function buscarSaldos(id: number): Promise<ICaixa> {
  const response = await api.get<ICaixa>(`/caixa/${id}`);
  return response.data;
}
