<template>
  <div id="test">
    <div class="condition">
      <mt-button size="small" style="height: 100%" plain @click="$router.push('/condition')">条件筛选</mt-button>
    </div>
    <div id="wrapper" ref="wrapper" :style="{ height: wrapperHeight + 'px' }">
      <mt-loadmore :bottom-method="loadBottom" @bottom-status-change="handleBottomChange" :bottom-all-loaded="allLoaded" ref="loadmore">
        <item-cell v-for="(item,i) in items" :item="item" :key="i"></item-cell>
        <div style="height: 50px"></div>
      </mt-loadmore>
    </div>
  </div>
</template>

<script>
import ItemCell from '@/components/itemCell'
import { Toast } from 'mint-ui'
import errorHandle from '@/utils/errorHandler'
export default {
  name: 'test',
  components: {
    ItemCell
  },
  data () {
    return {
      items: [],
      list: [],
      allLoaded: false,
      bottomStatus: '',
      wrapperHeight: 0,
      page: 0,
      size: 7,
      scrollTop: 0
    }
  },
  mounted: function () {
    this.wrapperHeight = document.documentElement.clientHeight
  },
  methods: {
    handleBottomChange (status) {
      this.bottomStatus = status
    },
    loadBottom () {
      const condition = this.$store.state.findItem.condition
      this.axios({
        url: '/item/items/page/' + this.page + '/size/' + this.size,
        method: 'post',
        data: condition
      }).then((response) => {
        if (response.data.data.length !== 0) {
          for (let item of response.data.data) {
            if (item.firstPic == null) {
              item.firstPic = require('@/assets/tupian.svg')
            } else {
              item.firstPic = 'http://warshop.test.upcdn.net' + item.firstPic
            }
            this.items.push(item)
          }
          this.page = this.page + 1
        } else {
          this.allLoaded = true
          Toast({
            message: 'sorry, no more',
            duration: 1500,
            className: 'errorToast'
          })
        }
        this.$refs.loadmore.onBottomLoaded()
      }).catch((error) => {
        errorHandle(error)
      })
    }
  },
  beforeRouteEnter (to, from, next) {
    next((vm) => {
      console.log(vm.$store.state.findItem.conditionChanged)
      if (vm.$store.state.findItem.conditionChanged) {
        vm.$store.commit('findItem/changeConditionChanged', false)
        vm.allLoaded = false
        vm.items = []
        vm.page = 0
      }
    })
  }
}
</script>

<style scoped>
  #wrapper {
    overflow: scroll;
  }
  .condition{
    position: fixed;
    right: 0px;
    bottom: 60px;
    width: 60px;
    height: 40px;
    background-color:rgba(245,245,245,0.97) ;
    z-index: 9999;
    text-align: center;
  }
</style>
