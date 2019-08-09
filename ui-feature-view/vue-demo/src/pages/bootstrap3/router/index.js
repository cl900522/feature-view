import Vue from 'vue'
import Router from 'vue-router'
import BootStrap3 from '@/components/BootStrap3'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'BootStrap3',
      component: BootStrap3
    }
  ]
})
