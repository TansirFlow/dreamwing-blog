<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
const toAddArticlePage=()=>{
    router.push('/console/editarticle')
}
</script>
<template>
文章管理
<el-button @click="toAddArticlePage">新增文章</el-button>
</template>
<style>
</style>