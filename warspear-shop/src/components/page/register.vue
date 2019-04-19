<template>
  <div id="register">
    <mt-field label="用户名" placeholder="请输入用户名" v-model="username"></mt-field>
    <mt-field label="邮箱" placeholder="请输入邮箱" type="email" v-model="email"></mt-field>
    <mt-field label="密码" placeholder="请输入密码" type="password" v-model="password"></mt-field>
    <mt-field label="手机号" placeholder="请输入手机号" type="tel" v-model="phone"></mt-field>
    <mt-field label="数字" placeholder="请输入qq" type="number" v-model="qq"></mt-field>
    <mt-field label="自我介绍" placeholder="自我介绍" type="textarea" rows="3" v-model="introduction"></mt-field>
    <mt-field label="验证码" v-model="captcha">
      <img src="@/assets/verification-test.png" height="45px" width="100px">
    </mt-field>
    <br>
    <mt-button size="large" >注册</mt-button>
  </div>
</template>

<script>
export default {
  name: 'register',
  data () {
    return {
      username: '',
      email: '',
      password: '',
      phone: '',
      qq: '',
      introduction: '',
      captcha: ''
    }
  }
}
</script>

<style scoped>

</style>
