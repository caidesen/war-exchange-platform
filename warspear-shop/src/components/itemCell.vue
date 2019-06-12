<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <mt-cell @click.native="handleClick">
    <template v-slot:title>
      <div class="p img-d">
        <img :src="item.firstPic" alt="图片">
      </div>
      <div class="p content">
        <span class="small">
          <span class="title" style="color: midnightblue">[{{item.server}}.{{item.faction}}]</span>
          <span class="title" style="color: red">[{{item.exchangeRelationship==='卖'?'出':'收'}}]</span>
        </span>
        <span class="title">{{item.title}}</span>
        <div class="tabbar">
          <span class="tab" v-for="(tab,i) in item.tags" :key="i">
            {{tab}}
          </span>
        </div>
        <div class="p">
          <span v-if="item.priceRMB!=null" class="rmb">{{item.priceRMB}} CNY</span>
          <span v-if="!(item.priceRMB==null||item.priceGold==null)" class="tab"> or</span>
          <span v-if="item.priceGold!=null" class="gold"> {{item.priceGold}}k gold</span>
        </div>
        <span class="master">{{item.username}}</span>
      </div>

    </template>
  </mt-cell>
</template>

<script>
export default {
  props: {
    item: {
      type: Object
    }
  },
  data () {
    return {
    }
  },
  name: 'itemCell',
  methods: {
    handleClick () {
      this.$store.commit('findItem/changeItemId', this.item.itemId)
      this.$router.push('/itemDetailed.vue')
    }
  }
}
</script>

<style scoped>
  img {
    max-width: 100%;
    max-height: 100%;

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
    width: 100%;
  }

  .img-d {
    width: 6rem;
    height: 7rem;
    float: left;
    text-align: center;
  }
  .small {
    font-size: 10px;
  }
</style>
