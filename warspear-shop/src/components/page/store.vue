<template>
  <div id="store">
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
  }
}
</script>

<style scoped>

</style>
