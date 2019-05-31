<template>
  <div id="register">
    <mt-field label="用户名" placeholder="请输入用户名" v-model="username" :state="usernameState">
      <button @click="checkUsername">检查</button>
    </mt-field>
    <mt-field label="邮箱" placeholder="请输入邮箱" type="email" v-model="email"></mt-field>
    <mt-field label="密码" placeholder="请输入密码" type="password" v-model="password" :state="passwordState"></mt-field>
    <mt-field label="确认密码" placeholder="请输入密码" type="password" v-model="passwordAgain" :state="passwordState"></mt-field>
    <mt-field label="qq号" placeholder="请输入qq" type="number" v-model="qqNum"></mt-field>
    <mt-field label="自我介绍" placeholder="自我介绍" type="textarea" rows="3" v-model="description"></mt-field>
    <mt-field label="验证码" v-model="captcha">
      <img :src="captchaUrl+captchaId+'?'+refreshCode" height="45px" width="100px" @click="refresh">
    </mt-field>
    <br>
    <mt-button size="large" @click="register">注册</mt-button>
    <br>
    <div>
      <span>注意事项：</span>
      <br>
      <span>1:用户名不予许重复，应尽量凸显自己身份,避免重名，如us.blackwings.A森</span><br>
      <span>2:邮箱请用真实邮箱，用于激活和登录</span><br>
    </div>
  </div>
</template>

<script>
import { Toast } from 'mint-ui'
import errorHandle from '@/utils/errorHandler'
export default {
  name: 'register',
  data () {
    return {
      usernameState: '',
      username: 'asen',
      email: 'asen001@live.com',
      password: '123456',
      passwordAgain: '',
      passwordState: '',
      qqNum: '503503090',
      description: '叫我阿森就行',
      captcha: '',
      captchaUrl: 'http://192.168.1.100/api/auth/images/captcha/',
      captchaId: (new Date()).valueOf(),
      refreshCode: 0
    }
  },
  methods: {
    checkUsername () {
      this.axios({
        url: '/auth/checkUsername',
        method: 'post',
        params: {
          username: this.username.trim()
        }
      }).then(() => {
        this.usernameState = 'success'
      }).catch((error) => {
        this.usernameState = 'warning'
        errorHandle(error)
      })
    },
    refresh () {
      this.refreshCode++
    },
    register () {
      if (this.password !== this.passwordAgain) {
        this.passwordState = 'warning'
      } else {
        this.passwordState = 'success'
        this.axios({
          url: '/auth/user',
          method: 'post',
          params: {
            username: this.username.trim(),
            password: this.password.trim(),
            email: this.email.trim(),
            qqNum: this.qqNum.trim(),
            description: this.description,
            captcha: this.captcha.trim(),
            captchaId: this.captchaId
          }
        }).then(() => {
          this.$router.go(-1)
          Toast({
            message: '注册成功',
            iconClass: 'icon icon-success'
          })
        }).catch((error) => {
          errorHandle(error)
        })
      }
    }
  }
}
</script>

<style scoped>
span {
  width: 100%;
}
</style>
