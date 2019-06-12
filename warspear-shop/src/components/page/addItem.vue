<template>
  <div  v-if="hackReset" id="addItem">
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
        <mt-cell title="职业" is-link :value="$store.state.addItem.className"  @click.native="popupCurrent(isMC?p.className.mc:p.className.elf)"></mt-cell>
        <mt-cell class="p" :title="'是否死绑：' + isSB2">
          <mt-switch v-model="isSB"></mt-switch>
        </mt-cell>
      </template>
      <template v-if="$store.state.addItem.exchangeType !== '金币'">
        <mt-field label="金币价格" placeholder="单位:k，可留空" v-model="priceGold"></mt-field>
        <mt-field label="人民币价格" placeholder="单位:CNY，可留空" v-model="priceRMB"></mt-field>
      </template>
      <template v-if="$store.state.addItem.exchangeType !== '金币'">
        <mt-cell title="点击上传图片" is-link value="第一张将首页展示" @click.native="goUpload"></mt-cell>
      </template>
      <mt-field label="标签" placeholder="键入标签，以空格隔开" type="text" v-model="tags"></mt-field>
      <mt-field label="添加描述" placeholder="添加描述........" type="textarea" rows="4" v-model="description"></mt-field>
      <mt-button size="large" @click="pushItem">确认发布</mt-button>
    </template>
    <popup-choose v-model="popupVisible" :space-name="'addItem'" ce :name="popupName" :valueList="pickerValues"></popup-choose>
    <div style="height: 53px"></div>
  </div>
</template>

<script>
import popupChoose from '@/components/popup/popupChoose'
import { Toast } from 'mint-ui'
import errorHandle from '@/utils/errorHandler'
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
  className: {
    elf: {
      name: 'ClassName',
      values: ['看守', '双刀', '游侠', '德鲁伊', '圣骑士', '法师', '牧师', '探索者']
    },
    mc: {
      name: 'ClassName',
      values: ['野蛮人', '盗贼', '萨满', '猎人', '死亡骑士', '术士', '死灵法师', '魔术师']
    }
  }
}
export default {
  name: 'addItem',
  components: {popupChoose},
  data () {
    return {
      hackReset: true,
      p: popupDataa,
      popupName: 'Server',
      popupVisible: false,
      pickerValues: [],
      title: '',
      isSell: false,
      isMC: false,
      isSB: true,
      isSB2: '是',
      faction: 'ELF',
      priceGold: '',
      priceRMB: '',
      tags: '',
      description: ''
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
    },
    /**
     * 上传图片
     */
    goUpload () {
      this.$router.push('/uploadPics')
    },
    pushItem () {
      let pics = new Array(0)
      if (this.$store.state.addItem.pics !== undefined) {
        for (let pic of this.$store.state.addItem.pics) {
          if (pic.uploaded === true) {
            pics.push(pic)
          }
        }
      }
      let item = {
        title: this.title,
        exchangeRelationship: this.isSell ? '买' : '卖',
        faction: this.faction,
        server: this.$store.state.addItem.server,
        exchangeType: this.$store.state.addItem.exchangeType,
        equipmentType: this.$store.state.addItem.equipmentType,
        weaponType: this.$store.state.addItem.weaponType,
        emailBindingState: this.isSB,
        className: this.$store.state.addItem.className,
        priceGold: this.priceGold,
        priceRMB: this.priceRMB,
        pics: pics,
        tags: this.tags,
        description: this.description
      }
      this.axios({
        url: '/item/item',
        method: 'post',
        headers: {'token': this.$store.state.user.token},
        data: item
      }).then(() => {
        Toast({
          message: '发布成功'
        })
        this.$store.commit('addItem/clear')
        this.$store.commit('findItem/changeConditionChanged', true)
        this.hackReset = false
        this.$nextTick(() => {
          this.hackReset = true
        })
      }).catch((error) => {
        errorHandle(error)
      })
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
    },
    isSB (newValue) {
      this.isSB2 = newValue ? '是' : '否'
    }
  }
}
</script>

<style scoped>

</style>
