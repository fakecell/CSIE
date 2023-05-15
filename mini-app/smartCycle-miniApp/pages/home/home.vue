<template>
	<u-card :border="true">
		<view v-for="cycle in cycles" :key="cycle.id" slot="body">
			<view class="u-body-item u-flex u-border-bottom u-col-between u-p-t-0">
				<view class="padding_card u-body-item-title u-line-2">车辆类型：{{ cycle.ctype }}</view>
				<view class="padding_card u-body-item-title u-line-2">车辆名称：{{ cycle.cname }}</view>
				<view class="padding_card u-body-item-title u-line-2">上次使用时间：{{ cycle.updatetime }}</view>
			</view>
			<view style="display: flex;justify-content: space-between;">
				<view v-if="is_lock === 1">
					<u-button type="primary" size="medium" @click="cycle_unlock(cycle)">unlock</u-button>
				</view>
				<view v-else>
					<u-button type="primary" size="medium" @click="cycle_lock(cycle)">lock</u-button>
				</view>
				<view>
					<u-button type="primary" size="medium" @click="running(cycle)">Running</u-button>
				</view>


			</view>



		</view>







	</u-card>


</template>

<script>
	import store from '@/store/index.js'
	import {
		onTabItemTap
	} from "@dcloudio/uni-app"
	// import $ from 'jquery'
	import {
		ref,
		reactive
	} from 'vue'

	export default {
		components: {

		},
		setup() {
			const title = ref('我的车辆')
			const is_lock = ref(1) //代表处于上锁状态
			let cycles = ref([]);
			const cycleAdd = reactive({
				cname: "",
				ctype: "",
				error_message: "",
			})
			let latitude = ref('');

			let longitude = ref('');
			const cycle_unlock = (cycle) => {
				
				console.log("unlock")
				is_lock.value = 0 //让其处于未上锁状态
				
				uni.request({
					header: {
						"api-key": '5Hkq=bRIvmeoGzLN0Dp3zyqezsk=',
					},
					url: 'http://api.heclouds.com/cmds?device_id=1062127269',
					method: 'POST',
					data: {
						"Cmd": 2,
					},
					success: (resp) => { //成功
						// console.log(resp)
						console.log(1)
						console.log(resp)
						// console.log("cycles:" + cycles.value[0].cname)
				
					}
				
				
				});
				
				
			}
			const cycle_lock = (cycle) => {
				is_lock.value = 1 //让其处于上锁状态
			}
			

			const refresh_cycles = () => {
				console.log(1)
				// console.log(store.state.user.token)
				uni.request({
					url: 'http://localhost:3000/user/cycle/getList/',
					method: 'GET',
					header: {
						Authorization: "Bearer " + store.state.user.token,
					},
					success: (resp) => {
						// console.log(resp.data)
						cycles.value = resp.data
					},
					fail: (resp) => {
						console.log(resp)
					}
				})
			}

			// eFleN6pcajV0o5AEmzNG8zMN0gDxsCT2
			// https://api.map.baidu.com/directionlite/v1/walking?origin=40.01116,116.339303&destination=39.936404,116.452562&ak=您的AK  //GET请求



			//点一次tabBar就刷新一次页面
			onTabItemTap(() => {
				// console.log(e)
				//暂时只刷新 CycleList
				refresh_cycles()
			})

			const running = (cycle) => {
				let local_location = {
					latitude: 41.856094,
					longitude: 123.786289
				}
				uni.switchTab({
					url: '/pages/run/run',

				})


			}



			refresh_cycles();
			return {
				cycles,
				running,
				title,
				is_lock,
				cycle_unlock,
				cycle_lock,
			}
		}
	}
</script>

<style scoped lang="scss">
	.more-style {
		text-align: center;
		font-size: 3px;
	}

	.wrap {
		padding: 24rpx;
	}

	.u-row {
		margin: 40rpx 0;
	}

	.demo-layout {
		height: 80rpx;
		border-radius: 8rpx;
	}

	.bg-purple {
		background: #d3dce6;
	}

	.bg-purple-light {
		background: #e5e9f2;
	}

	.bg-purple-dark {
		background: #99a9bf;
	}


	.u-card-wrap {
		background-color: $u-bg-color;
		padding: 1px;
	}

	.u-body-item {
		font-size: 32rpx;
		color: #333;
		padding: 20rpx 10rpx;
	}

	.u-body-item image {
		width: 120rpx;
		flex: 0 0 120rpx;
		height: 120rpx;
		border-radius: 8rpx;
		margin-left: 12rpx;
	}

	.padding_card {
		margin-top: 8px;
	}
</style>
