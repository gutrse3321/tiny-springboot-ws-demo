<template>
  <div class="main">
    {{ msg }}
  </div>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  data() {
    return {
      msg: '',
      holderId: "me",
      ws: ''
    }
  },
  created() {
    this.ws = new WebSocket('ws://127.0.0.1:8080' + '/ping/' + this.holderId)
    this.ws.onopen = this.onOpen
    this.ws.onerror = this.onError
    this.ws.onmessage = this.onMessage
    this.ws.onclose = this.onClose
  },
  methods: {
    /**
     * 连接成功的操作
     */
    onOpen() {
      // do something...日志或者发送报文给服务端
      console.log('连接成功')
    },
    onError(e) {
      //连接异常
      //可以做重连的操作
      console.log('连接异常: ', e)
    },
    /**
     * 接收数据
     * @param msg
     * @returns {*}
     */
    onMessage(msg) {
      let res = msg.data
      console.log(msg)
      this.msg = res
      this.ws.send('我收到了')
    },
    /**
     * 发送消息给服务端
     * @param msg
     */
    onSend(msg) {
      this.ws.send(msg)
    },
    /**
     * 连接关闭的时候会走
     * @param e
     */
    onClose(e) {
      console.log('断开连接：', e)
    }
  }
})
</script>

<style lang="stylus" scoped>
.main
  display: flex
  justify-content: center
  align-content: center
  width: 100%
  height: 100%
</style>