import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// import Vue from 'vue'
import BaiduMap from 'vue-baidu-map-3x'
createApp(App).use(BaiduMap, { ak: '2i1BaqGjolH3zHe75BHEf4VItP04tPU3' }).use(store).use(router).use(router).mount('#app')

// Vue.use(BaiduMap, {
//     // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
//     ak: 'eFleN6pcajV0o5AEmzNG8zMN0gDxsCT2'
// })
