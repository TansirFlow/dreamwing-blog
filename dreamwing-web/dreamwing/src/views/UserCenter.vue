<script setup>
import {
    Menu as IconMenu,
    Location,
    Odometer,
    Document,
    ChatLineRound,
    Folder,
    User,
    Setting,
    FullScreen,
    ChatLineSquare
} from '@element-plus/icons-vue'
import { ref, onMounted, watch, onActivated } from 'vue'
import { useRouter, useRoute, onBeforeRouteUpdate } from 'vue-router';
import { getUserDetailService }  from '@/api/userCenter.js'

import { } from '@vicons/tabler'
import { } from '@vicons/fluent'
import { } from '@vicons/ionicons4'
import { ExitOutline } from '@vicons/ionicons5'
import { } from '@vicons/antd'
import { } from '@vicons/material'
import { } from '@vicons/fa'
import { } from '@vicons/carbon'
import { Icon } from '@vicons/utils'

import { useTokenStore } from '@/stores/token.js'

const route = useRoute();
const router = useRouter()

const handleOpen = (key, keyPath) => {
    console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
    console.log(key, keyPath)
}


const blockName = ref('');//板块名称，显示在右边顶部
const handleBlockName = (index) => {
    let name_t = ""
    switch (index) {
        case "/uc/info":
            name_t = "个人中心";
            break;
        case "/uc/article":
            name_t = "我的文章";
            break;
        case "/uc/talk":
            name_t = "我的说说";
            break;
        case "/uc/attachment":
            name_t = "我的附件";
            break;
    }
    blockName.value = name_t;
}

const handleSelect = (index) => {
    handleBlockName(index)
}


onMounted(() => {
    handleBlockName(route.path)
});

watch(
    () => router.currentRoute.value,
    (newValue) => {
        handleBlockName(newValue.path)
    },
    { immediate: true }
)

const userDetailInfo=ref({})

const getUserDetail=async ()=>{
    let result=await getUserDetailService()
    console.log("result",result)
    userDetailInfo.value=result.data
    console.log("userDetailInfo",userDetailInfo)
}

async function userDetail() {// markdown-获取内容
    await getUserDetail()// axios获取内容
}
userDetail()

const tokenStore=useTokenStore()
const logout=()=>{
    tokenStore.removeToken()
    router.push('/login')
}

</script>
<template>
    <div class="common-layout">
        <el-container>
            <el-aside width="250px" :style="{ background: `white`, height: `100vh`, border: `0px solid red` }">
                <el-menu :style="{ height: `80vh` }" default-active="1" class="el-menu-vertical-demo" @open="handleOpen" 
                    @select="handleSelect" @close="handleClose" router>
                    <br>
                    <el-text class="mx-1" type="primary"
                        :style="{ fontSize: `36px`, display: 'flex', alignItem: `center`, justifyContent: 'center', height: `50px` }">DW
                        BLOG</el-text>
                    <br>
                    <el-menu-item index="/uc/info" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>个人中心</span>
                    </el-menu-item>
                    <el-menu-item index="/uc/article" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Document />
                        </el-icon>
                        <span>我的文章</span>
                    </el-menu-item>
                    <el-menu-item index="/uc/talk" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <ChatLineSquare />
                        </el-icon>
                        <span>我的说说</span>
                    </el-menu-item>
                    <el-menu-item index="/uc/attachment" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Folder />
                        </el-icon>
                        <span>我的附件</span>
                    </el-menu-item>
                </el-menu>
                <div
                    :style="{ position: `relative`, width: `90%`, height: `60px`, left: '10px', bottom: '-110px', border: `0px solid red` }">
                    <el-row :style="{ height: `100%` }">
                        <el-col :span="6" :style="{ border: `0px solid red`}">
                            <el-avatar :size="50" :src="circleUrl" />
                        </el-col>
                        <el-col :span="11" :style="{ border: `0px solid red`}">
                            <el-text class="mx-1" :style="{fontSize:`18px`}">{{ userDetailInfo.nickname }}</el-text>
                            <el-tag type="primary">{{ userDetailInfo.role }}</el-tag>
                        </el-col>
                        <el-col :span="7" :style="{ border: `0px solid red`,lineHeight:`0.8`,textAlign:`center`}">
                            <br>
                            <Icon size="30" @click="logout" :style="{cursor:`pointer`}">
                                <ExitOutline />
                            </Icon>
                        </el-col>
                    </el-row>
                </div>
            </el-aside>
            <el-container>
                <el-header :style="{background:`white`,display: 'flex', alignItem: `center`}">
                    <el-text class="mx-1" :style="{fontSize:`24px`,color:`black`}">{{ blockName }}</el-text>
                </el-header>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>

</template>
<style></style>