<template>
    <div class="a" v-if="!$store.state.user.pulling_info">
        <div class="b"></div>
        <div class="c">
            <div class="d">
                <form @submit.prevent="login">
                    <h1>Login</h1>
                    <input v-model="username" type="text" class="e" placeholder="USERNAME">
                    <input v-model="password" type="password" class="e" placeholder="PASSWORD">
                    <a href="#" class="f">忘记密码</a>
                    <div class="error-message">{{ error_message }}</div>
                    <button class="g" type="submit">登陆</button>

                </form>
            </div>
        </div>
    </div>
</template>

<script>

import { useStore } from 'vuex'
import { ref } from 'vue'
import router from '@/router'
export default {
    components: {


    },
    setup() {

        const store = useStore();
        let username = ref('');
        let password = ref('');
        let error_message = ref('');
        console.log("Before store.state.user.token: " + store.state.user.token)
        const jwt_token = localStorage.getItem("jwt_token");
        // console.log(jwt_token);
        if (jwt_token) {
            store.commit("updateToken", jwt_token);
            store.dispatch("getinfo", {
                success() {
                    router.push({ name: "home" });
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
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success() {
                    store.dispatch("getinfo", {
                        success() {
                            console.log("login sussess");
                            router.push({ name: "home" });//登陆成功跳转到home页面

                        },
                        error() {

                        }

                    })
                },
                error() {
                    error_message.value = '用户名或密码错误';
                },
            })
        }
        console.log("After store.state.user.token: " + store.state.user.token)
        return {
            username,
            password,
            error_message,
            login,

        }





    }
}
</script>


<style scoped>
.error-message {
    color: red;
}

body {
    /* 设置背景渐变 */
    background-image: linear-gradient(to left,
            #9c88ff, #3cadeb);
    display: flex;
    justify-content: center;
}

.a {
    position: relative;
    top: 100px;
    left: 22%;
    width: 1100px;
    height: 550px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, .8);
    display: flex;
}

.b {
    width: 800px;
    height: 550px;
    background-image: url("@/assets/LoginBackground.jpg");
    /* 让图片适应大小 */
    background-size: cover;
}

.c {
    width: 300px;
    height: 550px;
    background-color: white;
    display: flex;
    justify-content: center;
    align-items: center;
}

.d {
    width: 250px;
    height: 500px;
}

.d h1 {
    font: 900 30px '';
}

.e {
    width: 230px;
    margin: 20px 0;
    outline: none;
    border: 0;
    padding: 10px;
    border-bottom: 3px solid rgb(80, 80, 170);
    font: 900 16px '';
}

.f {
    float: right;
    margin: 10px 0;
}

.g {
    position: absolute;
    margin: 20px;
    bottom: 40px;
    display: block;
    width: 200px;
    height: 60px;
    font: 900 30px '';
    text-decoration: none;
    line-height: 50px;
    border-radius: 30px;
    background-image: linear-gradient(to left,
            #9c88ff, #3cadeb);
    text-align: center;
}
</style>