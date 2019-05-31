import Vue from 'vue'
import Vuex from 'vuex'
import popupModule from './popupModule'
import addItemModule from './addItemModule'
import userModule from './userModule'
import findItemModule from './findItemModule'
Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    pageName: 'home',
    isLogin: localStorage.isLogin || false,
    title: '战矛交易网'
  },
  mutations: {
    changePage (state, str) {
      state.pageName = str
    },
    changeLogin (state, newStates) {
      state.isLogin = newStates
      localStorage.isLogin = newStates
    },
    changeTitle (state, newTitle) {
      state.title = newTitle
    }
  },
  modules: {
    popup: popupModule,
    addItem: addItemModule,
    user: userModule,
    findItem: findItemModule
  }
})
export default store
