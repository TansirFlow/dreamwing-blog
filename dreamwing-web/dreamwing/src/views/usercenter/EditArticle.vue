<script setup>
import { ref, nextTick, onMounted } from 'vue'
import { ElInput, ElMessage } from 'element-plus'
import { Editor, Viewer } from '@bytemd/vue-next'
import { useRoute } from 'vue-router'
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
import 'juejin-markdown-themes/dist/juejin.min.css'
import zhHans from 'bytemd/locales/zh_Hans.json'
import 'highlight.js/styles/vs.css'
import { getArticleDetailService, addArticleService, updateArticleService, uploadImageService } from '@/api/usercenter/editArticle'
import { getCategoryListService, getArticleStatusListService, getArticleTypeListService } from '@/api/public'

import { Plus } from '@element-plus/icons-vue'




// --------------------------------------------byteMD编辑器相关--------------------------------------------------
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
const articleContent = ref('')
const components = [
    Viewer, Editor
]
const handleChange = (v) => {
    articleContent.value = v
}




// -----------------------------------------------------------动态tag编辑--------------------------------------------------
// 标签输入框输入内容
const tagInputValue = ref('')
// 标签列表
const dynamicTags = ref([])
// 切换标签输入框显示与否
const inputVisible = ref(false)
// 标签输入框组件
const InputRef = ref('')
// 处理tag输入框关闭
const handleTagInputClose = (tag) => {
    dynamicTags.value.splice(dynamicTags.value.indexOf(tag), 1)
}
// 显示tag
const showInput = () => {
    inputVisible.value = true
    nextTick(() => {
        InputRef.value.input.focus()
    })
}
// 处理tag输入确认
const handleInputConfirm = () => {
    if (tagInputValue.value) {
        dynamicTags.value.push(tagInputValue.value)
    }
    inputVisible.value = false
    tagInputValue.value = ''
}


// -----------------------------------------------------图片粘贴自动上传----------------------------------------------------------
const uploadImages = async (file) => {
    let result = await uploadImageService(file)
    return result.data
}

const uploadImage = async (files) => {
    console.log("测试图片上传")
    let imageUrl = await uploadImages(files[0])
    console.log("图片链接：" + imageUrl)
    return [
        {
            title: files.map((i) => i.name),
            url: imageUrl
        }
    ]
}


// ----------------------------------------------------文章相关处理-------------------------------------------------
// 文章标题输入
const articleTitle = ref('')
// 文章分类
const categoryValue = ref('')
// 文章状态
const statusValue = ref('')
// 文章类别
const typeValue = ref('')

// 文章分类列表
const categoryList = ref()

// 获取文章分类列表
const getCategoryList = async () => {
    let result = await getCategoryListService()
    categoryList.value = result.data;
}
getCategoryList()

// 文章状态列表
const articleStatusList = ref()

// 获取文章状态列表
const getArticleStatusList = async () => {
    let result = await getArticleStatusListService();
    articleStatusList.value = result.data
}
getArticleStatusList()

// 文章类型列表
const articleTypeList = ref()

// 获取文章类型列表
const getArticleTypeList = async () => {
    let result = await getArticleTypeListService()
    articleTypeList.value = result.data
}
getArticleTypeList()

// 发布文章
const addArticle = async () => {
    const articleItem = {
        "articleTitle": articleTitle.value,
        "articleContent": articleContent.value,
        "status": statusValue.value,
        "categoryId": categoryValue.value,
        "type": typeValue.value,
        "tagList": dynamicTags.value,
        "articleCover": articleCoverUrl.value,
        "articleAbstract": articleAbstract.value,
        "password": lookPassword.value
    }

    if (getArticleIdFromPath() !== undefined) {
        articleItem.id=getArticleIdFromPath()
        let result = await updateArticleService(articleItem);
        ElMessage.success("更新成功")
    } else {
        let result = await addArticleService(articleItem);
        ElMessage.success("发布成功")
    }
}

// 附加属性设置窗口是否可视
const settingBoxVisible = ref(false)

// 打开附加属性设置窗口
const openSettingBoxWindow = () => {
    settingBoxVisible.value = true
}

// 文章摘要
const articleAbstract = ref()

// 文章查看密码
const lookPassword = ref()

// 文章封面图片url
const articleCoverUrl = ref('')

// 图片上传成功时触发
const handleCoverUploadSuccess = (response, uploadFile) => {
    articleCoverUrl.value = URL.createObjectURL(uploadFile.raw)
}

// 图片上传前触发
const beforeCoverUpload = (rawFile) => {
    if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
        ElMessage.error('Avatar picture must be JPG format!')
        return false
    } else if (rawFile.size / 1024 / 1024 > 2) {
        ElMessage.error('Avatar picture size can not exceed 2MB!')
        return false
    }
    return true
}

