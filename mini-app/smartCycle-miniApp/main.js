import App from './App'
// import bmap from './common/bmap-wx.js'
//导入网络 请求的包
// import { $http } from '@escook/request-miniprogram'

import uView from './uni_modules/vk-uview-ui';
import store from './store'
// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App
})
// 使用 uView UI
  app.use(uView)
app.$mount()
// import BaiduMap from './node_modules/vue-baidu-map-3x'
// uni.$http = $http

// //请求拦截器
// $http.beforeRequest = function (options) {
// 	uni.showLoading({
// 		title: '数据加载中...',
		
// 	})
// }
// //响应拦截器
// $http.afterRequest = function (options) {
// 	uni.hideLoading()
// }

// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
   // 使用 uView UI
  app.use(uView)
  
  
  return {
    app
  }
}
// #endif