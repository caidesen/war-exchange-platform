<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div id="item-cell">
    <mt-cell @click.native="handleClick">
      <template v-slot:title>
        <div class="p img-d">
          <img v-lazy.container="item.firstPic" alt="图片">
        </div>
        <div class="p content">
        <span class="small">
          <span class="title" style="color: midnightblue" v-cloak>[{{item.server}}.{{item.faction}}]</span>
          <span class="title" style="color: red" v-cloak>[{{item.exchangeRelationship==='卖'?'出':'收'}}]</span>
        </span>
          <span v-cloak class="title">{{item.title}}</span>
          <div class="tabbar">
          <span class="tab" v-for="(tab,i) in item.tags" :key="i">
            {{tab}}
          </span>
          </div>
          <template v-if="item.exchangeType!=='金币'">
            <div class="p" v-cloak v-if="item.havePrice">
              <span v-if="item.priceRMB!=null" class="rmb">{{item.priceRMB}} CNY</span>
              <span v-if="!(item.priceRMB==null||item.priceGold==null)" class="tab"> or</span>
              <span v-if="item.priceGold!=null" class="gold"> {{gold}}k gold</span>
            </div>
            <div v-else class="p">
              <span class="rmb">价格另议</span>
            </div>
          </template>
          <span class="master" v-cloak>{{item.username}}</span>
        </div>
      </template>
    </mt-cell>
  </div>
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
  computed: {
    gold () {
      return parseFloat(this.item.priceGold)
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
  #item-cell{
    margin-bottom: 7px;
  }
  img {
    max-width: 100%;
    max-height: 100%;

  }

  .p {
    padding: 8px;
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
