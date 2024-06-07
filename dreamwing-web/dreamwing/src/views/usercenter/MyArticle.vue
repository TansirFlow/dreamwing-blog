<script setup>
import { ref } from 'vue'
import { getCategoryListService, getArticleStatusListService, getArticleTypeListService } from '@/api/public'


// -------------------------------------------------------列表分页处理----------------------------------------------------------
// 分页器--当前页序号
const currentPage = ref(4)

// 页码要不要底色（要的话更好看吧）
const background = ref(true)

// 每页显示的数量
const pageSize = ref(100)

// 更小的视图？？
const small = ref(false)

// 禁用？？
const disabled = ref(false)

// 调整每页数量后触发
const handleSizeChange = (val) => {
    console.log(`${val} items per page`)
}

// 切换页面后触发
const handleCurrentChange = (val) => {
    console.log(`current page: ${val}`)
}



// -------------------------------------------------------列表筛选，排序处理-----------------------------------------------------------
// 根据文章分类筛选
const categoryValue = ref('')
// 根据文章状态筛选
const statusValue = ref('')
// 根据文章类别筛选
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
                <el-select v-model="categoryValue" placeholder="Select" size="large">
                    <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName" :value="item.id" />
                </el-select>
            </div>
        </el-col>
        <el-col :span="2">
            <div class="flex flex-wrap gap-4 items-center">
                <el-select v-model="statusValue" placeholder="Select" size="large">
                    <el-option v-for="item in articleStatusList" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
            </div>
        </el-col>
        <el-col :span="2">
            <div class="flex flex-wrap gap-4 items-center">
                <el-select v-model="typeValue" placeholder="Select" size="large">
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
