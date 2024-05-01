<script setup>
import { getArticleByIdService } from '@/api/article';
import { useRoute } from 'vue-router';
import { ref, onMounted, onActivated, nextTick, onBeforeUnmount } from 'vue'
import { CirclePlus, Search, Link, House } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'


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


const editor = ref(null)// markdown-对象
const titleList = ref([])// markdown-文章标题列表
async function articleData() {// markdown-获取内容
    await getArticleById()// axios获取内容
}
async function getTitle() {// markdown-生成标题
    await nextTick()
    const anchors = editor.value.querySelectorAll(// 使用js选择器，获取对应的h标签，组合成列表
        'h1,h2,h3,h4,h5,h6'
    )
    const titles = Array.from(anchors).filter((title) => !!title.innerText.trim());// 删除标题头尾的空格
    if (!titles.length) {// 当文章h标签为空时，直接返回
        titleList.value = [];
        return;
    }
    const hTags = Array.from(new Set(titles.map((title) => title.tagName))).sort();// 从h标签属性中，提取相关信息
    titleList.value = titles.map((el) => ({
        title: el.innerText, // 标题内容
        lineIndex: el.getAttribute('data-v-md-line'), // 标签line id
        indent: hTags.indexOf(el.tagName), // 标签层级
        height: el.offsetTop, // 标签距离顶部距离
    }));
}

const heightTitle = ref(0)// markdown-当前高亮的标题index
const rollTo = (anchor, index) => {// markdown-标题跳转
    const { lineIndex } = anchor;// 获取要跳转的标签的lineIndex
    const heading = editor.value.querySelector(// 查找lineIndex对应的元素对象
        `.v-md-editor-preview [data-v-md-line="${lineIndex}"]`
    );
    if (heading) {// 页面跳转
        console.log(666)
        heading.scrollIntoView({ behavior: "smooth", block: "start" })
    }
    heightTitle.value = index// 修改当前高亮的标题
}


onMounted(() => {
    articleData()
    setTimeout(() => {
        getTitle()
    }, 1000)
})

onActivated(() => {
    articleData()
    setTimeout(() => {
        getTitle()
    }, 1000)
})
const scrollbar = ref(null)
const outline = ref()
const rightCol = ref()
const outlineTop = ref(-100)
const outlineStyle = ref({
    width: `100%`,
    transform: 'translate(0,0px)',
    'animation-duration': '0s'
})
const handleScroll = () => {
    let scrollTop = scrollbar.value.$refs.wrapRef.scrollTop;
    const absList = [] // 各个h标签与当前距离绝对值
    titleList.value.forEach((item) => {
        absList.push(Math.abs(item.height - scrollTop))
    })
    // 屏幕滚动距离与标题高度最近的index高亮
    heightTitle.value = absList.indexOf(Math.min.apply(null, absList))

    const windowHeight = window.innerHeight;
    const windowWidth = window.innerWidth;
    const outlintWidth=parseInt((windowWidth*18.0/24)*7.0/24);
    const outlineRight=parseInt(windowWidth*3.0/24)

    if (scrollTop < 0.4 * windowHeight) {
        outlineStyle.value.position=``
        outlineStyle.value.transform = `translate(0,0px)`
        outlineStyle.value.right=`${outlineRight}px`
        outlineStyle.value.width=`${outlintWidth}px`
        outlineStyle.value.top=`0px`
        outlineStyle.value.bottom='0px'
    } else {
        outlineStyle.value.position=`fixed`
        outlineStyle.value.right=`${outlineRight}px`
        outlineStyle.value.width=`${outlintWidth}px`
        outlineStyle.value.top=`100px`
        outlineStyle.value.bottom='0px'
    }
}




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

    <el-scrollbar ref="scrollbar" height="100vh" @scroll="handleScroll">
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
                            <div ref="editor"><!--文章内容-->
                                <v-md-preview :text="article.articleContent" ref="editor"></v-md-preview>
                            </div>
                        </el-card>
                    </el-col>

                    <el-col :style="{ border: `0px solid red` }" :span="7" ref="rightCol"><!--右侧栏，文章大纲-->

                        <!-- 内容 -->
                        <el-card ref="outline" :style="outlineStyle">
                            <el-text class="mx-1" size="large" type="primary">这一亩三分地放文章大纲得了</el-text><br><br><br>
                            <el-row justify="center">
                                <div><!--文章大纲-->
                                    <div v-for="(anchor, index) in titleList"
                                        :style="{ padding: `10px 0 10px ${anchor.indent * 10}px` }"
                                        @click="rollTo(anchor, index)"
                                        :class="index === heightTitle ? 'title-active' : 'title-not-active'">
                                        <a style="cursor: pointer">{{ anchor.title }}</a>
                                    </div>
                                </div>
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

.title-active {
    background-color: rgba(0, 0, 0, 0.1);
    color: black;
    border-left: 2px solid rgba(0, 0, 0, 0.5);
    font-size: 14px;
    font-family: '微软雅黑';
}

.title-not-active {
    color: black;
    font-size: 14px;
}
</style>
