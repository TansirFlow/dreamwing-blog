<script setup>
import { ref, onMounted, onActivated } from 'vue'
import { CirclePlus, Search, Link, House, Clock, User } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router';
import { getUserDetailService } from '@/api/layout'
import { getCategoryListService, getArticleListService } from '@/api/public'
const router = useRouter();
import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore()






// -------------------------------------------------------搜索相关------------------------------------------------
// 设置搜索框可见性
const searchBoxVisible = ref(false)
// 搜索框输入内容
const searchInput = ref('')
// 搜索框搜索结果
const searchResult = [
    {
        date: '2016-05-03',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    }
]

// ----------------------------------------页面keep-alive处理-----------------------------------------------------
// 文章列表视图滚动组件的滚动位置
const scrollPosition = ref(1)

// 文章列表视图滚动组件
const articleListViewScrollbar = ref(null)

// 当文章列表视图发生滚动时该函数触发并记录位置，以便回退到当前页面时保留当前位置
const handleArticleListViewScroll = () => {
    scrollPosition.value = articleListViewScrollbar.value.$refs.wrapRef.scrollTop;
}


// 组件成功挂载后执行
onMounted(async () => {
    await getArticleList()
    await getCategoryList()
    await checkLoginStatus()
})

// 适用于路由懒加载或开启了keep-alive后再次进入页面时触发
onActivated(() => {
    getCategoryList()
    checkLoginStatus()
    console.log("pos" + scrollPosition.value)
    var gotoScroll = scrollPosition.value
    setTimeout(function () {
        //兼容 PC and Mobile 写两个
        articleListViewScrollbar.value.$refs.wrapRef.scrollTop = gotoScroll;
    }, 10);
})




// --------------------------------------------------账户相关处理-----------------------------------------
// 存储用户详细信息
const userDetailInfo=ref({})
const getUserDetail=async ()=>{
    let result=await getUserDetailService();
    userDetailInfo.value=result.data
}

// 用户登录的状态
const loginStatus = ref(false)

// 检测当前的用户登录状态
const checkLoginStatus = () => {
    if (tokenStore.token) {
        loginStatus.value = true;
        getUserDetail()
    } else {
        loginStatus.value = false;
    }
}

// 用户注销登录
const logout = () => {
    tokenStore.removeToken()
    router.push('/')
}


// 转到登陆页面
const gotoLogin = () => {
    router.push('/login')
}


// 转到管理后台
const gotoConsole = () => {
    router.push('/console')
}

// 转到用户个人中心
const gotoUserCenter = () => {
    router.push('/uc')
}

// 转到发布文章页面
const gotoAddArticlePage = () =>{
    router.push('/uc/editarticle')
}


// -------------------------------------------菜单相关处理----------------------------------------------
// 顶部菜单栏当前活跃项的Index
const topMenuActiveIndex = ref('1')

// 处理顶部菜单选择事件
const handleTopMenuSelect = (key, keyPath) => {
    console.log(key, keyPath)
}

// 当前选中的分类index
const activeCategoryIndex = ref(1)

// 处理主页左侧文章分类点击事件
const handleCategorySelect = (index) => {
    ElMessage.success(`点击了${index}`)
}



// ---------------------------------------------页面信息处理---------------------------------------------
// 文章列表
const articleList = ref([]);

// 获取文章列表
const getArticleList = async () => {
    let result = await getArticleListService();
    console.log(result.data.items)
    articleList.value = result.data.items;
}

// 转到文章详情页
const lookArticleDetail = (id) => {
    router.push(`/article/${id}`)
}

// 文章分类列表
const categoryList = ref(null)

// 获取文章文类列表
const getCategoryList = async () => {
    let result = await getCategoryListService();
    categoryList.value = result.data;
}



</script>

