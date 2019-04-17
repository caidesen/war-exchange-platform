// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import MINT from 'mint-ui'
import store from './store'
Vue.use(MINT)

Vue.config.productionTip = false
Vue.filter('money', function (val) {
  val = val.toString().replace(/[$,]/g, '')
  if (isNaN(val)) {
    val = '0'
  }
  val = Math.floor(val * 100 + 0.50000000001)
  let cents = val % 100
  val = Math.floor(val / 100).toString()
  if (cents < 10) {
    cents = '0' + cents
  }
  for (var i = 0; i < Math.floor((val.length - (1 + i)) / 3); i++) {
    val = val.substring(0, val.length - (4 * i + 3)) + ',' + val.substring(val.length - (4 * i + 3))
  }

  return (val + '.' + cents)
})
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
})
