import './style.css'
import '@vuepic/vue-datepicker/dist/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import VueDatePicker from '@vuepic/vue-datepicker'
import router from './router'

const pinia = createPinia()
const app = createApp(App)

app.component('VueDatePicker', VueDatePicker)
app.use(pinia)
app.use(router)
app.mount('#app')