<script setup>
import { ref, onMounted, onActivated } from 'vue'
import { CirclePlus, Search, Link, House, Clock, User } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router';
import { getArticleListService } from '@/api/layout'
const activeIndex = ref('1')
const handleSelect = (key, keyPath) => {
    console.log(key, keyPath)
}

const dialogVisible = ref(false)

const searchInput = ref('')

const gridData = [
    {
        date: '2016-05-02',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District',
    },
    {
        date: '2016-05-04',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District',
    },
    {
        date: '2016-05-01',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District',
    },
    {
        date: '2016-05-03',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District',
    },
]

const tableData = [
    {
        date: '2016-05-03',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-02',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-04',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-01',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
]


const contents = ref([]);//文章列表

const getArticleList = async () => {
    let result = await getArticleListService();
    contents.value = result.data;
}

const router = useRouter();
onMounted(() => {
    getArticleList()
})

const pictures = ref([
    {
        id: 1,
        path: '../assets/1.webp'
    },
    {
        id: 2,
        path: '../assets/2.jpg'
    },
    {
        id: 3,
        path: '../assets/3.webp'
    },
    {
        id: 4,
        path: '../assets/4.webp'
    },
])

const exportImgSrc = (src) => {
    if (src) {
        return new URL(`${src}`, import.meta.url).href;
    } else {
        return new URL(`../assets/icon/default.png`, import.meta.url).href;
    }
}

const lookArticleDetail = (id) => {
    router.push(`/article/${id}`)
}

const keepScroll = ref(1)
const scrollbar = ref(null)
const handleScroll = () => {
    keepScroll.value = scrollbar.value.$refs.wrapRef.scrollTop;
}
onActivated(() => {
    console.log("pos" + keepScroll.value)
    var gotoScroll = keepScroll.value
    setTimeout(function () {
        //兼容 PC and Mobile 写两个
        scrollbar.value.$refs.wrapRef.scrollTop = gotoScroll;
    }, 10);
})


</script>

<template>
    <div style="position: fixed;z-index:999;border:0px solid red;width:100%;height:100px">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false"
            @select="handleSelect" style="height: 70px;position:flxed" background-color="rgba(255,255,255,0.5)"
            :style="{ backdropFilter: `blur(10px)` }">
            <el-menu-item index="0">
                <img style="width: 100px" src="../assets/element-plus-logo.svg" alt="Element logo" />
            </el-menu-item>
            <el-menu-item index="4" style="font-size: 21px;">
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
            </el-menu-item>
            <div class="flex-grow" />
            <el-sub-menu index="1">
                <template #title><el-avatar /></template>
                <el-menu-item index="1-1">管理后台</el-menu-item>
                <el-menu-item index="1-2">个人中心</el-menu-item>
                <el-menu-item index="1-3">退出登录</el-menu-item>
            </el-sub-menu>
            <el-menu-item index="2">
                <el-button size="large" :icon="Search" circle @click="dialogVisible = true" />
            </el-menu-item>
            <el-menu-item index="3">
                <el-button size="large" :icon="CirclePlus" type="primary" round>发布文章</el-button>
            </el-menu-item>
        </el-menu>
    </div>

    <el-scrollbar height="100vh" @scroll="handleScroll" ref="scrollbar">
        <div class="block text-center">
            <el-carousel motion-blur height="40vh">
                <el-carousel-item v-for="(picture, key) in pictures" :key="key">
                    <img style="width: 100%;height: 100%;" :src="exportImgSrc(picture.path)" />
                </el-carousel-item>
            </el-carousel>
        </div>
        <el-row :style="{ border: `0px solid red`, top: `100px` }" justify="center">
            <el-col :style="{ border: `0px solid red` }" :span="18">
                <el-row :style="{ border: `0px solid red` }" :gutter="0" justify="space-between">
                    <el-col :style="{ border: `0px solid red`, background: `none` }" :span="17"><!--文章条目-->
                        <div v-for="item in contents" :key="item.id">
                            <el-card style="width: 96%;">
                                <div class="card-header">
                                    <el-text class="mx-1" size="large"
                                        style="font-size: 21px;line-height: 2;cursor:pointer;color:black;" truncated
                                        @click="lookArticleDetail(item.id)">
                                        <b>{{ item.articleTitle }}</b>
                                    </el-text>
                                </div>
                                <el-text line-clamp="2" :style="{ whiteSpace: `normal`, cursor: `pointer` }"
                                    size="large" truncated @click="lookArticleDetail(item.id)">
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
                                                <el-check-tag :checked="true" type="primary" v-for="tags in item.tags"
                                                    @click="ElMessage.success(tags)">{{ tags }}</el-check-tag>
                                            </el-space>
                                        </el-col>
                                        <el-col :span="6" style="color:#b1b3b8">
                                            <el-text class="mx-1" type="info" style="float: right" size="large"
                                                truncated>
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
                    <el-col :style="{ border: `0px solid red` }" :span="7"><!--右侧栏-->
                        <el-card style="width: 100%;">
                            <el-row justify="center">
                                <img style="width: 100px" src="../assets/element-plus-logo.svg" alt="Element logo" />
                            </el-row>
                        </el-card>
                    </el-col>
                </el-row>
                <el-footer class="footer">
                    <!-- 版权信息 -->
                    © 2024 DreamWing Blog. All rights reserved.<br>
                    <el-link href="https://su.sctes.com/register?code=8cyve3yx9q2vc" target="_blank" size="large">
                        本站由&nbsp;<el-link type="primary">无畏云科技</el-link>&nbsp;提供免费云加速服务
                    </el-link>
                </el-footer>
            </el-col>
        </el-row>
    </el-scrollbar>


    <el-dialog v-model="dialogVisible" title="搜索" width="500">
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
                <el-table :data="tableData" style="width: 100%">
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
