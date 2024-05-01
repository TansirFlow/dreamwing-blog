<script setup>
import { getArticleByIdService } from '@/api/article';
import { useRoute } from 'vue-router';
import { ref, onMounted, onActivated } from 'vue'
import { CirclePlus, Search, Link, House } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Editor, Viewer } from '@bytemd/vue-next'
import 'bytemd/dist/index.css'
import breaks from '@bytemd/plugin-breaks'
import frontmatter from '@bytemd/plugin-frontmatter'
import gemoji from '@bytemd/plugin-gemoji'
import gfm from '@bytemd/plugin-gfm'
import footnotes from '@bytemd/plugin-footnotes'
import highlight from '@bytemd/plugin-highlight'
import math from '@bytemd/plugin-math-ssr'
import medium from '@bytemd/plugin-medium-zoom'
import mermaid from '@bytemd/plugin-mermaid'
import 'juejin-markdown-themes/dist/healer-readable.min.css'
import zhHans from 'bytemd/locales/zh_Hans.json'
import 'highlight.js/styles/vs.css'
/*healer-readable, */
const plugins = ref([
    breaks(),
    frontmatter(),
    gemoji(),
    gfm(),
    highlight(),
    math(),
    medium(),
    mermaid(),
    footnotes(),
])

/*
# SpringBoot屎山
```java
public Result update(@RequestBody User user) {
    Set<ConstraintViolation<User>> updateViolations = validator.validate(user, User.UpdateGroup.class);
    if (!updateViolations.isEmpty()) {
        return Result.error(GlobalConstants.INFORMATION_NOT_MATCH);
    }
    userService.update(user);
    return Result.success();
}
```
*/

const activeIndex = ref('1')//导航栏索引
const handleSelect = (key, keyPath) => {//导航栏选中
    console.log(key, keyPath)
}

const dialogVisible = ref(false)//搜索框可视

const searchInput = ref('')//搜索内容

const tableData = [
    {
        date: '2016-05-03',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    }
]

const pictures = ref([
    { id: 1, path: '../assets/1.webp' }, { id: 2, path: '../assets/2.jpg' }, { id: 3, path: '../assets/3.webp' }, { id: 4, path: '../assets/4.webp' }
])

const exportImgSrc = (src) => {//根据地址动态获取图片
    if (src) {
        return new URL(`${src}`, import.meta.url).href;
    } else {
        return new URL(`../assets/icon/default.png`, import.meta.url).href;
    }
}


const article = ref({})//文章

const currentRoute = useRoute();//获取路由对象
const getArticleId = () => {//从路径参数获取文章id
    return currentRoute.params.id;
}

const getArticleById = async () => {//请求文章
    article.value = {}
    let result = await getArticleByIdService(getArticleId());
    article.value = result.data;
}

onMounted(() => {
    getArticleById()
})


onActivated(() => {//进入页面获取文章(适用于缓存)
    if (article.value.articleId !== getArticleId()) {
        getArticleById()
    }
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

    <el-scrollbar ref="scrollbar" height="100vh">
        <el-row :style="{ height: `40vh` }">
            <img style="width: 100%;height: 100%;filter: brightness(0.75);object-fit: cover;"
                :src="exportImgSrc('../assets/default_background.webp')" />
            <el-text class="mx-1"
                :style="{ textAlign: `center`, color: `white`, fontSize: `36px`, position: `absolute`, top: `100px`, bottom: `20px`, left: `0`, right: `0`, border: `0px solid red` }">{{
            article.articleTitle }}</el-text>
        </el-row>
        <el-row :style="{ border: `0px solid red`, top: `50px` }" justify="center">
            <el-col :style="{ border: `0px solid red` }" :span="18">
                <el-row :style="{ border: `0px solid red` }" :gutter="0" justify="space-between">
                    <el-col :style="{ border: `0px solid red`, background: `none` }" :span="17"><!--文章内容-->
                        <el-card style="width: 96%;">
                            <div ref="viewRef">
                                <!-- <Editor class="editos" :value="value" :plugins="plugins" :locale="zhHans"
                                    @change="handleChange" :uploadImages="uploadImage" /> -->
                                <Viewer :value="article.articleContent" :plugins="plugins" :locale="zhHans" />
                            </div>
                        </el-card>

                    </el-col>
                    <el-col :style="{ border: `1px solid red` }" :span="7"><!--右侧栏，文章大纲-->
                        <el-card style="width: 100%;">
                            <el-text class="mx-1" size="large" type="primary">这一亩三分地放文章大纲得了</el-text><br><br><br>
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

<style lang="css" scoped>
.flex-grow {
    flex-grow: 1;
}

.footer {
    text-align: center;
    background-color: #f5f5f5;
    padding: 20px;
}

</style>
