const addItem = {
  namespaced: true,
  state: {
    server: undefined,
    exchangeType: undefined,
    equipmentType: undefined,
    weaponType: undefined,
    className: undefined,
    pics: undefined
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
    },
    changePics (state, newVal) {
      state.pics = newVal
    },
    clear (state) {
      state.server = undefined
      state.exchangeType = undefined
      state.equipmentType = undefined
      state.weaponType = undefined
      state.weaponType = undefined
    }
  }
}
export default addItem
