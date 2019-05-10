import Vue from 'vue'
import Vuex from 'vuex'
import popupModule from './popupModule'
import addItemModule from './addItemModule'
import userModule from './userModule'
Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    pageName: 'home',
    isLogin: true
  },
  mutations: {
    changePage (state, str) {
      state.pageName = str
    },
    changeLogin (state) {
      state.isLogin = !state.isLogin
    }
  },
  modules: {
    popup: popupModule,
    addItem: addItemModule,
    user: userModule
  }
})
export default store
