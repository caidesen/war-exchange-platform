import axios from 'axios'
import errorHandler from '@/utils/errorHandler'
import { Toast } from 'mint-ui'
const user = {
  namespaced: true,
  state: {
    userId: localStorage.userId,
    username: localStorage.username,
    email: localStorage.email,
    qqNum: localStorage.qqNum,
    description: localStorage.description,
    token: localStorage.token
  },
  mutations: {
    changeToken (state, newVal) {
      state.token = newVal
      localStorage.token = newVal
    },
    changeUser (state, newVal) {
      state.username = newVal.username
      state.userId = newVal.userId
      state.email = newVal.email
      state.qqNum = newVal.qqNum
      state.description = newVal.description
      localStorage.username = newVal.username
      localStorage.userId = newVal.userId
      localStorage.email = newVal.email
      localStorage.qqNum = newVal.qqNum
      localStorage.description = newVal.description
    }
  },
  actions: {
    /**
     * ajax发起登录请求
     * @param context
     * @param loginInfo
     */
    login (context, loginInfo) {
      axios({
        method: 'post',
        url: '/auth/login',
        dataType: 'json',
        params: {
          username: loginInfo.username,
          password: loginInfo.password
        }
      }).then((response) => {
        context.commit('changeToken', response.headers.token)
        context.dispatch('pullUser')
        this.commit('popup/popupLogin', false)
      }).catch((e) => {
        errorHandler(e)
      })
    },
    /**
     * 拉取用户信息
     * @param context
     */
    pullUser (context) {
      axios({
        method: 'get',
        url: '/auth/user',
        headers: {'token': context.state.token}
      }).then((response) => {
        context.commit('changeUser', response.data.data)
        this.commit('changeLogin', true)
        Toast('登录成功')
      }).catch((e) => {
        errorHandler(e)
      })
    }
  }
}
export default user
