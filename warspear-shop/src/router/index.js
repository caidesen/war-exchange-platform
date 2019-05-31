import Router from 'vue-router'
import register from '@/components/page/register'
import store from '@/components/page/store'
import myinfo from '@/components/page/myinfo'
import addItem from '@/components/page/addItem'
import updateUserInfo from '@/components/page/updateUserInfo'
import updateUserPassword from '@/components/page/updateUserPassword'
import $store from '@/store/index'

let router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      meta: {
        title: '首页'
      },
      component: store
    },
    {
      path: '/register',
      meta: {
        title: '注册'
      },
      component: register
    },
    {
      path: '/store',
      meta: {
        title: '市场'
      },
      component: store
    },
    {
      path: '/myinfo',
      meta: {
        title: '我的'
      },
      component: myinfo
    },
    {
      path: '/addItem',
      meta: {
        title: '发布'
      },
      component: addItem
    },
    {
      path: '/updateUserInfo',
      meta: {
        title: '修改用户信息'
      },
      component: updateUserInfo
    },
    {
      path: '/updateUserPassword',
      meta: {
        title: '修改密码'
      },
      component: updateUserPassword
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { x: 20, y: 0 }
    }
  }
})
router.beforeEach((to, from, next) => {
  window.document.title = to.meta.title
  $store.commit('changeTitle', to.meta.title)
  next()
})
export default router
