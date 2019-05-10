<template>
  <div id="addItem">
    <mt-cell class="p" :title="isSell?'我要买':'我要卖'">
      <mt-switch v-model="isSell"></mt-switch>
    </mt-cell>
    <mt-field label="标题" placeholder="请输入标题" v-model="title"></mt-field>
    <template v-if="!titleIsEmpty">
      <mt-cell title="服务器" is-link :value="$store.state.addItem.server"  @click.native="popupCurrent(p.server)"></mt-cell>
      <mt-cell class="p" :title="'所在阵营：' + faction">
        <mt-switch v-model="isMC"></mt-switch>
      </mt-cell>
      <mt-cell title="类型" is-link :value="$store.state.addItem.exchangeType"  @click.native="popupCurrent(p.exchangeType)"></mt-cell>
      <template v-if="$store.state.addItem.exchangeType === '装备'">
        <mt-cell title="装备类型" is-link :value="$store.state.addItem.equipmentType"  @click.native="popupCurrent(p.equipmentType)"></mt-cell>
        <template v-if="$store.state.addItem.equipmentType === '武器'">
          <mt-cell title="武器类型" is-link :value="$store.state.addItem.weaponType"  @click.native="popupCurrent(p.weaponType)"></mt-cell>
        </template>
      </template>
      <template v-if="$store.state.addItem.exchangeType === '账号'">
        <mt-cell title="职业" is-link :value="$store.state.addItem.class"  @click.native="popupCurrent(isMC?p.class.mc:p.class.elf)"></mt-cell>
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
  },
  class: {
    elf: {
      name: 'Class',
      values: ['看守', '双刀', '游侠', '德鲁伊', '圣骑士', '法师', '牧师', '探索者']
    },
    mc: {
      name: 'Class',
      values: ['野蛮人', '盗贼', '萨满', '猎人', '死亡骑士', '术士', '死灵法师', '魔术师']
    }
  }
}
export default {
  name: 'addItem',
  components: {popupChoose},
  data () {
    return {
      p: popupDataa,
      popupName: 'Server',
      popupVisible: false,
      pickerValues: [],
      title: '',
      isSell: false,
      isMC: false,
      faction: '',
      tabs: '',
      introduction: ''
    }
  },
  methods: {
    /**
     * 通用的弹出方法
     * @param obj
     */
    popupCurrent (obj) {
      this.pickerValues = obj.values
      this.popupName = obj.name
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
