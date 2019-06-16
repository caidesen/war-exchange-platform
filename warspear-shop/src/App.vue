<template>
  <div id="app">
    <template v-if="visible">
      <div style="height: 47px"></div>
      <div class="header">
        <i class="mintui mintui-back page-back" @click="back"></i>
        <div class="header-text">
          {{$store.state.title}}
        </div>
      </div>
    </template>
    <template v-else>
      <my-footer></my-footer>
    </template>
    <keep-alive exclude="itemDetailed">
      <router-view></router-view>
    </keep-alive>
    <template v-if="!this.$store.state.isLogin"><login></login></template>
  </div>
</template>

<script>
import myFooter from './components/footer'
import login from '@/components/popup/login'
export default {
  name: 'App',
  computed: {
    visible () {
      let flag = true
      let path = this.$route.path
      if (path === '/' || path === '/store' || path === '/addItem' || path === '/myInfo') {
        flag = false
      }
      return flag
    }
  },
  methods: {
    back () {
      this.$router.go(-1)
    }
  },
  components: {myFooter, login}
}
</script>

<style>
  html, body {
    background-color: #fafafa;
    -webkit-overflow-scrolling: touch;
    user-select: none;
    margin:0;
    padding:0;
  }
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }
  .page-back {
    display: inline-block;
    position: absolute 12px * *10px;
    width: 40px;
    height: 40px;
    text-align: center;
  }
  .header{
    position: fixed;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 40px;
    background-color: #ffffff ;
    z-index: 9999;
  }
  .header-text{
    position: fixed;
    left: 33%;
    top: 0px;
    width: 33%;
    height: 40px;
    background-color: #ffffff ;
    z-index: 9999;
    text-align: center;
    line-height: 40px;
  }
  i {
    font-size: 24px;
    line-height: 40px;
  }
  .errorToast{
    z-index: 9999;
  }
</style>
