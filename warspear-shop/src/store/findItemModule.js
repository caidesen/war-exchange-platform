const findItem = {
  namespaced: true,
  state: {
    condition: {
      title: '',
      server: '',
      isSell: '',
      exchangeType: '',
      equipmentType: '',
      weaponType: '',
      className: '',
      // 排序依据
      sortName: 'userId',
      // 升序0 asc 降序1 desc
      sort: 0
    }
  },
  mutations: {
    changeCondition (state, newVal) {
      state.condition = newVal
    }
  }
}
export default findItem
