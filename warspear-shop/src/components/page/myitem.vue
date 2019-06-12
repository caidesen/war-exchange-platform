<template>
  <div id="myitem">
    <div id="wrapper" ref="wrapper" :style="{ height: wrapperHeight + 'px' }">
      <div v-infinite-scroll="loadMore" infinite-scroll-disabled="loading"
           infinite-scroll-distance="50">
        <item-cell v-for="(item,i) in items" :item="item" :key="i"></item-cell>
        <p v-show="loading" class="page-infinite-loading">
          <mt-spinner type="fading-circle"></mt-spinner>
          加载中...
        </p>
        <div style="text-align: center"><span v-show="allLoaded"><hr>...没有数据了...</span></div>
      </div>
    </div>
  </div>
</template>

<script>
import ItemCell from '@/components/itemCell'
import { Toast } from 'mint-ui'
import errorHandle from '@/utils/errorHandler'
export default {
  name: 'myitem',
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
      this.axios({
        url: '/item/items/oneself/page/' + this.page + '/size/' + this.size,
        method: 'get',
        headers: {'token': this.$store.state.user.token}
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
            message: '已经没有更多数据啦',
            duration: 1500,
            className: 'errorToast'
          })
        }
      }).catch((error) => {
        errorHandle(error)
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>

<style scoped>
  #wrapper {
    overflow: scroll;
  }
</style>
