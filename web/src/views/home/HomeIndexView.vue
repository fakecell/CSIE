<template>

    <div class="container">
        <div class="row" style="margin-top:20px;">
            <div class="col-9" style="background-color: blue;margin-left:px;">
                <div class="hot-communicate">
                    <div class="hot-communicate-card">1sdfsdafsdfdasggggggggggggsdsdgf</div>
                    <div class="hot-communicate-card">2</div>
                </div>
                <div class="hot-activity-left">
                    3
                </div>
            </div>
            <div class="col-3" style="background-color: red;">

            </div>
        </div>
    </div>

</template>

<script>
// import ContentField from '@/components/ContentField.vue'

export default {
    components: {

    },

}
</script>


<style scoped>
.hot-communicate {
    display: flex;
    -webkit-box-pack: justify;
    justify-content: space-between;
}

.hot-communicate-card {
    box-shadow: 0 2px 8px rgba(var(--dsw-black-rgb), 0.08), 0 1px 2px rgba(var(--dsw-black-rgb), 0.1);
    background: rgba(var(--dsw-layer-1-rgb), 1);
    border-radius: 8px;
    padding: 16px;
    overflow: hidden;
    cursor: pointer;
    width: 362px;
    height: 152px;

}

.hot-activity-left {
    width: 100%;
    height: 128px;
    object-fit: cover;
}
</style>