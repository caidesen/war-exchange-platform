<template>
  <div id="myinfo">
    <div v-if="isLogin">
      <h1>{{$store.state.user.username}}</h1>
      <span>"{{$store.state.user.introduction}}"</span>
      <hr>
      <mt-button type="primary" size="large" @click="loginPop">修改账号信息</mt-button>
      <br>
      <mt-button type="primary" size="large" @click="loginPop">我的发布</mt-button>
    </div>
    <div v-else>
      <h1>当前未登录</h1>
      <mt-button type="default" size="large" @click="loginPop">
        点击登录
      </mt-button>
    </div>
  </div>
</template>

<script>
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
  }
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
