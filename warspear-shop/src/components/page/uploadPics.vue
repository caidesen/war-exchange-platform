<template>
  <div id="uploadPics">
    <div>
      <div v-for="(pic,i) in pics" class="pic-d" @click="pupupUpload(i)" :key="i">
        <div id="top"><img :src="pic.src" alt="图片"></div>
        <div><span>{{i+1}}</span></div>
      </div>
      <div class="pic-d"></div>
    </div>

    <p>只要显示删除按钮即为上传成功，由于网络原因无法及时回显，请不要刷新页面，刷新页面会导致数据丢失！</p>
    <upload-pic @changeSrc="changeSrc" @delete="deleteSrc"
                :pic="pics[uploading]"
                v-model="popupVisible"></upload-pic>
  </div>
</template>

<script>
// import { Toast } from 'mint-ui'
// import errorHandle from '@/utils/errorHandler'
import uploadPic from '@/components/popup/uploadPic'
export default {
  name: 'uploadPics',
  components: {uploadPic},
  data () {
    return {
      initialPic: require('@/assets/tabbar/add.png'),
      pics: [
        {src: undefined, uploaded: false, picId: undefined},
        {src: undefined, uploaded: false, picId: undefined},
        {src: undefined, uploaded: false, picId: undefined},
        {src: undefined, uploaded: false, picId: undefined},
        {src: undefined, uploaded: false, picId: undefined}
      ],
      popupVisible: false,
      uploading: 0
    }
  },
  computed: {
    uploaded () {
      return this.$store.state.addItem.uploaded
    }
  },
  watch: {
    uploaded (newValue) {
      if (newValue === true) {
        this.pics = [
          {src: undefined, uploaded: false, picId: undefined},
          {src: undefined, uploaded: false, picId: undefined},
          {src: undefined, uploaded: false, picId: undefined},
          {src: undefined, uploaded: false, picId: undefined},
          {src: undefined, uploaded: false, picId: undefined}
        ]
      }
      for (let pic of this.pics) {
        pic.src = this.initialPic
      }
    }
  },
  methods: {
    pupupUpload (i) {
      this.popupVisible = true
      this.uploading = i
    },
    changeSrc (newSrc) {
      this.pics[this.uploading].src = newSrc.src
      this.pics[this.uploading].uploaded = true
      this.pics[this.uploading].picId = newSrc.picId
      this.$store.commit('addItem/changePics', this.pics)
    },
    deleteSrc () {
      this.pics[this.uploading].src = this.initialPic
      this.pics[this.uploading].uploaded = false
      this.pics[this.uploading].picId = undefined
      this.$store.commit('addItem/changePics', this.pics)
    }
  },
  mounted () {
    for (let pic of this.pics) {
      pic.src = this.initialPic
    }
  }
}
</script>

<style scoped>
  #top{
    height: 100px;
  }
 .pic-d{
   text-align: center;
   width: 27%;
   height: 150px;
   float: left;
   padding: 3.166667%;
 }
 img {
   max-width: 100%;
   max-height: 100%;
 }
</style>
