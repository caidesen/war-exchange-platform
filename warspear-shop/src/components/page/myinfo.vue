<template>
  <div id="myinfo">
    <div v-if="$store.state.isLogin">
      <h1>{{$store.state.user.username}}</h1>
      <span>"{{$store.state.user.description}}"</span>
      <hr>
      <mt-button type="primary" size="large" @click="updateUserInfo">修改账号信息</mt-button>
      <br>
      <mt-button type="primary" size="large" @click="updateUserPassword">修改登录密码</mt-button>
      <br>
      <mt-button type="primary" size="large" @click="$router.push('/myitem')">我的发布</mt-button>
      <br>
      <mt-button type="primary" size="large" @click="logout">退出登录</mt-button>

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
          message: '操作成功',
          iconClass: 'icon icon-success'
        })
      }).catch(error => {
        errorHandle(error)
      })
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
