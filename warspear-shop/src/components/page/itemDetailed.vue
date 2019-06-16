<template>
  <div id="itemDetailed">
    <template v-cloak v-if="$store.state.user.username===item.username">
      <mt-button size="small" class="condition" @click="deleteItem" plain >删除</mt-button>
    </template>
    <template v-cloak v-if="state">
      <div class="center">
        <span class="exchange-relationship">[{{item.exchangeRelationship==='买'?'收购':'出售'}}]</span>
        <span class="title" v-text="item.title"></span>
        <br>
        <span>{{item.username}}</span>
      </div>
      <hr>
      <p>发布时间：{{item.createTime | dateFormate}}</p>
      <p v-cloak v-if="item.tags!==null">
        <span>标签：</span>
        <span class="tab" v-for="(tag,i) in item.tags" :key="i" v-text="tag + ' '"></span>
      </p>
      <p><span>所处服务器：{{item.server}}.{{item.faction}}</span></p>
      <p>
        <span>类型：{{item.exchangeType}}</span>
        <span v-if="item.exchangeType==='装备'">- {{item.equipmentType}}</span>
        <span v-if="item.equipmentType==='武器'">- {{item.weaponType}}}</span>
      </p>
      <template v-if="item.exchangeType==='账号'">
        <p>职业：{{item.className}}</p>
        <p v-if="item.emailBindingState">死绑吗：{{item.emailBindingState?'是':'否'}}</p>
      </template>
      <mt-button size="small" style="width: 100%" plain @click.native="getQQ" ref="button">联系方式</mt-button>
      <mt-popup v-model="popupVisible" class="mint-popup-1" :style="{ top: buttonBottom + 'px' }">
        <p>
          <span>qq:</span>
          <span id="copy">{{item.qqNum}}</span>
        </p>
      </mt-popup>

      <hr>
      <p>详细说明：</p>
      <p>{{item.description}}</p>
      <hr>
      <div>
        <span>图片：</span>
        <img v-for="(pic,i) in item.pics" :src="'http://warshop.test.upcdn.net'+pic" alt="图片" :key="i">
      </div>
    </template>
  </div>
</template>

<script>
import { Toast, MessageBox } from 'mint-ui'
import errorHandle from '@/utils/errorHandler'
import {dateFormate} from '@/utils/dataFormat'
export default {
  name: 'itemDetailed',
  data () {
    return {
      itemId: null,
      state: false,
      popupVisible: false,
      buttonBottom0: 0,
      buttonBottom: 0,
      item: {
        tags: null,
        server: null
      }
    }
  },
  mounted () {
    // 加载vuex中itemId
    this.itemId = this.$store.state.findItem.itemId
    // 如果vuex中没值，就从缓存中加载
    if (!this.itemId) {
      this.itemId = sessionStorage.getItem('itemId')
    }
    this.axios({
      url: '/item/detail/' + this.itemId,
      method: 'get',
      headers: {'token': this.$store.state.user.token}
    }).then((response) => {
      this.item = response.data.data
      this.state = true
      sessionStorage.setItem('itemId', this.item.itemId)
    }).catch((e) => {
      errorHandle(e)
    })
  },
  filters: {
    dateFormate (value) {
      var date = new Date(value)
      return dateFormate(date, 'yyyy-MM-dd hh:mm')
    }
  },
  methods: {
    getQQ () {
      this.buttonBottom = this.$refs.button.$el.getBoundingClientRect().bottom + 10
      this.popupVisible = true
    },
    deleteItem () {
      MessageBox.confirm('确定执行此操作?').then(() => {
        this.axios({
          url: '/item/item/' + this.itemId,
          method: 'delete',
          headers: {'token': this.$store.state.user.token}
        }).then(() => {
          Toast({
            message: '删除成功'
          })
          this.$store.commit('findItem/changeConditionChanged', true)
          this.$router.push('/store')
        }).catch((e) => {
          errorHandle(e)
        })
      }).catch((e) => {})
    }
  }
}
</script>

<style scoped>
  .center{
    text-align: center;
  }
  .title{
    font-size: 20px;
  }
  .exchange-relationship{
    color: red;
  }
  img{
    width: 100%;
    margin-top: 20px;
  }
  #copy{
    user-select:auto;
  }
  .mint-popup-1 {
    border-radius: 10px;
    padding: 10px;
    transform: translate(-50%, 0);
  }
  .condition{
    position: fixed;
    right: 5px;
    top: 70px;
    width: 60px;
    height: 40px;
    background-color:rgba(245,245,245,0.97) ;
    z-index: 9999;
    text-align: center;
  }
</style>
