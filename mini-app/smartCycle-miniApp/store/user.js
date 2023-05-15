export default {
    state: {
        id: "",
        username: "",
        photo: "",
        token: "",
        is_login: false,
        pulling_info: true,//正在从云端拉取信息
    },
    getters: {
    },
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.is_login = user.is_login;
        },
        updateToken(state, token) {
            state.token = token;
			
        },
        logout(state) {
            state.id = "";
            state.username = "";
            state.photo = "";
            state.token = "";
            state.is_login = false;
        },
        updatePullingInfo(state, pulling_info) {
            state.pulling_info = pulling_info;
        }
    },
    actions: {
        login(context, data) {
			// console.log(data.username)
			uni.request({
				header:{ 
					'Content-type':'application/x-www-form-urlencoded' //注意这里对应的是用Java搭建的服务器
				},
				
				url: 'http://localhost:3000/user/account/token/',
				method: 'POST',
				// dataType: 'json', //返回值类型
				data:{
					//传给后端的参数
					username: data.username,
					password: data.password,
				},
				success: (resp) => { //成功
				console.log(resp.data.error_massage)
					if(resp.data.error_massage === "success"){ //状态
						console.log(1)
						uni.setStorageSync("jwt_token", resp.data.token)
						context.commit("updateToken", resp.data.token);
						// console.log(state.token)
						data.success(resp);
					} 
					else {
						data.error(resp);
					}
				},
				fail : (resp) => { //失败
					data.error(resp);
				},
			});
			
        },
        getinfo(context, data) {			
			uni.request({
				// header:{ 
				// 	'Content-type':'application/x-www-form-urlencoded' //注意这里对应的是用Java搭建的服务器
				// },
				url: "http://localhost:3000/user/account/info/",
				method: 'GET',
				// dataType: 'json', //返回值类型
				header: {
				    Authorization: "Bearer " + context.state.token,
				},
				success: (resp) => { //成功
					if(resp.error_massage === "success"){ //状态
					console.log(resp)
						context.commit("updateUser", {
						    ...resp,//将resp中的
						    is_login: true,
						});
						data.success(resp);
					} 
					else {
						data.error(resp);
					}
				},
				fail : (resp) => { //失败
					data.error(resp);
				},
			});
        },
		
        logout(context) {
			try {
				uni.removeStorageSync("jwt_token");
				context.commit("logout");
			} catch (e) {
				// error
			}

        }
    },
    modules: {
    }
}