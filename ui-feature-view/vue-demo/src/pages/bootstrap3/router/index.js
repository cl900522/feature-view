import Vue from 'vue'
import Router from 'vue-router'
import Bootstrap3 from '@/components/Bootstrap3'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Bootstrap3',
      component: Bootstrap3
    }
  ]
})
