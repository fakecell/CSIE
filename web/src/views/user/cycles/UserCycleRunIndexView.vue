<!-- <template>
    <input v-model="center.lng">
    <input v-model="center.lat">
    <input v-model="zoom">
    <baidu-map class="map" :scroll-wheel-zoom="true" :center="center" :zoom="zoom" @moving="syncCenterAndZoom"
        @moveend="syncCenterAndZoom" @zoomend="syncCenterAndZoom">
    </baidu-map>    
    <div id="container"></div>

    111
</template> -->

<template>
    <div>
        <div class="searchPanel">
            <div class="searchInput">
                <tr>
                    <td>
                        <input type="text" name="searchInput" id="searchInput" placeholder="请输入您的目的地名称"
                            v-model="endPlace">
                    </td>
                    <!-- <td>
                        <button type="button" style="font-size: 1em;" class="btn btn-info"
                            @click="searchEndPlace">搜索</button>
                    </td> -->
                </tr>
            </div>
            <div class="card">
                <div class="card-body" v-for="result in searchResult" :key="result.uid" style="padding: 5px;">
                    <tr class="row">
                        <td class="col-8">
                            <div>{{ result.title }}</div>
                            <div style="font-size:0.5em ;font-style:oblique">地址:{{ result.address }}</div>
                        </td>
                        <td class="col-3" style="margin-right:0.1em;">
                            <button type="button" style="font-size: 0.8em;" class="btn btn-info"
                                @click="runToEnd(result.point)">前往</button>
                        </td>
                    </tr>

                    <!-- <td class="col-1"></td> -->

                </div>
            </div>

            <!-- <label>关键词：<input v-model="keyword"></label> -->
            <!-- <label>地区：<input v-model="location"></label> -->
            <!-- <button type="button" class="btn btn-info">搜索</button> -->
            <!-- <div> -->





            <!-- {{ result.title }} -->
            <!-- <tr>{{ result.address }}</tr> -->
            <!-- </div> -->


        </div>


        <baidu-map class="map" style="overflow:auto" center="山东省临沂市郯城县" :scroll-wheel-zoom="true">

            <div v-if="indexParam.status === 0">
                <bm-local-search :keyword="endPlace" :auto-viewport="true" :panel="false"
                    @searchcomplete="searchcomplete"></bm-local-search>
            </div>

            <div v-if="indexParam.status === 1">
                <bm-walking :start="run.start" :end="run.end" :auto-viewport="true" :policy="policy" :panel="true"
                    location=" 山东省临沂市郯城县" :selectFirstResult="true"></bm-walking>
            </div>

            <!-- { lng: 118.380272, lat: 34.616019 }体育馆 -->
            <!-- { lng: 123.792825, lat: 41.861801 } -->
        </baidu-map>
    </div>
</template>


<!-- 搜索前往自定义点，交给后端，给定区域找最近的自定义点，返回该点的坐标给前端 -->


<!-- 运动过程中不断移动的点：使用vue-baidu-map-v3中的point，通过每5s上报一次地理位置的时候，就更新点的坐标 -->

<!-- 路径的显示和关闭可以通过v-if，再加上js中定义一个变量动态实现 -->

<!-- 经过测试手机浏览器定位是很准的，web端定位是不准确的 -->
<!-- 我们可以电脑连接手机热点，然后请求url改为192.168.99.65，改完一定要每一个被修改的vue文件界面都保存一下，再将vue sever项目重新启动，即可手机端访问 -->


<!-- ！！！！！ -->
<!-- ！！！ 还差：到达endPlace 结束骑行，“骑行“按钮变成：上车 ， 同时后端更新一些状态 ！！！-->
<!-- 现在先进行小程序端的编写 -->

<script>
// import BaiduMap from 'vue-baidu-map/components/map/Map.vue'
// import { onMounted } from 'vue'

import { BaiduMap } from 'vue-baidu-map-3x'
// import { useStore } from 'vuex'
import { reactive, ref, nextTick } from 'vue';
import { useStore } from 'vuex'

