<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container">
            <a class="navbar-brand" href="#">Navbar w/ text</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarText">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <router-link class="nav-link" :to="{ name: 'home' }">Home
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link class="nav-link" :to="{ name: 'cyclelist' }">我的车辆</router-link>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">聊天室(未开放)</a>
                    </li>
                </ul>

                <ul class="navbar-nav" v-if="$store.state.user.is_login">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                            data-bs-toggle="dropdown" aria-expanded="false">
                            {{ $store.state.user.username }}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li>
                                <router-link class="dropdown-item" :to="{ name: 'cyclelist' }">我的车辆
                                </router-link>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#" @click="logout">退出</a></li>
                        </ul>
                    </li>

                </ul>

                <ul class="navbar-nav" v-else-if="!$store.state.user.pulling_info">
                    <li class="nav-item">
                        <router-link :to="{ name: 'user_account_login' }" class="nav-link" role="button">登陆
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :to="{ name: 'user_account_register' }" class="nav-link" role="button">注册
                        </router-link>
                    </li>
                </ul>

            </div>
        </div>
    </nav>
</template>

<script>
import { useRoute } from 'vue-router'
import { computed } from '@vue/reactivity'
import { useStore } from 'vuex'
export default {
    setup() {
        const store = useStore();
        const router = useRoute();
        let router_name = computed(() => router.name)
        const logout = () => {
            store.dispatch("logout");
        }
        return {
            router_name,
            logout,
        }
    }

}



</script>

<style scoped>
</style>