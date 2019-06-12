import Router from 'vue-router'
import $store from '@/store/index'
const register = () => import('@/components/page/register')
const store = () => import(/* webpackChunkName: "group-home" */ '@/components/page/store')
const myinfo = () => import(/* webpackChunkName: "group-home" */ '@/components/page/myinfo')
const addItem = () => import(/* webpackChunkName: "group-home" */ '@/components/page/addItem')
const updateUserInfo = () => import('@/components/page/updateUserInfo')
const updateUserPassword = () => import('@/components/page/updateUserPassword')
const uploadPics = () => import('@/components/page/uploadPics')
const itemDetailed = () => import('@/components/page/itemDetailed')
const condition = () => import('@/components/page/condition')
const myitem = () => import('@/components/page/myitem')
let router = new Router({
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
    },
    {
      path: '/uploadPics',
      meta: {
        title: '上传图片'
      },
      component: uploadPics
    },
    {
      path: '/itemDetailed.vue',
      meta: {
        title: '详情页'
      },
      component: itemDetailed
    },
    {
      path: '/condition',
      meta: {
        title: '查询条件'
      },
      component: condition
    },
    {
      path: '/myitem',
      meta: {
        title: '我的发布'
      },
      component: myitem
    }
  ]
})
router.beforeEach((to, from, next) => {
  window.document.title = to.meta.title
  $store.commit('changeTitle', to.meta.title)
  next()
})
export default router
