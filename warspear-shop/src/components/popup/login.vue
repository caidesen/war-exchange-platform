<template>
  <div id="login">
    <mt-popup class="popup" v-model="$store.state.popup.login" popup-transition="popup-fade">
      <div class="popup-content">
        <div class="p">
          <mt-field class="field" label="邮箱" placeholder="请输入邮箱" type="email" v-model="email" :state="emailState"></mt-field>
        </div>
        <div class="p" style="border-radius:5px">
          <mt-field class="field" label="密码" placeholder="请输入密码" type="password" v-model="password" :state="passwordState"></mt-field>
        </div>
        <mt-cell class="p remember" title="记住我">
          <mt-switch v-model="isRememberMe"></mt-switch>
        </mt-cell>
        <mt-button type="primary" >登录</mt-button>
        <br>
        <br>
        <router-link class="link" to="register">没有账号？点我注册</router-link>
      </div>
    </mt-popup>
  </div>
</template>

<script>
export default {
  name: 'login',
  data () {
    return {
      email: '',
      emailState: '',
      password: '',
      passwordState: '',
      isRememberMe: true
    }
  },
  computed: {
    popupVisible () {
      return this.$store.state.popup.login
    }
  },
  watch: {
    email (newValue) {
      let reg = new RegExp('^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$')
      if (!reg.test(newValue)) {
        this.emailState = 'error'
      } else {
        this.emailState = 'success'
      }
    },
    password (newValue) {
      this.passwordState = newValue === ('') ? 'error' : ''
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
.popup{
  width: 85%;
  border-radius: 10px;
  background: #f3f3f3
}
.field{
  border-radius:8px
}
.popup-content{
  padding: 20px;
  margin: 0 auto
}
.remember{
  background: #f3f3f3;
  text-align: left
}
.link{
  font-size: 12px;
  color: gray;
  text-decoration: none;
}
</style>
