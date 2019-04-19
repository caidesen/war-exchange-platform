import Vue from 'vue'
import Vuex from 'vuex'
import popupModule from './popupModule'
import addItemModule from './addItemModule'
Vue.use(Vuex)
const store = new Vuex.Store({
  state: {
    pageName: 'home',
    isLogin: false
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
    addItem: addItemModule
  }
})
export default store
