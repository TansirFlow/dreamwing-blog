<script setup>
import {
    Menu as IconMenu,
    Document,
    Folder,
    User,
    ChatLineSquare
} from '@element-plus/icons-vue'
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router';
import { getUserDetailService }  from '@/api/userCenter.js'
import { useTokenStore } from '@/stores/token.js'
const route = useRoute();
const router = useRouter()
const tokenStore=useTokenStore()



// -----------------------------------------------导入媒体文件--------------------------------------------------
import { } from '@vicons/tabler'
import { } from '@vicons/fluent'
import { } from '@vicons/ionicons4'
import { ExitOutline } from '@vicons/ionicons5'
import { } from '@vicons/antd'
import { } from '@vicons/material'
import { } from '@vicons/fa'
import { } from '@vicons/carbon'
import { Icon } from '@vicons/utils'



// ------------------------------------------------------板块与路由---------------------------------------------------------

// 处理左侧菜单打开事件
const handleLeftMenuOpen = (key, keyPath) => {
    console.log(key, keyPath)
}
// 处理左侧菜单关闭事件
const handleLeftMenuClose = (key, keyPath) => {
    console.log(key, keyPath)
}
// 处理左侧菜单选中事件
const handleLeftMenuSelect = (index) => {
    getBlockNameByPath(index)
}

//板块名称，显示在右边板块顶部
const blockName = ref('');

// 板块数据
const blockList=ref([
    {
        index:"0",
        path:"/uc/info",
        name:"个人中心"
    },
    {
        index:"1",
        path:"/uc/article",
        name:"我的文章"
    },
    {
        index:"2",
        path:"/uc/talk",
        name:"我的说说"
    },
    {
        index:"3",
        path:"/uc/attachment",
        name:"我的附件"
    },
    {
        index:"4",
        path:"/uc/editarticle",
        name:"编辑文章"
    }
])

// 通过索引信息获得板块名称
const getBlockNameByPath = (path) => {
    for(let i=0;i<blockList.value.length;++i){
        if(path===blockList.value[i].path || path.indexOf(blockList.value[i].path)!==-1){
            blockName.value=blockList.value[i].name
            break
        }
    }
}


// --------------------------------------------------账户相关-------------------------------------------
// 当前登录用户的详细信息
const userDetailInfo=ref({})

// 获取当前登录用户详细信息
const getUserDetail=async ()=>{
    let result=await getUserDetailService()
    console.log("result",result)
    userDetailInfo.value=result.data
    console.log("userDetailInfo",userDetailInfo)
}

getUserDetail()


// 用户注销登录
const logout=()=>{
    tokenStore.removeToken()
    router.push('/login')
}


// -----------------------------------------------------------钩子函数---------------------------------------------------
onMounted(() => {
    getBlockNameByPath(route.path)
});

// watch(
//     () => router.currentRoute.value,
//     (newValue) => {
//         getBlockNameByIndex(newValue.path)
//     },
//     { immediate: true }
// )

</script>
<template>
    <div class="common-layout">
        <el-container>
            <el-aside width="250px" :style="{ background: `white`, height: `100vh`, border: `0px solid red` }">
                <el-menu :style="{ height: `80vh` }" default-active="1" class="el-menu-vertical-demo" @open="handleLeftMenuOpen" 
                    @select="handleLeftMenuSelect" @close="handleLeftMenuClose" router>
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