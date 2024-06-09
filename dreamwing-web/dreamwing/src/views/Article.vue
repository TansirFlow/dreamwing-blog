<script setup>
import { getArticleDetailService, getCommentByArticleIdService } from '@/api/public';
import { useRoute } from 'vue-router';
import { ref, onMounted, onActivated, nextTick, onBeforeUnmount, onUpdated, defineComponent, createApp } from 'vue'
import { CirclePlus, Search, Link, House } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { addCommentSelfService } from '@/api/article'
//获取路由对象
const currentRoute = useRoute();


//----------------------------------------------------菜单相关------------------------------------------------------
// 顶部菜单索引
const topMenuActiveIndex = ref('1')

// 处理顶部菜单选择事件
const handleTopMenuSelect = (key, keyPath) => {
    console.log(key, keyPath)
}



//----------------------------------------------------搜索相关------------------------------------------------------
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


//----------------------------------------------------媒体文件（图片）相关------------------------------------------------------
// 根据src导入图片显示
const exportImgSrc = (src) => {//根据地址动态获取图片
    if (src) {
        return new URL(`${src}`, import.meta.url).href;
    } else {
        return new URL(`../assets/icon/default.png`, import.meta.url).href;
    }
}

// 用户头像（示例）
const circleUrl = ref('../assets/tstx.jpg');


// 图标库
import { } from '@vicons/fluent'
import { } from '@vicons/ionicons4'
import { LogoWechat } from '@vicons/ionicons5'
import { } from '@vicons/antd'
import { } from '@vicons/material'
import { Qq, Github } from '@vicons/fa'
import { } from '@vicons/carbon'
import { Icon } from '@vicons/utils'



// ---------------------------------------------------页面数据相关----------------------------------------------------------------
//从路径参数获取文章id
const getArticleId = () => {
    return currentRoute.params.id;
}
// 文章详细内容
const article = ref({})

//请求文章详细内容
const getArticleById = async () => {
    article.value = {}
    let result = await getArticleDetailService(getArticleId());
    article.value = result.data;
}

// 调用函数获取文章详细内容
async function getArticleData() {
    await getArticleById()
}

// 评论列表
const commentList = ref([
    {
        id: 1,//评论ID
        userId: 26,//发言人ID
        commentUserName: "Tansor",//发言人用户名
        commentUserAvatar: "http://jd.tansor.top:8999/dreamwing-blog/2d7f3bd1-113b-49ee-9315-6c266206e39a.JPG",
        content: "你这写的什么垃圾啊？随便找条狗来都比你写的好啊",//评论内容
        parentCommentId: 1,//父评论ID
        type: 1,//评论类型
        parentId: 1,//所属文章ID
        isDelete: 0,
        isReview: 1,
        createTime: "2024-06-09 16:46",
        updateTime: "2024-06-09",
    },
])

// 根评论（没有父评论）
const rootCommentList = ref([])

const getCommentByArticleId = async (id) => {
    commentList.value = []
    let result = await getCommentByArticleIdService(id);
    commentList.value = result.data;
    rootCommentList.value = []
    for (let i = 0; i < commentList.value.length; ++i) {
        if (commentList.value[i].parentCommentId === null || commentList.value[i].parentCommentId === undefined) {
            rootCommentList.value.push(commentList.value[i])
        }
    }
}
getCommentByArticleId(getArticleId())


// 新的根评论内容
const newCommentSelf = ref('')
// 控制发布评论的窗口（新楼）
const addCommentSelfBoxVisable = ref(false)
// 打开发布评论窗口
const openAddCommentSelfBox = () => {
    addCommentSelfBoxVisable.value = true
}
// 发布新评论
const addCommentSelf = async () => {
    let newCommentSelfData = {
        content: newCommentSelf.value,
        type: 1,
        parentId: getArticleId(),
    }
    let result = await addCommentSelfService(newCommentSelfData);
    ElMessage.success("发布成功")
    addCommentSelfBoxVisable.value = false
    getCommentByArticleId(getArticleId())
    newCommentSelf.value = ''
}


