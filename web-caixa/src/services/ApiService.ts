import axios, { AxiosResponse, InternalAxiosRequestConfig } from "axios";
import { useErroStore } from "../store/errorStore";
import { useLoadingStore } from "../store/loadingStore";

export const api = axios.create({
  baseURL: "http://192.168.1.131:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  async (config: InternalAxiosRequestConfig) => {
    const erroStore = useErroStore();
    const loadingStore = useLoadingStore();
    erroStore.addErro("");
    loadingStore.showLoading();
    return config;
  },
  (error): any => {
    const erroStore = useErroStore();
    if (axios.isAxiosError(error)) {
      erroStore.addErro(error.response?.data.message);
    } else {
      erroStore.addErro(error);
    }

    const loadingStore = useLoadingStore();
    loadingStore.hideLoading();
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (config: AxiosResponse<any, any>) => {
    const loadingStore = useLoadingStore();
    loadingStore.hideLoading();
    return config;
  },
  (error) => {
    const erroStore = useErroStore();
    if (axios.isCancel(error)) {
      erroStore.addErro("erro");
      return Promise.reject(error);
    } else if (axios.isAxiosError(error)) {
      if (error.response !== undefined) {
        erroStore.addErro(error.response?.data.message);
      } else {
        erroStore.addErro(error.message);
      }
    } else {
      erroStore.addErro(error);
    }

    const loadingStore = useLoadingStore();
    loadingStore.hideLoading();
    return Promise.reject(error);
  }
);
