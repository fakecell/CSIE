import { createRouter, createWebHistory } from 'vue-router'
import NotFound from '../views/error/NotFound'
import UserAccountLoginView from '../views/user/account/UserAccountLoginView'
import UserAccountRegisterView from '../views/user/account/UserAccountRegisterView'
import HomeIndexView from '../views/home/HomeIndexView'
import UserCycleIndexView from '../views/user/cycles/UserCycleIndexView'
// import UserCycleTestView from '../views/user/cycles/UserCycleTestView'
import UserCycleRunIndexView from '../views/user/cycles/UserCycleRunIndexView'
import store from '@/store/index'
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeIndexView,
    meta: {
      requestAuto: true,
    }
  },
  {
    path: '/user/cyclelist/',
    name: 'cyclelist',
    component: UserCycleIndexView,
    meta: {
      requestAuto: true,//需要授权
    }
  },
  {
    path: '/404/',
    name: '404',
    component: NotFound,
  },
  {
    path: '/user/account/login/',
    name: 'user_account_login',
    component: UserAccountLoginView,
    meta: {
      requestAuto: false,
    }
  },
  {
    path: '/user/account/register/',
    name: 'user_account_register',
    component: UserAccountRegisterView,
    meta: {
      requestAuto: false,
    }
  },
  {
    path: '/user/account/cycle/run/',
    name: 'cycle_run',
    component: UserCycleRunIndexView,
    meta: {
      requestAuto: true,
    }
  },
  {
    path: '/:catchAll(.*)',
    redirect: '/404/'
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requestAuto && !store.state.user.is_login) {
    next({ name: "user_account_login" });
  } else {
    next();
  }
})


export default router
