<template>
  <div id="updateUserPassword">
    <mt-field label="旧的密码" placeholder="请输入旧密码" v-model="oldPassword" :state="oldPasswordState"></mt-field>
    <mt-field label="新的密码" placeholder="请输入新密码" v-model="newPassword" :state="newPasswordState"></mt-field>
    <mt-field label="确认密码" placeholder="请再此输入密码" v-model="newPasswordAgain" :state="newPasswordState"></mt-field>
    <mt-button size="large" @click="update">确认修改</mt-button>
  </div>
</template>

<script>
import { Toast } from 'mint-ui'
import errorHandle from '@/utils/errorHandler'
export default {
  name: 'updateUserPassword',
  data () {
    return {
      oldPassword: '',
      oldPasswordState: '',
      newPasswordState: '',
      newPassword: '',
      newPasswordAgain: ''
    }
  },
  methods: {
    update () {
      if (this.newPassword === '' || this.newPasswordAgain !== this.newPassword) {
        this.newPasswordState = 'warning'
      } else {
        this.newPasswordState = 'success'
        this.axios({
          url: '/auth/user/password',
          method: 'put',
          headers: {'token': this.$store.state.user.token},
          params: {
            oldPassword: this.oldPassword,
            newPassword: this.newPassword
          }
        }).then(() => {
          // 跳转清空缓存
          this.$router.go(-1)
          localStorage.clear()
          let loginInfo = {
            'username': this.$store.state.user.username,
            'password': this.newPassword
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
}
</script>

<style scoped>

</style>
