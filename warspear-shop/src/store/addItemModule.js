const addItem = {
  namespaced: true,
  state: {
    server: '',
    exchangeType: '',
    equipmentType: '',
    weaponType: '',
    class: ''
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
    changeClass (state, newVal) {
      state.class = newVal
    }
  }
}
export default addItem
