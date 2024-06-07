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
const route = useRoute();
const router = useRouter()
const tokenStore=useTokenStore()


// 搜索功能input
const input=ref() 


// ---------------------------------------------------导入媒体--------------------------------------------------
import { } from '@vicons/tabler'
import { } from '@vicons/fluent'
import { } from '@vicons/ionicons4'
import { ExitOutline } from '@vicons/ionicons5'
import { } from '@vicons/antd'
import { } from '@vicons/material'
import { } from '@vicons/fa'
import { } from '@vicons/carbon'
import { Icon } from '@vicons/utils'
import {getUserDetailService} from '@/api/console'
import { useTokenStore } from '@/stores/token.js'


// --------------------------------------------------板块与路由------------------------------------------------
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

// 当前板块名称，显示在右边顶部
const blockName = ref('');

// 板块数据列表
const blockList=ref([
    {
        index:"0",
        path:"/console/panel",
        name:"仪表盘"
    },
    {
        index:"1",
        path:"/console/article",
        name:"文章管理"
    },
    {
        index:"2",
        path:"/console/talk",
        name:"说说管理"
    },
    {
        index:"3",
        path:"/console/comment",
        name:"评论管理"
    },
    {
        index:"4",
        path:"/console/attachment",
        name:"附件管理"
    },
    {
        index:"5",
        path:"/console/user",
        name:"用户管理"
    },
    {
        index:"6",
        path:"/console/setting",
        name:"系统设置"
    },
    {
        index:"7",
        path:"/console/all",
        name:"系统概览"
    },
    {
        index:"8",
        path:"/console/editArticle",
        name:"编辑文章"
    },
    
])


// 通过索引信息获得板块名称
const getBlockNameByPath = (path) => {
    for(let i=0;i<blockList.value.length;++i){
        if(path===blockList.value[i].path){
            blockName.value=blockList.value[i].name
            break
        }
    }
}



// ------------------------------------------------------------钩子函数-----------------------------------------------------------
onMounted(() => {
    getBlockNameByPath(route.path)
    getUserDetail()
});

onActivated(()=>{
    getUserDetail()
})

// watch(
//     () => router.currentRoute.value,
//     (newValue) => {
//         getBlockNameByPath(newValue.path)
//     },
//     { immediate: true }
// )


// ------------------------------------------------------账户相关----------------------------------------------
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

const logout=()=>{
    tokenStore.removeToken()
    router.push('/login')
}




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
                    <div :style="{ display: 'flex', alignItem: `center`, justifyContent: 'center', height: `60px` }">
                        <el-input v-model="input" style="width: 90%;height:40px;" placeholder="搜索" />
                    </div>
                    <el-menu-item index="/console/panel" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Odometer />
                        </el-icon>
                        <span>仪表盘</span>
                    </el-menu-item>
                    <el-menu-item index="/console/article" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Document />
                        </el-icon>
                        <span>文章管理</span>
                    </el-menu-item>
                    <el-menu-item index="/console/talk" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <ChatLineSquare />
                        </el-icon>
                        <span>说说管理</span>
                    </el-menu-item>
                    <el-menu-item index="/console/comment" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <ChatLineRound />
                        </el-icon>
                        <span>评论管理</span>
                    </el-menu-item>
                    <el-menu-item index="/console/attachment" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Folder />
                        </el-icon>
                        <span>附件管理</span>
                    </el-menu-item>
                    <el-menu-item index="/console/user" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>用户管理</span>
                    </el-menu-item>
                    <el-menu-item index="/console/setting" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Setting />
                        </el-icon>
                        <span>系统设置</span>
                    </el-menu-item>
                    <el-menu-item index="/console/all" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <FullScreen />
                        </el-icon>
                        <span>系统概览</span>
                    </el-menu-item>
                </el-menu>
                <!-- <div
                    :style="{ position: `relative`, width: `100%`, height: `60px`, left: '0', bottom: '-130px', border: `0px solid red` }">
                    <el-row :style="{ height: `100%` }">
                        <el-col :span="6" :style="{ border: `0px solid red` }">
                            <el-avatar :size="50" :src="circleUrl" />
                        </el-col>
                        <el-col :span="11" :style="{ border: `0px solid red` }">
                            <el-text class="mx-1" :style="{ fontSize: `18px` }">Tansor</el-text>
                            <el-tag type="primary">超级管理员</el-tag>
                        </el-col>
                        <el-col :span="7" :style="{ border: `0px solid red`, lineHeight: `0.8`, textAlign: `center` }">
                            <br>
                            <Icon size="30">
                                <ExitOutline />
                            </Icon>
                        </el-col>
                    </el-row>
                </div> -->
            </el-aside>
            <el-container>
                <el-header :style="{ background: `white`, display: 'flex', alignItem: `center` }">
                    <el-row justify="space-between" :style="{ width:`100%`, display: 'flex'}">
                        <el-col :span="3" :style="{display: 'flex',alignItem: `center`,justifyContent: 'left',border:`1px solid red`}">
                            <el-text class="mx-1" :style="{ fontSize: `24px`, color: `black` }">{{ blockName
                                }}</el-text>
                        </el-col>
                        <el-col :span="3" :style="{display: 'flex',alignItem: `center`,justifyContent: 'right',border:`1px solid red`}">
                            <el-dropdown :hide-on-click="false" :style="{display: 'flex',alignItem: `center`,justifyContent: 'right',border:`1px solid red`,width:`140px`,height:`50px`}">
                                <span class="el-dropdown-link">
                                    <el-row :style="{display:`flex`,width:`100%`}">
                                        <el-col :span="12">
                                            <el-avatar :size="50" />
                                        </el-col>
                                        <el-col :span="12">
                                            <el-text class="mx-1" :style="{display: 'flex',
                                            alignItem: `center`,justifyContent: 'right',height:`50px`,border:`1px solid red`}">{{ userDetailInfo.username }}</el-text>
                                        </el-col>
                                    </el-row>
                                    
                                </span>
                                <template #dropdown>
                                    <el-dropdown-menu>
                                        <el-dropdown-item>修改信息</el-dropdown-item>
                                        <el-dropdown-item>修改密码</el-dropdown-item>
                                        <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                                    </el-dropdown-menu>
                                </template>
                            </el-dropdown>
                        </el-col>
                    </el-row>
                </el-header>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>

</template>
<style>
.example-showcase .el-dropdown+.el-dropdown {
    margin-left: 15px;
}

.example-showcase .el-dropdown-link {
    cursor: pointer;
    color: var(--el-color-primary);
    display: flex;
    align-items: center;
}
</style>