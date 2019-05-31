const addItem = {
  namespaced: true,
  state: {
    server: '',
    exchangeType: '',
    equipmentType: '',
    weaponType: '',
    className: ''
  },
  mutations: {
    changeServer (state, newVal) {
      state.server = newVal
    },
    changeExchangeType (state, newVal) {
      state.exchangeType = newVal
    },
    changeEquipmentType (state, newVal) {
      state.equipmentType = newVal
    },
    changeWeaponType (state, newVal) {
      state.weaponType = newVal
    },
    changeClassName (state, newVal) {
      state.className = newVal
    }
  }
}
export default addItem
