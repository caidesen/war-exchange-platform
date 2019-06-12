const findItem = {
  namespaced: true,
  state: {
    condition: {
      title: '',
      server: '',
      faction: '',
      exchangeRelationship: '',
      exchangeType: '',
      equipmentType: '',
      weaponType: '',
      className: '',
      // 排序依据
      sortName: 'itemId',
      // 升序0 asc 降序1 desc
      sort: 1
    },
    itemId: undefined,
    conditionChanged: false
  },
  mutations: {
    changeExchangeRelationship (state, newVal) {
      state.condition.exchangeRelationship = newVal
    },
    changeFaction (state, newVal) {
      state.condition.faction = newVal
    },
    changeServer (state, newVal) {
      state.condition.server = newVal
    },
    changeExchangeType (state, newVal) {
      state.condition.exchangeType = newVal
    },
    changeEquipmentType (state, newVal) {
      state.condition.equipmentType = newVal
    },
    changeWeaponType (state, newVal) {
      state.condition.weaponType = newVal
    },
    changeClassName (state, newVal) {
      state.condition.className = newVal
    },
    changeCondition (state, newVal) {
      state.condition = newVal
    },
    changeItemId (state, newVal) {
      state.itemId = newVal
    },
    changeConditionChanged (state, newVal) {
      state.conditionChanged = newVal
    }
  }
}
export default findItem
