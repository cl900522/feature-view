import Vue from 'vue'
import Router from 'vue-router'
import Foot from '@/components/Foot'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Foot',
      component: Foot
    }
  ]
})
