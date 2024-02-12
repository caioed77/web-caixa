import { ITransacoes } from "../types/TransacoesType";
import { api } from "./ApiService";
import { IPaginacao } from "../types/Paginacao";

export async function onGravarTransacao(dados: ITransacoes) {
  await api.post<ITransacoes>("/transacoes/gravar", dados);
}

export async function onRetornarTransacoes(
  pagina: number
): Promise<IPaginacao<ITransacoes>> {
  const response = await api.get<IPaginacao<ITransacoes>>(
    `/transacoes/retornarTransacoes?page=${pagina}&size=9`
  );
  return response.data;
}
