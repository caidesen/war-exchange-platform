<template>
  <div id="app">
    <store></store>
    <div style="height: 55px;"></div>
    <my-footer></my-footer>
  </div>
</template>

<script>
import myFooter from './components/footer'
import store from './components/page/store'
export default {
  name: 'App',
  components: {
    myFooter, store
  },
  data () {
    return {
      item: {
        id: 1,
        srcUrl: require('@/assets/item-test.png'),
        title: '22级蓝戒指 +2 法师用 体力',
        tabList: ['法师', '戒指'],
        rmb: 10.00,
        gold: 3
      }
    }
  }
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }
</style>
