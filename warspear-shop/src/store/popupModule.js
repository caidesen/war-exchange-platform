const popup = {
  namespaced: true,
  state: {
    login: false
  },
  mutations: {
    popupLogin (state, flag) {
      state.login = flag
    }
  }
}
export default popup