import $ from 'jquery'
// import router from '@/router/index'
export default {

    components: {
        // BaiduMap
        BaiduMap
    },
    setup() {
        // const end = ref({
        //     lng: 0,
        //     lat: 0
        // });
        // const start = ref({
        //     lng: 0,
        //     lat: 0
        // });
        const store = useStore();
        // let status = 0;//0：代表最开始状态（未骑行，检索目标点中。。。） || 1:代表检索完成，正在选择目标点|| 2：代表骑行中 || 3：代表骑行结束
        var param = localStorage.getItem("routeId");
        const routeId = param;
        const keyword = '';
        let searchResult = ref([]);
        let endPlace = ref('');
        let center = ref({
            lng: 118.36,
            lat: 34.629,
        });
        let indexParam = reactive({
            status: 0,
        });
        let run = reactive({
            start: {
                lng: 118.366382,
                lat: 34.629637
            },
            end: {
                lng: 118.380272,
                lat: 34.616019
            },
        });
        const displayStart = (position) => {
            run.start.lng = position.coords.longitude,
                run.start.lat = position.coords.latitude;
            console.log("--------------------");
            console.log("run.start:" + run.start.lng + "," + run.start.lat);
        }

        const displayLocation = (position) => {
            run.start.lng = position.coords.longitude,
                run.start.lat = position.coords.latitude;
            console.log("--------------------");
            console.log("run.start:" + run.start.lng + "," + run.start.lat);
        }
        const displayError = () => {

        }
        // console.log(1);
        navigator.geolocation.getCurrentPosition(displayStart, displayError);

        // console.log(2);

        //js定时器，每5s上报一次地理位置
        setInterval(async function () {
            await navigator.geolocation.getCurrentPosition(displayLocation, displayError);
            await $.ajax({
                url: "http://localhost:3000/user/cycle/run/" + routeId,
                type: "post",
                async: true,//设置同步方法，这样就可以在返回上一页再骑行的时候可以等上一个ajax执行完，再执行一个全新的ajax请求
                data: {
                    latitude: run.localtion.lat,//34.629637,//run.localtion.lat,
                    longitude: run.localtion.lng,//118.366382,//run.localtion.lng,
                    speed: 3,//run.localtion.speed
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            });
        }, 5555000);

        const localPoint = reactive({ lng: 0, lat: 0, });//{ lng: 118.366382, lat: 34.629637 }
        let zoom = ref(15);

        console.log("routeID:" + routeId);
        const syncCenterAndZoom = (e) => {
            const { lng, lat } = e.target.getCenter();
            zoom.value = e.target.getZoom();
            nextTick(() => {
                center.value.lng = lng;
                center.value.lat = lat;
                console.log("center.value.lng=" + center.value.lng);
            })
        };

        const searchcomplete = (LocalResult) => {//LocalResult.Ir[0].title : Ir是一个数组[]，里面的每一个值都是一个Object{}
            console.log(LocalResult.Ir);
            searchResult.value = LocalResult.Ir;
            console.log(searchResult.value[1]);
            // document.getElementById("searchPanel").innerHTML = LocalResult.Ir;
        }

        const runToEnd = (point) => {

            $.ajax({
                url: "http://localhost:3000/user/cycle/run/search",
                type: "post",
                async: true,//设置同步方法，这样就可以在返回上一页再骑行的时候可以等上一个ajax执行完，再执行一个全新的ajax请求
                data: {
                    latitude: point.lat,
                    longitude: point.lng,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    run.end.lat = resp.lat;
                    run.end.lng = resp.lng;
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            });
            // run.end.lat = point.lat;
            // run.end.lng = point.lng;
            // this.$set(indexParam, "status", 1);
            indexParam.status = 1;

            // this.$forceUpdate();
            console.log("status:" + indexParam.status);
            console.log(run.end.lat);
        }
        console.log("status:" + indexParam.status);
        // const searchEndPlace = () => {

        // }

        return {
            center,
            status,
            searchResult,
            keyword,
            run,
            localPoint,
            zoom,
            endPlace,
            indexParam,
            searchcomplete,
            syncCenterAndZoom,
            runToEnd,
            // searchEndPlace,
        }



        //-----------------------------------------
        // let map = ref('');


        // const initMap = () => {
        //     map.value = new BMapGL.Map('map');
        //     map.value.centercenterAndZoom(new BMapGL.Point(104.072242, 30.663711), 14);
        // }


        // initMap();

        // return {
        //     map,
        //     initMap,
        // }
        // onMounted(() => {

        // })
        // var map = new BMap.Map('container')
        // var point = new BMap.Point(116.404, 39.915)
        // map.centerAndZoom(point, 15)
        // map.enableScrollWheelZoom(true)

    }




}


</script>


<style scoped>
.map {
    width: 100%;
    height: 1500px;
    z-index: -1;
}

.searchPanel {
    /* 20% 100px */
    width: 20%;
    height: 50%;
    background-color: white;
    position: absolute;
    top: 100px;
    left: 20px;
    z-index: 2;
}
</style>