// 上传文章封面图片
const toUploadCoverImg = async (UploadRequestOptions) => {
    let files = UploadRequestOptions.file
    let imageUrl = await uploadImages(files)
    articleCoverUrl.value = imageUrl
}



// ----------------------------------------------------------初始化数据获取--------------------------------------------------------
//获取路由对象
const currentRoute = useRoute();


//从路径参数获取文章id
const getArticleIdFromPath = () => {
    return currentRoute.params.id;
}

onMounted(() => {
    let id = getArticleIdFromPath()
    if (id !== undefined) {
        loadArticleDetail(id)
    }
})

const loadArticleDetail = async (id) => {
    let result = await getArticleDetailService(id);
    console.log(result.data)
    articleTitle.value = result.data.articleTitle
    articleContent.value = result.data.articleContent
    categoryValue.value = result.data.categoryId
    statusValue.value = `${result.data.status}`
    typeValue.value = `${result.data.type}`
    dynamicTags.value = result.data.tagList
    articleCoverUrl.value = result.data.articleCover
    articleAbstract.value = result.data.articleAbstract
    lookPassword.value = result.data.password
}


</script>
<template>
    <el-row :style="{ width: `100%` }" justify="space-between">
        <el-col :span="2">
            <el-select v-model="categoryValue" placeholder="Select" size="large" style="width: 100%">
                <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName" :value="item.id" />
            </el-select>
        </el-col>
        <el-col :span="2">
            <el-select v-model="statusValue" placeholder="Select" size="large" style="width: 100%">
                <el-option v-for="item in articleStatusList" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
        </el-col>
        <el-col :span="2">
            <el-select v-model="typeValue" placeholder="Select" size="large" style="width: 100%">
                <el-option v-for="item in articleTypeList" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
        </el-col>
        <el-col :span="8">
            <el-input v-model="articleTitle" style="width: 100%" maxlength="30" size="large" placeholder="文章标题"
                show-word-limit type="text" />
        </el-col>
        <el-col :span="2">
            <el-button size="large" :style="{ width: `80%` }" @click="openSettingBoxWindow">附加属性</el-button>
        </el-col>
        <el-col :span="5">
            <div class="flex gap-2" style="width:100%;line-height: 2.3;">
                <el-tag v-for="tag in dynamicTags" :key="tag" closable :disable-transitions="false" size="large"
                    @close="handleTagInputClose(tag)">
                    {{ tag }}
                </el-tag>
                <el-input v-if="inputVisible" ref="InputRef" v-model="tagInputValue" class="w-20"
                    :style="{ width: `100px` }" @keyup.enter="handleInputConfirm" @blur="handleInputConfirm" />
                <el-button v-else class="button-new-tag" @click="showInput">
                    + 标签
                </el-button>
            </div>
        </el-col>
        <el-col :span="2">
            <el-button size="large" :style="{ width: `80%` }" type="primary" @click="addArticle">发布文章</el-button>
        </el-col>
    </el-row><br>
    <el-row :style="{ width: `100%` }">
        <el-col :span="24">
            <Editor class="editos" :value="articleContent" :plugins="plugins" :locale="zhHans" @change="handleChange"
                :class="bytemd" :uploadImages="uploadImage" />
        </el-col>

    </el-row>


    <el-dialog v-model="settingBoxVisible" title="设置附加属性" width="500">
        <el-form>
            <el-form-item label="文章封面" :label-width="formLabelWidth">
                <!-- <el-input v-model="articleCover" autocomplete="off" /> -->
                <!-- <el-image style="width: 100%; height: 200px" :src="url" :fit="fit" /> -->

                <el-upload class="avatar-uploader" action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                    :show-file-list="false" :on-success="handleCoverUploadSuccess" :before-upload="beforeCoverUpload"
                    :http-request="toUploadCoverImg">
                    <img v-if="articleCoverUrl" :src="articleCoverUrl" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon">
                        <Plus />
                    </el-icon>
                </el-upload>

            </el-form-item>
            <el-form-item label="文章摘要" :label-width="formLabelWidth">
                <el-input v-model="articleAbstract" autocomplete="off" />
            </el-form-item>
            <el-form-item label="查看密码" :label-width="formLabelWidth">
                <el-input v-model="lookPassword" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="settingBoxVisible = false">取消</el-button>
                <el-button type="primary" @click="settingBoxVisible = false">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>
<style>
.bytemd {
    height: calc(100vh - 165px);
}

.avatar-uploader .avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>
<style scoped>
.avatar-uploader .avatar {
    width: 100%;
    height: 178px;
    display: block;
}

.avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 350px;
    height: 200px;
    text-align: center;
}
</style>