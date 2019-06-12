import { Toast } from 'mint-ui'
import store from '@/store/index'
export default function (error) {
  if (error.response) {
    if (error.response.status !== 402) {
      Toast({
        message: error.response.data.message,
        duration: 1500,
        className: 'errorToast'
      })
    } else {
      Toast({
        message: '没有登录或已过期',
        duration: 1500,
        className: 'errorToast'
      })
      store.commit('changeLogin', false)
      localStorage.clear()
    }
  } else {
    Toast({
      message: error,
      duration: 1500,
      className: 'errorToast'
    })
  }
}
