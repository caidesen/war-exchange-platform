<template>
  <div id="myinfo">
    <div v-if="isLogin"></div>
    <div v-else>
      <h1>当前未登录</h1>
      <mt-button type="default" size="large" @click="loginPop">
        点击登录
      </mt-button>
      <login></login>
    </div>
  </div>
</template>

<script>
import login from '@/components/popup/login'
export default {
  name: 'myinfo',
  data () {
    return {
      isRememberMe: true
    }
  },
  computed: {
    isLogin () {
      return this.$store.state.isLogin
    }
  },
  methods: {
    loginPop () {
      this.$store.commit('popup/popupLogin')
    }
  },
  components: {login}
}
</script>

<style scoped>
div{
  text-align: center;
}
.p{
  padding: 10px;
}
</style>
