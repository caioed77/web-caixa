import { createRouter, createWebHashHistory } from "vue-router";
import Caixa from "../views/Caixa.vue";

const routes = [
  {
    path: "/",
    component: Caixa,
    childrean: [
      {
        path: "/:id",
        component: () => import('../views/Caixa.vue'),
      }
    ]
  },

];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;