<template>
  <div id="updateUserInfo">
    <mt-field label="用户名" placeholder="请输入用户名" v-model="username" :state="usernameState">
      <button @click="checkUsername">检查</button>
    </mt-field>
    <mt-field label="qq号" placeholder="请输入QQ号" v-model="qqNum"></mt-field>
    <mt-field label="自我介绍" placeholder="自我介绍" type="textarea" rows="3" v-model="description"></mt-field>
    <mt-field label="密码" placeholder="请输入旧密码" type="password" v-model="oldPassword" ></mt-field>
    <mt-button size="large" @click="update">确认修改</mt-button>
  </div>
</template>

<script>
import { Toast } from 'mint-ui'
import errorHandle from '@/utils/errorHandler'

export default {
  name: 'updateUserInfo',
  data () {
    return {
      username: this.$store.state.user.username,
      usernameState: '',
      qqNum: this.$store.state.user.qqNum,
      description: this.$store.state.user.description,
      oldPassword: ''
    }
  },
  created: function () {
  },
  methods: {
    checkUsername () {
      if (this.username.trim().length >= 2) {
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
      }
    },
    update () {
      //
      this.axios({
        url: '/auth/user',
        method: 'put',
        headers: {'token': this.$store.state.user.token},
        params: {
          username: this.username.trim(),
          qqNum: this.qqNum.trim(),
          description: this.description,
          password: this.oldPassword
        }
      }).then(() => {
        // 跳转清空缓存
        this.$router.go(-1)
        localStorage.clear()
        let loginInfo = {
          'username': this.username,
          'password': this.oldPassword
        }
        // 重新登录
        this.$store.dispatch('user/login', loginInfo)
        Toast({
          message: '修改成功',
          iconClass: 'icon icon-success'
        })
      }).catch((error) => {
        errorHandle(error)
      })
    }
  }
}
</script>

<style scoped>

</style>
