<template>
  <div id="app">
    <img src="./assets/logo.png">
  </div>
</template>

<script>
import { Indicator } from 'mint-ui'
export default {
  name: 'App'
}

Indicator.open({
  text: '加载中...',
  spinnerType: 'fading-circle'
})
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
