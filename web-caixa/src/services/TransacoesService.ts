import { ITransacoes } from "../types/TransacoesType";
import { api } from "./ApiService";
import { IPaginacao } from "../types/Paginacao";
import { IRelatorio } from "../types/RelatorioType";

export async function onGravarTransacao(dados: ITransacoes) {
  await api.post<ITransacoes>("/transacoes/gravar", dados);
}

export async function onRetornarTransacoes(
  pagina: number,
  data: string
): Promise<IPaginacao<ITransacoes>> {
  const response = await api.get<IPaginacao<ITransacoes>>(
    `/transacoes/retornarTransacoes?data=${data}&page=${pagina}&size=7`
  );
  return response.data;
}

export async function onGerarRelatorioTransacoes(dados: IRelatorio) {
  await api.post<IRelatorio>('/transacoes/gerarRelatorio', dados);
}