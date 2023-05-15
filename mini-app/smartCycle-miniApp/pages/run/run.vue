<template>
	<view>
		<!-- <BMap></BMap> -->
		<!-- 暂定：前端使用腾讯地图，后端使用百度地图试试 -->


		<u-search placeholder="请输入目的地" v-model="destination" @search="run"></u-search>
		<u-popup v-model="show"  mode="bottom" width="100%" height="30%" :closeable="true" close-icon-pos="top-right">
			<view style="text-align: center">
				<div style="height: 5em;top: 3em;"></div>
				
				<u-button type="primary" :ripple="true" size="medium" @click="falsh">停车入库</u-button>
			</view>
				
		</u-popup>
		<map style="width: 100vw; height: 100vh;" :latitude="latitude" :longitude="longitude" :polyline="polyline">

		</map>
	</view>
</template>

<script>
	// import BMap from '@/components/Bmap.vue' 
	// const bmap = require("@/common/bmap-wx.js"); // 引入bmap-wx文件
	import * as QQMapWX from '@/common/qqmap-wx-jssdk.js'
	import store from '@/store/index.js'
	import {
		ref,
		reactive
	} from 'vue'
	export default {
		components: {
			// BMap
		},
		// onLoad() {
		// 	this.getLine()
		// },
		setup() {
			// let BMap = new bmap.BMapWX({
			// 	ak: '4hdyOwtVnaLgdl5Kzjzu1QC2iq3bybPm'
			// })
			// const tMap = new QQMapWX({
			// key: 'CKIBZ-A4OOD-N7P4S-P64VA-ZWUFE-L5FJD'
			// })
			let latitude = ref(41.856094)
			let longitude = ref(123.786289)
			let polyline_copy = []
			// let points = []
			let polyline = ref([]) //响应式对象，如果是响应式字符串则使用ref
			let points = []
			let destination = ref('')
			let show = ref(false)
			const dest_points = {
				'四教': {
					'lat': '41.857686',
					'lng': '123.790119'
				}
				// {},
				// {},
				// {},
				// {},
				// {},

			}
			
			const falsh = () => {
				uni.switchTab({
				   url:'/pages/home/home',
				   
				});
			}

			//成功且路线没有偏移
			const draw_polyline = (slat,slng,lat, lng) => {
				uni.request({
					url: 'https://apis.map.qq.com/ws/direction/v1/walking/?from='+slat+','+slng+'&to=' +
						lat + ',' + lng + '&output=json&callback=cb&key=CKIBZ-A4OOD-N7P4S-P64VA-ZWUFE-L5FJD',
					method: 'GET',
					success: (resp) => { //成功
						
						console.log(resp)
						points = []
						// console.log(resp.data.result.routes[0].steps[0])
						let steps = resp.data.result.routes[0].polyline
						for (var i = 2; i < steps.length; i++) {
							steps[i] = steps[i - 2] + steps[i] / 1000000
						}
						// console.log(steps)
						let point = {
							latitude: 0,
							longitude: 0
						}

						for (var i = 0; i < steps.length - 1; i += 2) {
							points.push({
								latitude: steps[i],
								longitude: steps[i + 1]
							})
							// console.log(steps[i] + ' ' + steps[i + 1])
						}
						// console.log(points)
						polyline_copy.length = 0//清空数组，之后再画线
						polyline_copy.push({ //响应式赋值，直接 polyline.value = xxx不行
							points: points,
							width: 3
						})
						console.log(polyline_copy)
						polyline.value = []
						polyline.value = polyline_copy 
						// console.log(polyline.value)
					}

					// console.log(steps.length)
					// 
					// console.log("cycles:" + cycles.value[0].cname)

				});


			}


			const run = () => {
				// '四教':{'lat':41.857686,'lng':123.790119}
				let dest = dest_points[destination.value]
				// console.log(destination.value)
				// console.log(dest.lat)
				uni.request({
					url: 'http://localhost:3000/user/cycle/run/search',
					method: 'POST',
					data: {
						"latitude": dest.lat,
						"longitude": dest.lng,
					},
					header: {
						'Content-type': 'application/x-www-form-urlencoded', //java-springboot服务器必须加上这行，不然@RequestParam收不到值，500错误
						Authorization: "Bearer " + store.state.user.token,
					},
					success: (resp) => {
						console.log(resp.data.lng)
						draw_polyline(41.856131,123.786253,resp.data.lat, resp.data.lng);
						getChange(resp);
					},
					fail: (resp) => {
						console.log(resp.data)
					}


				});
			}

			const getChange = (resp) => {
				uni.startLocationUpdate({
				  success: (res) => {
					uni.onLocationChange(function(res){
						console.log(getDistance(res.latitude,res.longitude,resp.data.lat, resp.data.lng))
						if(getDistance(res.latitude,res.longitude,resp.data.lat, resp.data.lng) < 15){
							console.log("您到目的地了")
							show.value = true
							draw_polyline(res.latitude,res.longitude,resp.data.lat, resp.data.lng);
							uni.offLocationChange()
						}
						draw_polyline(res.latitude,res.longitude,resp.data.lat, resp.data.lng);
						
					})
				  }
				});
			}
			
			const getDistance = (lat1, lng1, lat2, lng2) => {
			    lat1 = lat1 || 0;
			    lng1 = lng1 || 0;
			    lat2 = lat2 || 0;
			    lng2 = lng2 || 0;
			
			    var rad1 = lat1 * Math.PI / 180.0;
			    var rad2 = lat2 * Math.PI / 180.0;
			    var a = rad1 - rad2;
			    var b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;
			    var r = 6378137;
			    var distance = r * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(rad1) * Math.cos(rad2) * Math.pow(Math.sin(b / 2), 2)));
			
			    return distance;
			}

			return {
				latitude,
				longitude,
				polyline,
				destination,
				run,
				show,
				falsh,
				// getLine
			}



		}
	}
</script>

<style lang="scss">
	.uni_search_bar {
		// z-index: 11;
	}
</style>
