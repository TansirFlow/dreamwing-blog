<script setup>
import { ref, nextTick } from 'vue'
import { ElInput } from 'element-plus'
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
const value = ref('')
const components = [
    Viewer, Editor
]
const handleChange = (v) => {
    value.value = v
}



const inputValue = ref('')
const dynamicTags = ref([])
const inputVisible = ref(false)
const InputRef = ref('')

const handleClose = (tag) => {
    dynamicTags.value.splice(dynamicTags.value.indexOf(tag), 1)
}

const showInput = () => {
    inputVisible.value = true
    nextTick(() => {
        InputRef.value.input.focus()
    })
}

const handleInputConfirm = () => {
    if (inputValue.value) {
        dynamicTags.value.push(inputValue.value)
    }
    inputVisible.value = false
    inputValue.value = ''
}

const articleTitle = ref('')
const articleCategoryId = ref('默认分类')
const articleCategoryList = ref([
    {
        id: "1",
        categoryName: "默认分类",
        createTime: "2024-05-01",
        updateTime: "",
        createUser: "1",
        createUserName: "Tansor"
    },
    {
        id: "2",
        categoryName: "计算机技术",
        createTime: "2024-05-01",
        updateTime: "",
        createUser: "1",
        createUserName: "Tansor"
    },
    {
        id: "3",
        categoryName: "人文地理",
        createTime: "2024-05-01",
        updateTime: "",
        createUser: "1",
        createUserName: "Tansor"
    },
])

const articleTypeId = ref('原创')
const articleType = ref([
    {
        id: '1',
        type: '原创'
    },
    {
        id: '2',
        type: '转载'
    },
    {
        id: '3',
        type: '翻译'
    },
])
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
</script>
<template>
    <el-row :style="{ width: `100%` }" justify="space-between">
        <el-col :span="2">
            <el-select v-model="articleCategoryId" placeholder="Select" size="large" style="width: 100%">
                <el-option v-for="item in articleCategoryList" :key="item.id" :label="item.categoryName"
                    :value="item.categoryName" />
            </el-select>
        </el-col>
        <el-col :span="2">
            <el-select v-model="articleTypeId" placeholder="Select" size="large" style="width: 100%">
                <el-option v-for="item in articleType" :key="item.id" :label="item.type" :value="item.type" />
            </el-select>
        </el-col>
        <el-col :span="10">
            <el-input v-model="articleTitle" style="width: 100%" maxlength="30" size="large" placeholder="文章标题"
                show-word-limit type="text" />
        </el-col>
        <el-col :span="2">
            <el-button size="large" :style="{ width: `80%` }" type="primary">附加属性</el-button>
        </el-col>
        <el-col :span="7">
            <div class="flex gap-2" style="width:100%;line-height: 2.3;">
                <el-tag v-for="tag in dynamicTags" :key="tag" closable :disable-transitions="false" size="large"
                    @close="handleClose(tag)">
                    {{ tag }}
                </el-tag>
                <el-input v-if="inputVisible" ref="InputRef" v-model="inputValue" class="w-20"
                    :style="{ width: `100px` }" @keyup.enter="handleInputConfirm" @blur="handleInputConfirm" />
                <el-button v-else class="button-new-tag" @click="showInput">
                    + 标签
                </el-button>
            </div>
        </el-col>
    </el-row><br>
    <el-row :style="{ width: `100%` }">
        <el-col :span="24">
            <Editor class="editos" :value="value" :plugins="plugins" :locale="zhHans" @change="handleChange"
                :class="bytemd" :uploadImages="uploadImage" />
        </el-col>

    </el-row>



</template>
<style>
.bytemd {
    height: calc(100vh - 165px);
}
</style>
