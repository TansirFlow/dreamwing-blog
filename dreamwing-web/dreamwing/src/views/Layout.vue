<script setup>
import { ref } from 'vue'
import { CirclePlus, Search, Link, House } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
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


const contents = [
    {
        id: '1',
        title: '测试文章标题1',
        info: '测试文章信息1',
        viewNum: 30,
        talkNum: 9,
        createTime: '2023-1-1',
        author: '作者1',
        tags: [
            'tag1', 'tag2', 'tag3'
        ]
    },
    {
        id: '2',
        title: '测试文章标题2',
        info: '测试文章信息2',
        viewNum: 12,
        talkNum: 20,
        createTime: '2023-1-1',
        author: '作者2',
        tags: [
            'tag1', 'tag2', 'tag3'
        ]
    },
    {
        id: '3',
        title: '测试文章标题3',
        info: '测试文章信息3',
        viewNum: 1,
        talkNum: 5,
        createTime: '2023-1-1',
        author: '作者3',
        tags: [
            'tag1', 'tag2', 'tag3'
        ]
    },
]
</script>

<template>
    <div style="position: fixed;z-index:9999;border:0px solid red;width:100%;height:100px">
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
                <template #title><el-avatar :size="large" :src="circleUrl" /></template>
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

    <el-row :style="{ border: `0px solid red`, top: `100px` }" justify="center">
        <el-col :style="{ border: `0px solid red` }" :span="18">
            <el-row :gutter="0" justify="space-between">
                <el-col :style="{ border: `0px solid red`, background: `none` }" :span="17"><!--文章条目-->
                    <div v-for="item in contents" :key="item.id">
                        <el-card style="width: 96%;">
                            <template #header>
                                <div class="card-header">
                                    <span>{{ item.title }}</span>
                                </div>
                            </template>
                            <span>{{ item.info }}</span>
                            <template #footer>
                                <el-row justify="space-between">
                                    <el-col :span="3">
                                        <span>{{ item.author }}</span>
                                    </el-col>
                                    <el-col :span="17">
                                        <el-space>
                                            <el-tag type="primary" v-for="tags in item.tags">{{ tags }}</el-tag>
                                        </el-space>
                                    </el-col>
                                    <el-col :span="4">
                                        <span>{{ item.createTime }}</span>
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
        </el-col>
    </el-row>
    <el-footer class="footer">
        <!-- 版权信息 -->
        © 2024 Your Company. All rights reserved.
    </el-footer>

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
