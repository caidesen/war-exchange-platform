<template>
  <div id="app">
    <item-cell @pop="ppp"></item-cell>
    <item-cell @pop="ppp"></item-cell>
    <item-cell @pop="ppp"></item-cell>
    <item-cell @pop="ppp"></item-cell>
    <item-cell @pop="ppp"></item-cell>
    <item-cell @pop="ppp"></item-cell>
    <div style="height: 55px;background: aqua"></div>
  </div>
</template>

<script>
import ItemCell from './components/item-cell'
export default {
  name: 'App',
  components: {
    ItemCell
  },
  data () {
    return {
      popupVisible: false
    }
  },
  methods: {
    ppp (id) {
      this.popupVisible = true
    }
  },
  mounted () {
    this.popupVisible = false
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
