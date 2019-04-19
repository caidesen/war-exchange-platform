<template>
  <div class="footer">
    <div class="tab" v-for="(tab,i) in tabs" :key="i" style="font-size: 0px;" @click="handleClick(i)">
      <img :src="tab.img" alt="图片" style="height: 22px;padding-top: 7px;vertical-align: middle;"><br>
      <span v-text="tab.name" style="font-size: 12px;"></span>
      <template v-if="tab.isAction">
        <div style="border-radius:5px;background: gray;height: 5px;width: 10px;margin:0 auto">
        </div>
      </template>
    </div>

  </div>
</template>

<script>
export default {
  name: 'my-footer',
  data () {
    return {
      tabs: [
        {
          img: require('@/assets/tabbar/store.png'),
          name: '市场',
          page: 'store',
          isAction: true
        },
        {
          img: require('@/assets/tabbar/add.png'),
          name: '发布',
          page: 'addItem',
          isAction: false
        },
        {
          img: require('@/assets/tabbar/my.png'),
          name: '我的',
          page: 'myInfo',
          isAction: false
        }
      ]
    }
  },
  methods: {
    handleClick (i) {
      this.clean()
      this.tabs[i].isAction = true
      this.$router.push(this.tabs[i].page)
    },
    clean () {
      for (let i = 0; i < this.tabs.length; i++) {
        this.tabs[i].isAction = false
      }
    }
  }
}
</script>

<style scoped>
  .tab{
    width: 33.3%;
    height: 100%;
    float: left;
  }
  .footer {
    position: fixed;
    left: 0px;
    bottom: 0px;
    width: 100%;
    height: 55px;
    background-color:rgba(245,245,245,0.97) ;
    z-index: 9999;
    text-align: center;
  }

</style>
