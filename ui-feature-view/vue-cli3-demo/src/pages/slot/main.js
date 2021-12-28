import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

new Vue({
// 放入参数router
  render: h => h(App),
}).$mount('#app')
