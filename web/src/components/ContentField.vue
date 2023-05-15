<template>
    <div class="container content-field">
        <div class="card">
            <div class="card-body">
                <!-- 要填充的内容都会渲染在slot里面 -->
                <slot></slot>
            </div>
        </div>
    </div>

</template>



<script>


</script>


<style scoped>
div.content-field {
    margin-top: 20px;
}
</style>