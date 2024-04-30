<script setup>
</script>

<template>
    <!-- <keep-alive>
        <router-view v-if="$route.meta.keepAlive"></router-view>
    </keep-alive> -->
    <router-view v-slot="{ Component, route }">
        <keep-alive v-if="route.meta.keepAlive">
            <component :is="Component" />
        </keep-alive>
        <component :is="Component" v-if="!route.meta.keepAlive" />
    </router-view>
</template>

<style scoped></style>
