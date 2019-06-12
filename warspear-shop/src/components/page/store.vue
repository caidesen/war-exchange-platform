<template>
  <div id="store">
    <div class="condition">
      <mt-button size="small" style="height: 100%" plain @click="$router.push('/condition')">条件筛选</mt-button>
    </div>
    <div id="wrapper" ref="wrapper" :style="{ height: wrapperHeight + 'px' }">
      <div v-infinite-scroll="loadMore" infinite-scroll-disabled="loading"
           infinite-scroll-distance="50">
        <item-cell v-for="(item,i) in items" :item="item" :key="i"></item-cell>
        <p v-show="loading" class="page-infinite-loading">
          <mt-spinner type="fading-circle"></mt-spinner>
          加载中...
        </p>
        <div style="text-align: center"><span v-show="allLoaded"><hr>...没有数据了...</span></div>
        <div style="height: 55px"></div>
      </div>
    </div>
  </div>
</template>

<script>
import ItemCell from '@/components/itemCell'
import { Toast } from 'mint-ui'
import errorHandle from '@/utils/errorHandler'
export default {
  name: 'store',
  components: {
    ItemCell
  },
  data () {
    return {
      items: [],
      list: [],
      bottomStatus: '',
      page: 0,
      size: 5,
      scrollTop: 0,
      loading: false,
      allLoaded: false,
      wrapperHeight: 0
    }
  },
  mounted: function () {
    this.wrapperHeight = document.documentElement.clientHeight
  },
  methods: {
    loadMore () {
      if (this.allLoaded) {
        return
      }
      this.loading = true
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
          if (this.page !== 1) {
            Toast({
              message: '已经没有更多数据啦',
              duration: 1500,
              className: 'errorToast'
            })
          }
        }
      }).catch((error) => {
        errorHandle(error)
      }).finally(() => {
        this.loading = false
      })
    }
  },
  activated () {
    let scrollTop = this.scrollTop
    if (this.$store.state.findItem.conditionChanged) {
      this.$store.commit('findItem/changeConditionChanged', false)
      this.allLoaded = false
      this.items = []
      this.page = 0
      this.loadMore()
      scrollTop = 0
    }
    this.$refs.wrapper.scrollTop = scrollTop
  },
  beforeRouteLeave (to, from, next) {
    this.scrollTop = this.$refs.wrapper.scrollTop
    next()
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
