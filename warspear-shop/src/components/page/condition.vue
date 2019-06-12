<template>
  <div id="condition">
    <mt-field label="标题" placeholder="根据标题搜索" v-model="condition.title"></mt-field>
    <mt-cell title="买？卖？" is-link :value="$store.state.findItem.condition.exchangeRelationship"  @click.native="popupCurrent(p.exchangeRelationship)"></mt-cell>
    <mt-cell title="服务器" is-link :value="$store.state.findItem.condition.server"  @click.native="popupCurrent(p.server)"></mt-cell>
    <mt-cell title="所在阵营" is-link :value="$store.state.findItem.condition.faction" @click.native="popupCurrent(p.faction)"></mt-cell>
    <mt-cell title="类型" is-link :value="$store.state.findItem.condition.exchangeType" @click.native="popupCurrent(p.exchangeType)"></mt-cell>
    <template v-if="$store.state.findItem.condition.exchangeType === '装备'">
      <mt-cell title="装备类型" is-link :value="$store.state.findItem.condition.equipmentType"  @click.native="popupCurrent(p.equipmentType)"></mt-cell>
      <template v-if="$store.state.findItem.condition.equipmentType === '武器'">
        <mt-cell title="武器类型" is-link :value="$store.state.findItem.condition.weaponType"  @click.native="popupCurrent(p.weaponType)"></mt-cell>
      </template>
    </template>
    <template v-if="$store.state.findItem.condition.exchangeType === '账号'">
      <mt-cell title="职业" is-link :value="$store.state.findItem.condition.className"  @click.native="popupCurrent($store.state.findItem.condition.faction==='MC'?p.className.mc:p.className.elf)"></mt-cell>
    </template>
    <mt-cell class="p" :title="'排序:'+sortS">
      <mt-switch v-model="sort"></mt-switch>
    </mt-cell>
    <mt-button size="large" @click="changeCondition">更改筛选条件</mt-button>
    <popup-choose v-model="popupVisible" :space-name="'findItem'" ce :name="popupName" :valueList="pickerValues"></popup-choose>
  </div>
</template>

<script>
import popupChoose from '@/components/popup/popupChoose'
const popupDataa = {
  exchangeRelationship: {
    name: 'ExchangeRelationship',
    values: ['', '买', '卖']
  },
  faction: {
    name: 'Faction',
    values: ['', 'ELF', 'MC']
  },
  server: {
    name: 'Server',
    values: ['', 'US', 'EU', 'BR', 'SEA']
  },
  exchangeType: {
    name: 'ExchangeType',
    values: ['', '装备', '账号', '金币', '其他']
  },
  equipmentType: {
    name: 'EquipmentType',
    values: ['', '布甲', '轻甲', '重甲', '武器', '戒指', '项链', '披风', '腰带']
  },
  weaponType: {
    name: 'WeaponType',
    values: ['', '匕首', '双手剑', '单手剑', '双手斧', '单手斧', '单手锤', '双手锤', '战矛', '盾牌', '法杖', '弓', '弩']
  },
  className: {
    elf: {
      name: 'ClassName',
      values: ['', '看守', '双刀', '游侠', '德鲁伊', '圣骑士', '法师', '牧师', '探索者']
    },
    mc: {
      name: 'ClassName',
      values: ['', '野蛮人', '盗贼', '萨满', '猎人', '死亡骑士', '术士', '死灵法师', '魔术师']
    }
  }
}
export default {
  name: 'condition',
  components: {popupChoose},
  data () {
    return {
      p: popupDataa,
      popupName: 'Server',
      popupVisible: false,
      pickerValues: [],
      condition: {
        title: '',
        // 排序依据
        sortName: 'itemId',
        // 升序0 asc 降序1 desc
        sort: 1
      },
      sort: false,
      sortS: '降序'
    }
  },
  watch: {
    sort (newValue) {
      this.sortS = newValue ? '升序' : '降序'
      this.condition.sort = newValue ? 0 : 1
    }
  },
  methods: {
    popupCurrent (obj) {
      this.pickerValues = obj.values
      this.popupName = obj.name
      this.popupVisible = true
    },
    changeCondition () {
      let condition = this.$store.state.findItem.condition
      condition.title = this.condition.title
      condition.sortName = this.condition.sortName
      condition.sort = this.condition.sort
      this.$store.commit('findItem/changeCondition', condition)
      this.$store.commit('findItem/changeConditionChanged', true)
      this.$router.push('/store')
    }
  }
}
</script>

<style scoped>

</style>
