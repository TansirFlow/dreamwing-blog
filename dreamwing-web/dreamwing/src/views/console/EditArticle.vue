<script setup>
import { ref, nextTick } from 'vue'
import { ElInput, ElMessage } from 'element-plus'
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
import 'juejin-markdown-themes/dist/juejin.min.css'
import zhHans from 'bytemd/locales/zh_Hans.json'
import 'highlight.js/styles/vs.css'
import { addArticleService} from '@/api/console/editArticle'
import { getCategoryListService, getArticleStatusListService, getArticleTypeListService } from '@/api/public'











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
import { uploadImageService } from '@/api/console/editArticle'

const uploadImages=async (file)=>{
    let result=await uploadImageService(file)
    return result.data
}

const uploadImage = async (files) => {
    console.log("测试图片上传")
    let imageUrl=await uploadImages(files[0])
    console.log("图片链接："+imageUrl)
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

const addArticle =async ()=>{
    const articleItem={
        "articleTitle":articleTitle.value,
        "articleContent":articleContent.value,
        "status":statusValue.value,
        "type":typeValue.value,
        "tagList":dynamicTags.value
    }
    console.log(articleItem)
    let result=await addArticleService(articleItem);
    ElMessage.success("发布成功")
}

</script>
<template>
    <el-row :style="{ width: `100%` }" justify="space-between">
        <el-col :span="2">
            <el-select v-model="categoryValue" placeholder="Select" size="large" style="width: 100%">
                <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName"
                    :value="item.id" />
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
            <el-button size="large" :style="{ width: `80%` }" type="primary">附加属性</el-button>
        </el-col>
        <el-col :span="7">
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
                <el-button @click="addArticle">发布</el-button>
            </div>
        </el-col>
    </el-row><br>
    <el-row :style="{ width: `100%` }">
        <el-col :span="24">
            <Editor class="editos" :value="articleContent" :plugins="plugins" :locale="zhHans" @change="handleChange"
                :class="bytemd" :uploadImages="uploadImage" />
        </el-col>

    </el-row>



</template>
<style>
.bytemd {
    height: calc(100vh - 165px);
}
</style>
