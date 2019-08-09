import Vue from 'vue'
import Router from 'vue-router'
import Iview from '@/components/Iview'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Iview',
      component: Iview
    }
  ]
})
