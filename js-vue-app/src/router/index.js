import Vue from 'vue'
import Router from 'vue-router'
import Top from '@/components/page/top/Top.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Top',
      component: Top
    }
  ]
})
