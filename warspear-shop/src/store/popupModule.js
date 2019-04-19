const popup = {
  namespaced: true,
  state: {
    login: false,
    server: false,
    exchangeType: false,
    equipmentType: false,
    weaponType: false
  },
  mutations: {
    popupLogin (state) {
      state.login = true
    },
    popupServer (state, flag) {
      state.server = flag
    },
    popupExchangeType (state, flag) {
      state.exchangeType = flag
    },
    popupEquipmentType (state, flag) {
      state.equipmentType = flag
    },
    popupWeaponType (state, flag) {
      state.weaponType = flag
    }
  }
}
export default popup
