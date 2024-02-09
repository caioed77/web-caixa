import { createRouter, createWebHashHistory } from "vue-router";
import Caixa from "../views/Caixa.vue";

const routes = [
  {
    path: "/",
    component: Caixa,
  } 
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;