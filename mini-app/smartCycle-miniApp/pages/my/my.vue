<template>
	<view>
		<u-input v-model="username"  />
		<u-input v-model="password"  />
		<u-button type="info" @click="login">登录</u-button>
		<!-- <u-button type="info" @click="login">test</u-button> -->
		
	</view>
</template>

<!-- --------！！！！！！！！！！！！！！！！！！！！！-------- -->
<!-- 注意：后期需要使用uniapp的uni-simple-router用来设置路由：先判断是否有store.state.user.token 没有就跳转到login -->
<!-- 也可以专门设置一个登录界面，my界面分为：未登录，已登录 -->

<script>
	// import { useStore } from 'vuex'
	import store from '@/store/index.js'
	import { ref } from 'vue'
	export default {
		components: {
			
		},
		setup() {
				
				let username = ref('sunzhengqing');
				let password = ref('123');
				let error_message = ref('');
				let jwt_token = ref('');
				try {
					 jwt_token = uni.getStorageSync("jwt_token");
				} catch (e) {
					// error
				}
			   
				console.log("jwt_token: "+jwt_token);
				if (jwt_token) {
					store.commit("updateToken", jwt_token);
					store.dispatch("getinfo", {
						success() {
							// router.push({ name: "home" });
							console.log("pushToHome")
							store.commit("updatePullingInfo", false);
							
						},
						error() {
							store.commit("updatePullingInfo", false);
						}
					});
		
				} else {
					store.commit("updatePullingInfo", false);
				}
		
		
		const login = () => {
			// console.log(username.value)
			store.dispatch("login", {
				username: username.value,
				password: password.value,
				success() {
					store.dispatch("getinfo", {
						success() {
							console.log(store.state.user.username)
							console.log("login sussess");
							// router.push({ name: "home" });//登陆成功跳转到home页面
							console.log("pushToHome")
							
						}, 
						error() {
							console.log(store.state.user.username)
						}

					})
				},
				error() {
					error_message.value = '用户名或密码错误';
				},
			})
		}
		// console.log(store.state.user.token)
		login()
		return {
			username,
			password,
			login,
		}
		}
	}
</script>

<style lang="scss">

</style>
