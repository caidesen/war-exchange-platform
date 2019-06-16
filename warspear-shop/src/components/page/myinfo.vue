<template>
  <div id="myinfo">
    <div v-cloak v-if="$store.state.isLogin">
      <h1>{{$store.state.user.username}}</h1>
      <span>"{{$store.state.user.description}}"</span>
      <hr>
      <template v-cloak v-if="$store.state.user.state&& resendBotton">
        <mt-button type="primary" size="large" @click="resend">重发激活邮件</mt-button>
      </template>
      <br>
      <mt-button type="primary" size="large" @click="updateUserInfo">修改账号信息</mt-button>
      <br>
      <mt-button type="primary" size="large" @click="updateUserPassword">修改登录密码</mt-button>
      <br>
      <mt-button type="primary" size="large" @click="$router.push('/myitem')">我的发布</mt-button>
      <br>
      <mt-button type="primary" size="large" @click="logout">退出登录</mt-button>
      <template  v-if="$store.state.user.state">
        <div class="p left">
          <p>
            <span>关于激活：</span>
            <br>
            <span>1. 未完成激活无法发布</span><br>
            <span>2. 邮箱发送有延迟，请耐心等待片刻</span><br>
            <span>3. 邮件可能被邮箱系统判定为垃圾邮箱，请检查</span><br>
          </p>
        </div>
      </template>
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
import { Toast } from 'mint-ui'
import errorHandle from '@/utils/errorHandler'
export default {
  name: 'myinfo',
  data () {
    return {
      resendBotton: true,
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
      this.$store.commit('popup/popupLogin', true)
    },
    updateUserInfo () {
      this.$router.push('/updateUserInfo')
    },
    updateUserPassword () {
      this.$router.push('/updateUserPassword')
    },
    logout () {
      localStorage.clear()
      this.axios({
        url: '/auth/logout',
        method: 'get',
        headers: {'token': this.$store.state.user.token}
      }).then(() => {
        this.$store.commit('changeLogin', false)
        localStorage.clear()
        Toast({
          message: '操作成功'
        })
      }).catch(error => {
        errorHandle(error)
      })
    },
    resend () {
      this.axios({
        url: '/auth/activation/resend/email',
        method: 'get',
        headers: {'token': this.$store.state.user.token}
      }).then(() => {
        this.resendBotton = false
        Toast({
          message: '请求成功'
        })
      }).catch(error => {
        errorHandle(error)
      })
    }
  }
}
</script>

<style scoped>
#myinfo{
  text-align: center;
}
.p{
  padding: 10px;
}
  .left{
    text-align: left;
  }
</style>