<template>
    <div style="position: fixed;z-index:999;border:0px solid red;width:100%;height:100px">
        <el-menu :default-active="topMenuActiveIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false"
            @select="handleTopMenuSelect" style="height: 70px;position:flxed" background-color="rgba(255,255,255,0.5)"
            :style="{ backdropFilter: `blur(10px)` }">
            <el-menu-item index="0">
                <!-- <img style="width: 100px" src="../assets/element-plus-logo.svg" alt="Element logo" /> -->
                 <el-text type="primary" :style="{fontSize:`21px`,}">DreamWing Blog</el-text>
            </el-menu-item>
            <!-- <el-menu-item index="4" style="font-size: 21px;">
                <el-icon :size="20">
                    <House />
                </el-icon>
                首页
            </el-menu-item>
            <el-menu-item index="5" style="font-size: 21px;">
                <el-icon :size="20">
                    <Link />
                </el-icon>
                友链
            </el-menu-item> -->
            <div class="flex-grow" />
            <el-sub-menu index="1">
                <template #title><el-avatar :src="userDetailInfo.avatar"/></template>
                <div v-if="loginStatus">
                    <el-menu-item index="1-1" @click="gotoConsole" v-if="userDetailInfo.role===1">管理后台</el-menu-item>
                    <el-menu-item index="1-2" @click="gotoUserCenter">个人中心</el-menu-item>
                    <el-menu-item index="1-3" @click="logout">退出登录</el-menu-item>
                </div>
                <div v-else>
                    <el-button @click="gotoLogin">登录</el-button>
                </div>

            </el-sub-menu>
            <el-menu-item index="2">
                <el-button size="large" :icon="Search" circle @click="searchBoxVisible = true" />
            </el-menu-item>
            <el-menu-item index="3">
                <el-button size="large" :icon="CirclePlus" type="primary" @click="gotoAddArticlePage" round>发布文章</el-button>
            </el-menu-item>
        </el-menu>
    </div>

    <el-scrollbar height="100vh" @scroll="handleArticleListViewScroll" ref="articleListViewScrollbar">
        <el-row :style="{ border: `0px solid red`, top: `100px` }" justify="center">
            <el-col :style="{ border: `0px solid red` }" :span="18">
                <el-row :style="{ border: `0px solid red` }" :gutter="0" justify="space-between">
                    <el-col :style="{ border: `0px solid red` }" :span="4"><!--左侧栏-->
                        <el-affix :offset="100">
                            <el-card style="width: 100%;">
                                <el-menu :style="{ borderRight: `none` }" :default-active="activeCategoryIndex"
                                    class="el-menu-demo" @select="handleCategorySelect">
                                    <!-- <div v-for="item in categoryList" :key="item.id">
                                        <el-card style="width: 100%;">
                                            <div>
                                                <el-text style="font-size: 16px;cursor:pointer;color:black;" truncated>
                                                    <b>{{ item.categoryName }}</b>
                                                </el-text>
                                            </div>
                                        </el-card>
                                        <br>
                                    </div> -->

                                    <el-menu-item v-for="item in categoryList" :key="item.id" :index="item.id">
                                        <el-icon><icon-menu /></el-icon>
                                        <span :style="{ fontSize: `18px` }">{{ item.categoryName }}</span>
                                    </el-menu-item>
                                </el-menu>

                            </el-card>
                        </el-affix>
                    </el-col>
                    <el-col :style="{ border: `0px solid red`, background: `none` }" :span="14"><!--文章条目-->
                        <div v-for="item in articleList" :key="item.id">
                            <el-card style="width: 100%;">
                                <div class="card-header">
                                    <el-text class="mx-1" size="large"
                                        style="font-size: 21px;line-height: 2;cursor:pointer;color:black;" truncated
                                        @click="lookArticleDetail(item.id)">
                                        <b>{{ item.articleTitle }}</b>
                                    </el-text>
                                </div>
                                <el-text line-clamp="2" :style="{ whiteSpace: `normal`, cursor: `pointer` }" size="large"
                                    truncated @click="lookArticleDetail(item.id)">
                                    {{ item.articleAbstract ? item.articleAbstract : item.articleContent }}
                                </el-text>
                                <template #footer>
                                    <el-row justify="space-between">
                                        <el-col :span="3" style="color:#b1b3b8">
                                            <el-text class="mx-1" type="info" size="large" truncated
                                                style="cursor: pointer;" @click="ElMessage.success(item.author)">
                                                <el-icon>
                                                    <User />
                                                </el-icon>
                                                {{ item.author }}
                                            </el-text>
                                        </el-col>
                                        <el-col :span="15">
                                            <el-space size="large">
                                                <el-check-tag :checked="true" type="primary" v-for="tag in item.tagList"
                                                    @click="ElMessage.success(tag.name)">{{ tag.name }}</el-check-tag>
                                            </el-space>
                                        </el-col>
                                        <el-col :span="6" style="color:#b1b3b8">
                                            <el-text class="mx-1" type="info" style="float: right" size="large" truncated>
                                                <el-icon>
                                                    <Clock />
                                                </el-icon>
                                                {{ item.createTime }}
                                            </el-text>
                                        </el-col>
                                    </el-row>
                                </template>
                            </el-card>
                            <br>
                        </div>
                    </el-col>
                    <el-col :style="{ border: `0px solid red` }" :span="5"><!--右侧栏-->
                        <el-card style="width: 100%;">
                            <el-row justify="center">
                                <img style="width: 100px" src="../assets/element-plus-logo.svg" alt="Element logo" />
                            </el-row>
                        </el-card>
                    </el-col>
                </el-row>
                <el-footer class="footer">
                    <!-- 版权信息 -->
                    © 2024 DreamWing Blog. All rights reserved.
                </el-footer>
            </el-col>
        </el-row>
    </el-scrollbar>


    <el-dialog v-model="searchBoxVisible" title="搜索" width="500">
        <el-row :gutter="10" justify="space-between">
            <el-col :span="20">
                <el-input v-model="searchInput" style="width: 100%" placeholder="输入搜索内容" />
            </el-col>
            <el-col :span="4">
                <el-button type="primary" style="width: 100%" plain>搜索</el-button>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <el-table :data="searchResult" style="width: 100%">
                    <el-table-column prop="date" label="序号" width="120px" />
                    <el-table-column prop="name" label="标题" width="100px" />
                    <el-table-column prop="address" label="匹配" />
                </el-table>
            </el-col>
        </el-row>
    </el-dialog>
    <el-backtop :right="100" :bottom="100" />
</template>

<style>
.flex-grow {
    flex-grow: 1;
}

.footer {
    text-align: center;
    background-color: #f5f5f5;
    padding: 20px;
}
</style>
