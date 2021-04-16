import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'

import Foo from './Foo.vue'
import Bar from './Bar.vue'
import User from './User.vue'


Vue.config.productionTip = false
Vue.use(VueRouter)

// 2. 定义路由
// 每个路由应该映射一个组件。 其中"component" 可以是
// 通过 Vue.extend() 创建的组件构造器，
// 或者，只是一个组件配置对象。
// 我们晚点再讨论嵌套路由。
const routes = [
  { path: '/foo', component: Foo },
  { name: "barRouter", path: '/bar', component: Bar },
  { path: '/user/:userId', component: User },
  { name: "userRouter", path: '/user/:userId', component: User }
]

// 3. 创建 router 实例，然后传 `routes` 配置
// 你还可以传别的配置参数, 不过先这么简单着吧。
const router = new VueRouter({
  routes // (缩写) 相当于 routes: routes
})


new Vue({
// 放入参数router
  router: router,
  render: h => h(App),
}).$mount('#app')
