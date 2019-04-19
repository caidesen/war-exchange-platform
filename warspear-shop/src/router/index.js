import Vue from 'vue'
import Router from 'vue-router'
import register from '@/components/page/register'
import store from '@/components/page/store'
import myinfo from '@/components/page/myinfo'
import addItem from '@/components/page/addItem'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: store
    },
    {
      path: '/register',
      component: register
    },
    {
      path: '/store',
      component: store
    },
    {
      path: '/myinfo',
      component: myinfo
    },
    {
      path: '/addItem',
      component: addItem
    }
  ]
})
