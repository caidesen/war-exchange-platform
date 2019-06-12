<template>
  <div id="popupChoose">
    <mt-popup class="popup" v-model="popupVisible" position="bottom">
      <div>
        <div class="page-picker-wrapper">
          <mt-picker :slots="slot" @change="change" :visible-item-count="3" :item-height="42"></mt-picker>
        </div>
        <div style="height: 55px"></div>
      </div>
    </mt-popup>
  </div>
</template>

<script>
export default {
  name: 'popupChoose',
  props: {
    value: {
      type: Boolean
    },
    valueList: {
      type: Array
    },
    name: {
      type: String
    },
    spaceName: {
      type: String
    }
  },
  data () {
    return {
      popupVisible: false
    }
  },
  computed: {
    slot () {
      return [
        {
          flex: 1,
          values: this.valueList,
          className: 'slot1'
        }
      ]
    }
  },
  methods: {
    change (picker, values) {
      this.$store.commit(this.spaceName + '/change' + this.name, values[0])
    }
  },
  watch: {
    popupVisible (val) {
      if (val === false) {
        this.$emit('input', val)
      }
    },
    value (val) {
      this.popupVisible = val
    }
  }
}
</script>

<style scoped>
  .popup{
    width: 100%;
    background: #f3f3f3;
  }
  .page-picker-wrapper{
    padding-top: 10px;
  }
</style>