// 控制回复评论的窗口是否可视
const addCommentReplyBoxVisable = ref(false)
// 打开回复窗口
const openAddCommentRelayBoxVisable = (id,tempContent) => {
    subCommentWillReplyId.value=id
    subCommentWillReplyIdContent.value=tempContent
    addCommentReplyBoxVisable.value = true;
}

// 回复的评论内容
const newCommentRelay = ref('')
// 当前正在查看的根评论
const thisRootComment = ref();
// 发布回复评论
const addCommentReply = async (newCommentData) => {
    let result = await addCommentSelfService(newCommentData);
    ElMessage.success("发布成功")
    addCommentSelfBoxVisable.value = false
    getCommentByArticleId(getArticleId())
    newCommentSelf.value = ''
}

// 在已经获取到的数据中根据ID查找
const getCommentByIdLocal=(id)=>{
    for(let i=0;i<commentList.value.length;++i){
        if(commentList.value[i].id===id){
            return commentList.value[i]
        }
    }
}

// 子评论窗口是否可视
const subCommentBoxVisable = ref(false)
// 当前选定评论的子评论列表
const thisSubCommentList = ref([])

// 尝试根据父评论id来获取某个根评论的所有下级评论
const openSubCommentBox = (fatherId) => {
    thisRootComment.value=getCommentByIdLocal(fatherId);
    let subCommentListTemp = [] // 存储临时的评论id
    for (let i = 0; i < commentList.value.length; ++i) {
        if (commentList.value[i].parentCommentId === fatherId) {
            subCommentListTemp.push(commentList.value[i].id)
        }
    }
    let flag = true;
    while (flag) {
        flag=false;
        for (let i = 0; i < commentList.value.length; ++i) {
            if (subCommentListTemp.indexOf(commentList.value[i].id) === -1
                && subCommentListTemp.indexOf(commentList.value[i].parentCommentId) !== -1
            ) {
                subCommentListTemp.push(commentList.value[i].id)
                flag=true;
            }
        }
    }
    thisSubCommentList.value=[]
    for(let i=0;i<subCommentListTemp.length;++i){
        thisSubCommentList.value.push(getCommentByIdLocal(subCommentListTemp[i]))
        let thisParentCommentId=thisSubCommentList.value[i].parentCommentId;
        thisSubCommentList.value[i].parentCommentContent=getCommentByIdLocal(thisParentCommentId).content
        thisSubCommentList.value[i].parentCommentUserName=getCommentByIdLocal(thisParentCommentId).commentUserName
        thisSubCommentList.value[i].parentCommentUserId=getCommentByIdLocal(thisParentCommentId).userId
    }
    console.log(thisSubCommentList.value)
    subCommentBoxVisable.value=true
}


// 二级评论列表页面的默认输入框内容，输入的是要恢复给一级评论的内容
const subCommentData=ref('')
// 给一级评论回复一个评论
const sendSubComment=()=>{
    let newCommentSelfData = {
        content: subCommentData.value,
        type: 1,
        parentCommentId: thisRootComment.value.id,
        parentId: getArticleId(),
    }
    addCommentReply(newCommentSelfData);
    subCommentData.value=''
}

// 当前正在回复的二级评论的ID
const subCommentWillReplyId=ref()
// 当前正在回复的二级评论的内容
const subCommentWillReplyIdContent=ref('')


// 给二级评论回复的评论内容
const subSubCommentData=ref('')
// 给二级评论回复
const sendSubSubComment=()=>{
    let newCommentSelfData = {
        content: subSubCommentData.value,
        type: 1,
        parentCommentId: subCommentWillReplyId.value,
        parentId: getArticleId(),
    }
    addCommentReply(newCommentSelfData);
    subSubCommentData.value=''
    addCommentReplyBoxVisable.value=false
}

