<template>

    <div class="container" style="margin-top:20px;">
        <div class="row" style="margin-top:20px;">
            <div class="col-12">

                <div class="card">
                    <div class="card-header">
                        我的车辆
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                            data-bs-target="#add-cycle-btn">添加车辆</button>
                        <!-- Modal-add -->
                        <div class="modal fade" id="add-cycle-btn" data-bs-backdrop="static" data-bs-keyboard="false"
                            tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="staticBackdropLabel">添加车辆</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="add-cycle-cname" class="form-label">车辆名称</label>
                                            <input v-model="cycleAdd.cname" type="text" class="form-control"
                                                id="add-cycle-cname" placeholder="请输入车辆名称">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-cycle-type" class="form-label">车辆类型</label>
                                            <input v-model="cycleAdd.ctype" type="text" class="form-control"
                                                id="add-cycle-ctype" placeholder="请输入车辆类型(自行车、电动车)">
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <div class="error-message">{{ cycleAdd.error_message }}</div>
                                        <button type="button" class="btn btn-primary" @click="add_cycle">添加</button>
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">取消</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Modal add end -->


                        <div class="card-body">
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <th>名称</th>
                                        <th>类型</th>
                                        <th>骑行状态</th>
                                        <th>其他</th>
                                        <th>操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="cycle in cycles" :key="cycle.id">
                                        <td>{{ cycle.cname }}</td>
                                        <td>{{ cycle.ctype }}</td>
                                        <td>{{ cycle.cstate }}</td>
                                        <td></td>
                                        <td>
                                            <button type="button" class="btn btn-secondary" data-bs-toggle="modal"
                                                :data-bs-target="'#update-cycle-btn-' + cycle.id"
                                                style="margin-right:10px;">修改</button>

                                            <button type="button" class="btn btn-danger" @click="remove_cycle(cycle)"
                                                style="margin-right:10px;">删除</button>

                                            <button type="button" class="btn btn-primary"
                                                @click="cycle_run(cycle)">骑行</button>
                                        </td>

                                        <!-- Modal-update -->
                                        <div class="modal fade" :id="'update-cycle-btn-' + cycle.id"
                                            data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                                            aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="staticBackdropLabel">修改车辆</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="update-cycle-cname"
                                                                class="form-label">车辆名称</label>
                                                            <input v-model="cycleAdd.cname" type="text"
                                                                class="form-control" id="update-cycle-cname"
                                                                placeholder="请修改车辆名称">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-cycle-type"
                                                                class="form-label">车辆类型</label>
                                                            <input v-model="cycleAdd.ctype" type="text"
                                                                class="form-control" id="update-cycle-ctype"
                                                                placeholder="请修改车辆类型(自行车、电动车)">
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ cycleAdd.error_message }}</div>
                                                        <button type="button" class="btn btn-primary"
                                                            @click="update_cycle(cycle)">修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">取消</button>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Modal update end -->

                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                </div>


            </div>
        </div>
    </div>


</template>


<script>
import $ from 'jquery'
import { useStore } from 'vuex'
import { ref, reactive } from 'vue'
import { Modal } from 'bootstrap/dist/js/bootstrap'
import router from '@/router/index'
export default {
    components: {

    },
    setup() {
        let cycles = ref([]);
        const store = useStore();
        const cycleAdd = reactive({
            cname: "",
            ctype: "",
            error_message: "",
        })
        let latitude = ref('');
        let longitude = ref('');
        const refresh_cycles = () => {
            $.ajax({
                url: "http://localhost:3000/user/cycle/getList/",
                type: "GET",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    cycles.value = resp;
                }
            });
        }

        const add_cycle = () => {
            cycleAdd.error_message = "";
            $.ajax({
                url: "http://localhost:3000/user/cycle/add/",
                type: "POST",
                data: {
                    cname: cycleAdd.cname,
                    ctype: cycleAdd.ctype,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(cycleAdd.cname);
                    console.log(resp);
                    if (resp.error_message === "success") {
                        console.log("ok");
                        cycleAdd.cname = "";
                        cycleAdd.ctype = "";
                        Modal.getInstance("#add-cycle-btn").hide();
                        refresh_cycles();
                    } else cycleAdd.error_message = resp.error_message;
                },
                error(resp) {
                    console.log(resp);
                }
            });
        }

        const remove_cycle = (cycle) => {
            $.ajax({
                url: "http://localhost:3000/user/cycle/remove/",
                type: "post",
                data: {
                    cycle_id: cycle.id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        refresh_cycles();
                    }
                }

            });
        }

        const update_cycle = (cycle) => {
            $.ajax({
                url: "http://localhost:3000/user/cycle/update/",
                type: "post",
                data: {
                    cycle_id: cycle.id,
                    cname: cycleAdd.cname,
                    ctype: cycleAdd.ctype,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                    if (resp.error_message === "success") {
                        Modal.getInstance('#update-cycle-btn-' + cycle.id).hide();
                        refresh_cycles();
                    }
                    else cycle.error_message = resp.error_message;

                },
                error(resp) {
                    console.log(resp);
                }
            });
        }
        const displayLocation = (position) => {
            longitude.value = position.coords.longitude,
                latitude.value = position.coords.latitude;
            console.log(position.coords.longitude);
        }
        const displayError = () => {

        }
        navigator.geolocation.getCurrentPosition(displayLocation, displayError);
        const cycle_run = async (cycle) => {

            await $.ajax({
                url: "http://localhost:3000/user/cycle/route/",//192.168.0.101
                type: "post",
                async: false,//设置同步方法，这样就可以在返回上一页再骑行的时候可以等上一个ajax执行完，再执行一个全新的ajax请求
                data: {
                    cycle_id: cycle.id,
                    longitude: longitude.value,
                    latitude: latitude.value,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log("route_id:" + resp.routeId);
                    if (resp.error_message === "success") {
                        router.push({
                            name: "cycle_run"
                        });
                        localStorage.setItem("routeId", JSON.stringify(resp.routeId));
                    }
                },
                error() {
                    console.log(cycle.id);
                    console.log(longitude.value);
                }
            });

        }


        refresh_cycles();
        return {
            cycles,
            cycleAdd,
            add_cycle,
            remove_cycle,
            update_cycle,
            cycle_run,
        }

    }
}

</script>

<style scoped>
.error-message {
    color: red;
}
</style>