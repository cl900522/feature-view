import Vue from 'vue'
import Router from 'vue-router'
import BootStrapVue from '@/components/BootStrapVue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'BootStrapVue',
      component: BootStrapVue
    }
  ]
})