// ----------------------------------------------------markdown处理、大纲处理-----------------------------------------------------------------
// markdown-对象
const editor = ref(null)

// markdown-文章标题列表
const titleList = ref([])

// 记录当前所处的位置对应的大纲里的标题index
const heightTitle = ref(0)

// 文章内容板块滚动块
const scrollbar = ref(null)

// 处理大纲板块的位置和大小变动
const handleOutlineBlockResize = () => {
    adaptOutlinePosition()
}

// 大纲当前滚动的位置
const outlineScrollHeight = ref(0)

// 大纲滚动板块组件
const outlineScrollRef = ref()


// markdown-生成大纲标题
async function getTitle() {
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



// markdown-大纲点击标题跳转
const rollTo = (anchor, index) => {
    const { lineIndex } = anchor;// 获取要跳转的标签的lineIndex
    const heading = editor.value.querySelector(// 查找lineIndex对应的元素对象
        `.v-md-editor-preview [data-v-md-line="${lineIndex}"]`
    );
    if (heading) {// 页面跳转
        console.log(666)
        heading.scrollIntoView({ behavior: "smooth", block: "start" })
        scrollbar.value
        let old = scrollbar.value.$refs.wrapRef.scrollTop;
        scrollbar.value.$refs.wrapRef.scrollTop = old - 100;
    }
    heightTitle.value = index// 修改当前高亮的标题
}

// 定义大纲样式
const outlineStyle = ref({
    width: `100%`,
    transform: 'translate(0,0px)',
    'animation-duration': '0s',
    position: 'fixed',
    borderRadius: `var(--el-border-radius-base)`
})

//初始化大纲板块位置
const initOutlinePosition = () => {
    const windowHeight = window.innerHeight;
    const windowWidth = window.innerWidth;
    const outlintWidth = parseInt((windowWidth * 20.0 / 24) * 5.0 / 24);
    const outlineRight = parseInt(windowWidth * 2.0 / 24)
    outlineStyle.value.left = `${outlineRight}px`
    outlineStyle.value.width = `${outlintWidth}px`
    let topPosY = windowHeight * 0.4 + 50;
    let scrollTop = scrollbar.value.$refs.wrapRef.scrollTop;
    let thisPosY = topPosY - scrollTop;
    outlineStyle.value.top = `${thisPosY}px`
    outlineStyle.value.bottom = '20px'
    outlineScrollHeight.value = `${windowHeight * 0.6 - 160}px`
}


// 根据窗口大小和滚动位置来自适应调节大纲板块的视图位置和高度
const adaptOutlinePosition = () => {
    const windowHeight = window.innerHeight;
    const windowWidth = window.innerWidth;
    const outlintWidth = parseInt((windowWidth * 20.0 / 24) * 5.0 / 24);
    const outlineRight = parseInt(windowWidth * 2.0 / 24)
    outlineStyle.value.left = `${outlineRight}px`
    outlineStyle.value.width = `${outlintWidth}px`
    let scrollTop = scrollbar.value.$refs.wrapRef.scrollTop;
    outlineStyle.value.bottom = '20px'
    if (scrollTop < 0.3 * windowHeight) {
        let topPosY = windowHeight * 0.4 + 50;
        outlineStyle.value.top = `${topPosY}px`
    } else {
        outlineStyle.value.top = `90px`
        outlineScrollHeight.value = `${windowHeight - 200}px`
    }
    const outlineScrollTop = outlineScrollRef.value.$refs.wrapRef.scrollTop;
    const highLightTop = 28 * (heightTitle.value + 1);
    // console.log("当前高亮标题距离大纲顶部距离"+(highLightTop))
    // console.log("当前大纲滚动距离"+(outlineScrollTop))
    // console.log("当前高亮标题距离大纲滚动区顶部距离" + (highLightTop - outlineScrollTop))
    // console.log("大纲滚动高度" + outlineScrollHeight.value)
    const highLightToScrollAreaTopDistance = highLightTop - outlineScrollTop;
    if (highLightToScrollAreaTopDistance >= parseInt(outlineScrollHeight.value)) {
        let delta = parseInt(outlineScrollHeight.value)
        outlineScrollRef.value.$refs.wrapRef.scrollTop += delta / 2
    }
    if (highLightToScrollAreaTopDistance < 0) {
        let delta = parseInt(outlineScrollHeight.value)
        outlineScrollRef.value.$refs.wrapRef.scrollTop -= delta / 2
    }

}



// 处理整个页面滚动事件
const handleScroll = () => {
    let scrollTop = scrollbar.value.$refs.wrapRef.scrollTop;
    const absList = [] // 各个h标签与当前距离绝对值
    titleList.value.forEach((item) => {
        absList.push(Math.abs(item.height - scrollTop))
    })
    // 屏幕滚动距离与标题高度最近的index高亮
    heightTitle.value = absList.indexOf(Math.min.apply(null, absList))
    adaptOutlinePosition()
}





// -----------------------------------------------------钩子函数--------------------------------------------------------------
onMounted(() => {
    window.addEventListener('resize', handleOutlineBlockResize);
    initOutlinePosition()
    getArticleData()
    setTimeout(() => {
        getTitle()
    }, 1000)
})

onActivated(() => {
    initOutlinePosition()
    getArticleData()
    setTimeout(() => {
        getTitle()
    }, 1000)
})

onBeforeUnmount(() => {
    window.removeEventListener('resize', handleOutlineBlockResize);
})




</script>

<template>
    <div style="position: fixed;z-index:999;border:0px solid red;width:100%;height:100px">
        <el-menu :default-active="topMenuActiveIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false"
            @select="handleTopMenuSelect" style="height: 70px;position:flxed" background-color="rgba(255,255,255,0.5)"
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
                <el-button size="large" :icon="Search" circle @click="searchBoxVisible = true" />
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
            <el-col :style="{ border: `0px solid red` }" :span="20">
                <el-row :style="{ border: `0px solid red` }" :gutter="0" justify="space-between">
                    <el-col :style="{ border: `0px solid red` }" :span="5"><!--左侧栏，文章大纲-->
                        <el-card :style="outlineStyle">
                            <el-text class="mx-1" size="large" type="primary"
                                :style="{ fontSize: `24px` }">文章大纲</el-text><br><br>
                            <el-scrollbar :height="outlineScrollHeight" ref="outlineScrollRef">
                                <el-row justify="center">
                                    <div :style="{ width: `100%` }"><!--文章大纲-->
                                        <div v-for="(anchor, index) in titleList"
                                            :style="{ padding: `0px 0 0px ${anchor.indent * 10 + 5}px`, fontSize: `${(anchor.indent) * 3 + 12}px`, height: `28px`, display: 'flex', alignItem: `center` }"
                                            @click="rollTo(anchor, index)"
                                            :class="index === heightTitle ? 'title-active' : 'title-not-active'">
                                            <!-- <a style="cursor: pointer">{{ anchor.title.length>10?anchor.title.slice(0,10):anchor.title }}</a> -->
                                            <el-text class="mx-1" style="cursor: pointer" truncated>{{ anchor.title
                                            }}</el-text>
                                        </div>
                                    </div>
                                </el-row>
                            </el-scrollbar>
                        </el-card>
                    </el-col>
                    <!-- <el-row :span="1"></el-row> -->
                    <el-col :style="{ border: `0px solid red`, background: `none`, alignItem: `center` }"
                        :span="13"><!--文章内容-->
                        <el-card :style="{ width: '100%', borderRadius: `var(--el-border-radius-base)` }">
                            <div ref="editor"><!--文章内容-->
                                <v-md-preview :text="article.articleContent"></v-md-preview>
                            </div>
                        </el-card>
                    </el-col>
                    <!-- <el-row :span="1"></el-row> -->
                    <el-col :style="{ border: `0px solid red` }" :span="5"><!--右侧栏，作者信息-->
                        <el-card :style="{ borderRadius: `0 0 0 0` }"><!--var(--el-border-radius-round)-->
                            <el-row justify="center">
                                <el-col :span="24">
                                    <div class="demo-basic--circle">
                                        <el-row :style="{ width: `100%` }">
                                            <el-col :span="24"
                                                :style="{ display: 'flex', border: `0px solid red`, alignItem: `center`, justifyContent: 'center', height: `145px` }">
                                                <div><br><br>
                                                    <el-avatar :size="90" :src="exportImgSrc(circleUrl)"
                                                        :style="{ border: `0px solid red`, boxShadow: `var(--el-box-shadow)` }" />
                                                </div>
                                            </el-col>
                                        </el-row>
                                        <el-row
                                            :style="{ border: `0px solid red`, width: `100%`, borderBottom: `1px solid lightgray`, height: `90px` }">
                                            <el-col :span="24">
                                                <el-text class="mx-1"
                                                    :style="{ width: `100%`, display: 'flex', alignItem: `center`, justifyContent: 'center', fontSize: `21px`, fontFamily: `微软雅黑`, color: `black`, fontWeight: `bold` }"
                                                    size="large">Tansor</el-text><br>
                                                <el-text class="mx-1"
                                                    :style="{ width: `100%`, display: 'flex', alignItem: `center`, justifyContent: 'center', fontSize: `16px`, color: `black` }"
                                                    size="large">最好的个性签名就是没有个性签名</el-text>
                                            </el-col>
                                        </el-row><br>
                                        <el-row justify="space-between">
                                            <el-col :span="2"></el-col>
                                            <el-col :span="6"
                                                :style="{ display: 'flex', alignItem: `center`, justifyContent: 'center' }">
                                                <Icon size="35">
                                                    <Github />
                                                </Icon>
                                            </el-col>
                                            <el-col :span="6"
                                                :style="{ display: 'flex', alignItem: `center`, justifyContent: 'center' }">
                                                <Icon size="35">
                                                    <Qq />
                                                </Icon>
                                            </el-col>
                                            <el-col :span="6"
                                                :style="{ display: 'flex', alignItem: `center`, justifyContent: 'center' }">
                                                <Icon size="35">
                                                    <LogoWechat />
                                                </Icon>
                                            </el-col>
                                            <el-col :span="2"></el-col>
                                        </el-row>
                                    </div>
                                </el-col>
                            </el-row>
                        </el-card><br>
                        <el-affix :offset="90">
                            <el-card class="comment-style">
                                <el-row justify="space-between" :style="{ paddingBottom: `10px`, width: `100%` }">
                                    <el-col :span="12">
                                        <el-text :style="{ fontSize: `21px` }">评论/留言</el-text>
                                    </el-col>
                                    <el-col :span="12">
                                        <el-button type="primary" plain @click="openAddCommentSelfBox">我也说一句</el-button>
                                    </el-col>
                                </el-row>
                                <el-row class="comment-item">
                                    <el-scrollbar height="100%" :style="{ width: `100%` }">

                                        <el-row class="red-border" v-for="item in rootCommentList" :key="item.id" @click="openSubCommentBox(item.id)"
                                            :style="{ width: `100%`,cursor:`pointer`,borderBottom:`1px solid #00000011` }">
                                            <el-row class="red-border"
                                                :style="{ width: `100%`, paddingTop: `5px`, paddingBottom: `5px` }">
                                                <el-col class="red-border" :span="4">
                                                    <el-avatar :size="30" :src="item.commentUserAvatar" />
                                                </el-col>
                                                <el-col class="red-border" :span="20">
                                                    <el-text :style="{ fontSize: `16px`, color: `black` }">
                                                        {{item.commentUserName }}
                                                    </el-text>
                                                    <br>
                                                    <el-text>{{ item.createTime }}</el-text><br>
                                                    <el-text :style="{ width: `100%` }">{{ item.content }}</el-text>
                                                </el-col>
                                            </el-row>
                                        </el-row>

                                    </el-scrollbar>
                                </el-row>

                            </el-card>
                        </el-affix>
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

    <el-dialog v-model="addCommentSelfBoxVisable" title="发布新的评论" width="500">
        <el-form>
            <el-form-item label="评论内容" :label-width="formLabelWidth">
                <el-input type="textarea" v-model="newCommentSelf" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addCommentSelfBoxVisable = false">取消</el-button>
                <el-button type="primary" @click="addCommentSelf">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-dialog v-model="addCommentReplyBoxVisable" title="回复二级评论" width="500">
        <el-form>
            <el-form-item label="评论回复给" :label-width="formLabelWidth">
                <el-text>//{{ subCommentWillReplyIdContent }}</el-text>
            </el-form-item>
            <el-form-item label="评论内容" :label-width="formLabelWidth">
                <el-input type="textarea" v-model="subSubCommentData" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addCommentReplyBoxVisable = false">取消</el-button>
                <el-button type="primary" @click="sendSubSubComment">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-dialog v-model="subCommentBoxVisable" title="评论和回复" width="500">
        <el-row class="sub-comment-item">
            <el-text>共{{ thisSubCommentList.length }}条评论回复</el-text>
            <el-scrollbar height="100%" :style="{ width: `100%` }">
                <el-row class="red-border" v-for="item in thisSubCommentList" :key="item.id" :style="{ width: `100%`,borderBottom:`1px solid #00000011` }">
                    <el-row class="red-border" :style="{ width: `100%`, paddingTop: `5px`, paddingBottom: `5px` }">
                        <el-col class="red-border" :span="4">
                            <el-avatar :size="60" :src="item.commentUserAvatar" />
                        </el-col>
                        <el-col class="red-border" :span="20">
                            <el-text :style="{ fontSize: `16px`, color: `black` }">
                                {{item.commentUserName }}&emsp;回复
                            </el-text>
                            <el-text>
                                &emsp;//{{ item.parentCommentUserName }}:{{ item.parentCommentContent }}
                            </el-text>
                            <el-button type="info" link @click="openAddCommentRelayBoxVisable(item.id,`${item.parentCommentUserName}:${item.parentCommentContent}`)">&emsp;回复</el-button>
                            <br>
                            <el-text>{{ item.createTime }}</el-text><br>
                            <el-text :style="{ width: `100%` }">{{ item.content }}</el-text>
                        </el-col>
                    </el-row>
                </el-row>
            </el-scrollbar>
        </el-row>
        <template #footer>
            <el-input type="textarea" placeholder="回复一级评论" v-model="subCommentData"></el-input><br>
            <div class="dialog-footer">
                <el-button type="primary" @click="sendSubComment">发送</el-button>
            </div>
        </template>
    </el-dialog>
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
    background-color: rgba(71, 162, 255, 0.1);
    color: rgba(71, 162, 255, 1);
    border-left: 2px solid rgba(71, 162, 255, 1);
    /*font-size: 14px;*/
    font-family: '微软雅黑';
}

.title-not-active {
    color: black;
    border-left: 2px solid rgba(0, 0, 0, 0);
    /*font-size: 14px;*/
}

.comment-style {
    width: 100%;
    height: calc(100vh - 110px);
}

.red-border {
    border: 0px solid red;
}

.comment-item {
    width: 100%;
    height: calc(100vh - 190px);
}

.sub-comment-item {
    width: 100%;
    height: calc(100vh - 320px);
}
</style>
