<template>
  <div id="addItem">
    <mt-cell class="p" :title="isSell?'我要买':'我要卖'">
      <mt-switch v-model="isSell"></mt-switch>
    </mt-cell>
    <mt-field label="标题" placeholder="请输入标题" v-model="title"></mt-field>
    <template v-if="!titleIsEmpty">
      <mt-cell title="服务器" is-link :value="$store.state.addItem.server"  @click.native="chooseServer"></mt-cell>
      <mt-cell class="p" :title="'所在阵营：' + faction">
        <mt-switch v-model="isMC"></mt-switch>
      </mt-cell>
      <mt-cell title="类型" is-link :value="$store.state.addItem.exchangeType"  @click.native="chooseExchangeType"></mt-cell>
      <template v-if="$store.state.addItem.exchangeType === '装备'">
        <mt-cell title="装备类型" is-link :value="$store.state.addItem.equipmentType"  @click.native="chooseEquipmentType"></mt-cell>
        <template v-if="$store.state.addItem.equipmentType === '武器'">
          <mt-cell title="武器类型" is-link :value="$store.state.addItem.weaponType"  @click.native="chooseWeaponType"></mt-cell>
        </template>
      </template>
      <template v-if="$store.state.addItem.exchangeType === '账号'">
      </template>
      <mt-cell title="上传图片" is-link value="第一张将首页展示"></mt-cell>
      <mt-field label="标签" placeholder="键入标签，以空格隔开" type="text" v-model="tabs"></mt-field>
      <mt-field label="添加描述" placeholder="添加描述........" type="textarea" rows="4" v-model="introduction"></mt-field>
    </template>
    <popup-choose v-model="popupVisible" :name="popupName" :valueList="pickerValues"></popup-choose>
  </div>
</template>

<script>
import popupChoose from '@/components/popup/popupChoose'
const popupDataa = {
  server: {
    name: 'Server',
    values: ['US', 'EU', 'BR', 'SEA']
  },
  exchangeType: {
    name: 'ExchangeType',
    values: ['装备', '账号', '金币', '其他']
  },
  equipmentType: {
    name: 'EquipmentType',
    values: ['布甲', '轻甲', '重甲', '武器', '戒指', '项链', '披风', '腰带']
  },
  weaponType: {
    name: 'WeaponType',
    values: ['匕首', '双手剑', '单手剑', '双手斧', '单手斧', '单手锤', '双手锤', '战矛', '盾牌', '法杖', '弓', '弩']
  }
}
export default {
  name: 'addItem',
  components: {popupChoose},
  data () {
    return {
      popupName: 'Server',
      popupVisible: false,
      pickerValues: [],
      title: '',
      isSell: false,
      server: '123',
      isMC: false,
      faction: '',
      tabs: '',
      introduction: ''
    }
  },
  methods: {
    chooseServer () {
      this.pickerValues = popupDataa.server.values
      this.popupName = popupDataa.server.name
      this.popupVisible = true
    },
    chooseExchangeType () {
      this.pickerValues = popupDataa.exchangeType.values
      this.popupName = popupDataa.exchangeType.name
      this.popupVisible = true
    },
    chooseEquipmentType () {
      this.pickerValues = popupDataa.equipmentType.values
      this.popupName = popupDataa.equipmentType.name
      this.popupVisible = true
    },
    chooseWeaponType () {
      this.pickerValues = popupDataa.weaponType.values
      this.popupName = popupDataa.weaponType.name
      this.popupVisible = true
    }
  },
  computed: {
    titleIsEmpty () {
      return this.title.trim() === ''
    }
  },
  watch: {
    isMC (newValue) {
      this.faction = newValue ? 'MC' : 'ELF'
    }
  }
}
</script>

<style scoped>

</style>
