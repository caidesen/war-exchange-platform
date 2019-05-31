<template>
  <div id="store">
    <div class="condition">
        <mt-button size="small" style="height: 100%" plain>条件筛选</mt-button>
    </div>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
    <item-cell :item="item"></item-cell>
  </div>
</template>

<script>
import ItemCell from '@/components/itemCell'
export default {
  name: 'store',
  components: {
    ItemCell
  },
  data () {
    return {
      item: {
        id: 1,
        srcUrl: require('@/assets/item-test.png'),
        title: '22级蓝戒指 +2 法师用 体力',
        tabList: ['法师', '戒指'],
        rmb: 10.00,
        gold: 3
      }
    }
  },
  mounted: function () {
  },
  methods: {
  }
}
</script>

<style scoped>
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
