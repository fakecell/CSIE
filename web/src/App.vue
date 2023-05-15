<template>
  <NavBar></NavBar>
  <router-view></router-view>
</template>



<script>
import NavBar from '@/components/NavBar.vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import "bootstrap/dist/js/bootstrap"
// import $ from 'jquery'
// import { useStore } from 'vuex'
export default {
  components: {
    NavBar,
  },


  setup() {
    // const store = useStore();
    // $.ajax({
    //   url: "http://localhost:6688/route",
    //   type: "post",
    //   data: {
    //     username: "sunzhengqing",
    //     password: 123,
    //   },
    //   // headers: {
    //   //   Authorization: "Bearer " + store.state.user.token,
    //   // },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.log(resp);
    //   }
    // })
  }


}

</script>


<style scoped>

</style>
