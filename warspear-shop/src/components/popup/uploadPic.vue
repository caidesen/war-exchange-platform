<template>
  <div id="uploadPic">
    <mt-popup class="popupUpload" v-model="popupVisible" popup-transition="popup-fade">
      <div class="center-d">
        <input ref="filElem1" id="file" type="file" accept="image/png, image/jpg, image/jpeg" @change="getFile($event)" v-show="false" >
        <div class="img-d"><img :src="src" alt="点击选择" @click="choiceImg"></div>
        <div>
          <template v-if="UploadingProgress">
            <mt-progress :value="progress" :bar-height="5"></mt-progress>
          </template>
          <template v-if="!pic.uploaded">
            <mt-button type="primary" size="small" @click="upload">上传</mt-button>
          </template>
          <template v-if="pic.uploaded">
            <mt-button type="danger" size="small" @click="deletePic">删除</mt-button>
          </template>
        </div>
      </div>
    </mt-popup>
  </div>
</template>

<script>
import { Toast } from 'mint-ui'
import errorHandle from '@/utils/errorHandler'
export default {
  name: 'uploadPic',
  props: {
    value: {
      type: Boolean
    },
    pic: {type: Object}
  },
  data () {
    return {
      popupVisible: false,
      file: '',
      src: '',
      fileNameExtension: '',
      progress: 0,
      Uploading: false,
      UploadingProgress: false
    }
  },
  methods: {
    choiceImg () {
      this.$refs.filElem1.dispatchEvent(new MouseEvent('click'))
    },
    getFile (event) {
      let _this = this
      this.file = event.target.files[0]
      let files = this.file
      if (!event || !window.FileReader) {
        return
      }
      let reader = new FileReader()
      reader.readAsDataURL(files) // 这里是最关键的一步，转换就在这里
      reader.onloadend = function () {
        _this.src = this.result
      }
      this.fileNameExtension = this.file.name.substr(this.file.name.lastIndexOf('.'))
    },
    upload () {
      if (this.Uploading === false) {
        this.Uploading = true
        this.UploadingProgress = true
        this.axios({
          url: '/auth/pic/put/token',
          method: 'get',
          headers: {'token': this.$store.state.user.token},
          params: {
            fileNameExtension: this.fileNameExtension
          }
        }).then((response) => {
          /*
           * 拿到token后
           */
          let date = response.data.data.date
          let url = 'http://v0.api.upyun.com' + response.data.data.uri
          let token = response.data.data.token
          let picName = response.data.data.picName
          console.log(date + '')
          console.log(url + '')
          console.log(token)
          // 拿到请求参数向又拍云发起上传请求
          this.end = 0
          this.progress = 0
          this.axios({
            url: url,
            method: 'put',
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'X-Date': date,
              'Authorization': token
            },
            onUploadProgress: (progressEvent) => {
              if (progressEvent.lengthComputable) {
                let num = Math.round(progressEvent.loaded / progressEvent.total * 100)
                if (num < 98) {
                  setInterval(() => {
                    let diff = num - this.progress
                    if (diff > 0) {
                      this.progress = this.progress + (diff / (diff > 10 ? 40 : 2))
                      diff = num - this.progress
                    }
                  }, 20)
                }
              }
            },
            data: this.file
          }).then(() => {
            // 又拍云上传图片成功,通知后端
            this.axios({
              url: '/item/pic',
              method: 'post',
              headers: {
                'token': this.$store.state.user.token
              },
              params: {'picName': picName}
            }).then((response) => {
              // 至此上传成功
              this.Uploading = false
              Toast({
                message: '操作成功',
                iconClass: 'icon icon-success',
                className: 'errorToast'
              })
              this.end = 100
              this.progress = 100
              setTimeout(() => {
                this.UploadingProgress = false
              }, 500)
              let newPic = {
                'src': 'http://warshop.test.upcdn.net' + response.data.data.picUri,
                'picId': response.data.data.picId
              }
              this.$emit('changeSrc', newPic)
            }).catch((error) => {
              errorHandle(error)
              this.Uploading = false
            })
          }).catch(error => {
            errorHandle(error)
            this.Uploading = false
          })
        }).catch((error) => {
          errorHandle(error)
          this.Uploading = false
        })
      }
    },
    /**
     * 删除
     */
    deletePic () {
      this.axios({
        url: '/item/pic/' + this.pic.picId,
        method: 'delete',
        headers: {
          'token': this.$store.state.user.token
        }
      }).then(() => {
        this.$emit('delete')
        this.src = this.pic.src
        Toast({
          message: '操作成功',
          iconClass: 'icon icon-success',
          className: 'errorToast'
        })
      }).catch((error) => {
        errorHandle(error)
      })
    }
  },
  watch: {
    popupVisible (val) {
      if (val === false) {
        this.$emit('input', val)
      } else {
        this.src = this.pic.src
      }
    },
    value (val) {
      this.popupVisible = val
    }
  }
}
</script>

<style scoped>
  .img-d{
    text-align: center;
  }
  .center-d{
    text-align: center;
  }
  img{
    max-width: 80%;
    max-height: 50%;
    margin-bottom: 10px ;
  }
  .popupUpload{
    width: 77%;
    padding: 10px;
    border-radius:10px;
    background: #f3f3f3
  }
</style>
