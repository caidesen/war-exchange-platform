<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <mt-cell @click.native="handleClick">
    <template v-slot:title>
      <div class="p img-d">
        <img :src="item.srcUrl" alt="图片">
      </div>
      <div class="p content">
        <span class="title">{{item.title}}</span>
        <div class="tabbar">
          <span class="tab" v-for="(tab,i) in item.tabList" :key="i">
            {{tab}}
          </span>
        </div>
        <div class="p">
          <span class="rmb">{{item.rmb | money}} CNY</span>
          <span class="tab"> or</span>
          <span class="gold"> {{item.gold}}k gold</span>
        </div>
        <span class="master">us.asen</span>
      </div>
    </template>
  </mt-cell>
</template>

<script>
export default {
  props: {
    item: {
      type: Object
      // default: function () {
      //   return {
      //     id: 1,
      //     srcUrl: require('@/assets/item-test.png'),
      //     title: '22级蓝戒指 +2 法师用 体力',
      //     tabList: function () {
      //       return ['法师', '戒指']
      //     },
      //     rmb: 10.00,
      //     gold: 3
      //   }
      // }
    }
  },
  name: 'item-cell',
  methods: {
    handleClick () {
      this.$emit('pop', this.item.id)
    }
  }
}
</script>

<style scoped>
  img {
    height: 100%;
  }

  .p {
    padding: 10px;
  }

  .title {
    color: black;
    font-size: 14px;
  }

  .master {
    color: #2c3e50;
    font-size: 14px;
  }
  .tabbar{
    padding-top: 5px;
  }
  .tab {
    color: gray;
    font-size: 12px;
  }

  .rmb {
    color: red;
    font-size: 18px;
  }

  .gold {
    color: gold;
    font-size: 18px;
  }

  .content {
    height: 6rem;
  }

  .img-d {
    width: 6rem;
    height: 6rem;
    float: left;
  }
</style>
