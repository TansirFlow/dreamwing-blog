<script setup>
import { ref } from 'vue'
import { getCategoryListService, getArticleStatusListService, getArticleTypeListService } from '@/api/public'

const currentPage = ref(4)
const background = ref(true)
const pageSize = ref(100)
const small = ref(false)
const disabled = ref(false)
const handleSizeChange = (val) => {
    console.log(`${val} items per page`)
}
const handleCurrentChange = (val) => {
    console.log(`current page: ${val}`)
}


const value = ref('')


const categoryList = ref()
const getCategoryList = async () => {
    let result = await getCategoryListService()
    categoryList.value = result.data;
}
getCategoryList()


const articleStatusList = ref()
const getArticleStatusList = async () => {
    let result = await getArticleStatusListService();
    articleStatusList.value = result.data
}
getArticleStatusList()

const articleTypeList = ref()
const getArticleTypeList = async () => {
    let result = await getArticleTypeListService()
    articleTypeList.value = result.data
}
getArticleTypeList()
</script>
<template>
    我的文章
    <el-row>
        <el-col :span="1">
            <el-checkbox v-model="checked1" label="全选" size="large" />
        </el-col>
        <el-col :span="4">
            <el-input placeholder="输入关键词搜索" size="large"></el-input>
        </el-col>
        <el-col :span="2">
            <el-button size="large">发布文章</el-button>
        </el-col>
        <el-col :span="2">
            <div class="flex flex-wrap gap-4 items-center">
                <el-select v-model="value" placeholder="Select" size="large" >
                    <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName"
                        :value="item.id" />
                </el-select>
            </div>
        </el-col>
        <el-col :span="2">
            <div class="flex flex-wrap gap-4 items-center">
                <el-select v-model="value" placeholder="Select" size="large" >
                    <el-option v-for="item in articleStatusList" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
            </div>
        </el-col>
        <el-col :span="2">
            <div class="flex flex-wrap gap-4 items-center">
                <el-select v-model="value" placeholder="Select" size="large" >
                    <el-option v-for="item in articleTypeList" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
            </div>
        </el-col>



    </el-row>
    <el-row>

    </el-row>
    <el-row>
        <div class="demo-pagination-block">
            <div class="demonstration">All combined</div>
            <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize"
                :page-sizes="[100, 200, 300, 400]" :small="small" :disabled="disabled" :background="background"
                layout="total, sizes, prev, pager, next, jumper" :total="400" @size-change="handleSizeChange"
                @current-change="handleCurrentChange" />
        </div>
    </el-row>

</template>
<style>
.demo-pagination-block+.demo-pagination-block {
    margin-top: 10px;
}

.demo-pagination-block .demonstration {
    margin-bottom: 16px;
}
</style>
