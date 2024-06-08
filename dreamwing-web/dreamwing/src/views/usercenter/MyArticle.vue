<script setup>
import { ref, computed, watchEffect, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getCategoryListService, getArticleStatusListService, getArticleTypeListService } from '@/api/public'
import { getMyArticleListService, getMyArticleListByConditionService, deleteArticleServive,deleteArticleByIdListService } from '@/api/usercenter/myArticle'
import { ElMessage, ElMessageBox } from 'element-plus'

const router=useRouter()

// -------------------------------------------------------列表分页处理----------------------------------------------------------
// 分页器--当前页序号
const currentPage = ref(1)

// 页码要不要底色（要的话更好看吧）
const background = ref(true)

// 每页显示的数量
const pageSize = ref(5)

// 更小的视图？？
const small = ref(false)

// 禁用？？
const disabled = ref(false)

// 调整每页数量后触发
const handleSizeChange = (val) => {
    console.log(`${val} items per page`)
    getMyArticleList()
}

// 切换页面后触发
const handleCurrentChange = (val) => {
    console.log(`current page: ${val}`)
    getMyArticleList()
}


// -------------------------------------------------------列表筛选，排序处理-----------------------------------------------------------
// 根据文章分类筛选
const categoryValue = ref(0)
// 根据文章状态筛选
const statusValue = ref('0')
// 根据文章类别筛选
const typeValue = ref('0')

// 文章分类列表
const categoryList = ref()

// 获取文章分类列表
const getCategoryList = async () => {
    let result = await getCategoryListService()
    console.log(result.data)
    categoryList.value = result.data;
    categoryList.value.unshift({ id: 0, categoryName: '全部分类' })
}
getCategoryList()

// 文章状态列表
const articleStatusList = ref()

// 获取文章状态列表
const getArticleStatusList = async () => {
    let result = await getArticleStatusListService();
    console.log(result.data)
    articleStatusList.value = result.data
    articleStatusList.value.unshift({ id: '0', name: '全部状态' })
}
getArticleStatusList()

// 文章类型列表
const articleTypeList = ref()

// 获取文章类型列表
const getArticleTypeList = async () => {
    let result = await getArticleTypeListService()
    console.log(result.data)
    articleTypeList.value = result.data
    articleTypeList.value.unshift({ id: '0', name: '全部类型' })
}
getArticleTypeList()

// 列表排序
const orderRule = ref('0')
const orderRuleList = ref([
    {
        id: "0",
        name: "默认排序"
    },
    {
        id: "1",
        name: "较晚更新"
    }
])

const articleGetListByConditionData = ref({

})

// 我的文章列表
const myArticleList = ref([])
// 文章列表总长度
const articleListLength = ref()
// 获取文章列表
const getMyArticleList = async () => {
    articleGetListByConditionData.value = {
        pageNum: currentPage.value,
        pageSize: pageSize.value,
        categoryId: categoryValue.value === 0 ? null : categoryValue.value,
        statusId: statusValue.value === '0' ? null : statusValue.value,
        typeId: typeValue.value === '0' ? null : typeValue.value,
        orderRule: orderRule.value
    }
    let result = await getMyArticleListByConditionService(articleGetListByConditionData);
    articleListLength.value = result.data.total
    myArticleList.value = result.data.items
    for (let i = 0; i < myArticleList.value.length; ++i) {
        myArticleList.value[i].select = false
    }
}
getMyArticleList()



// 选中本页所有文章
const selectAllList = ref(false)

// 全选/取消全选
const handleAllSelect = () => {
    if (selectAllList.value === false) {
        for (let i = 0; i < myArticleList.value.length; ++i) {
            myArticleList.value[i].select = false
        }
    } else {
        for (let i = 0; i < myArticleList.value.length; ++i) {
            myArticleList.value[i].select = true
        }
    }
}

// 监听文章条目选中状态变化
watch(
    () => myArticleList.value, // 监听的数组
    (newArray, oldArray) => {
        let allFlag=true
        for(let i=0;i<myArticleList.value.length;++i){
            if(myArticleList.value[i].select===false){
                allFlag=false;
                break;
            }
        }
        if(myArticleList.value.length===0)allFlag=false;
        selectAllList.value=allFlag;
    },
    { deep: true }
);

// 根据文章状态id获得状态名
const getStatusNameById = (id) => {
    for (let i = 0; i < articleStatusList.value.length; ++i) {
        if (articleStatusList.value[i].id === `${id}`) {
            return articleStatusList.value[i].name
        }
    }
}

// 处理筛选条件变化事件
const handleConditionChange = (value) => {
    console.log(value)
    getMyArticleList()
}


// 删除文章操作（不是真的删除，只是在数据库打一个id_delete=1的标记）
const deleteArticle = async (id) => {
    let result = await deleteArticleServive(id);
    ElMessage.success("删除成功")
    getMyArticleList()
}

// 根据id删除文章
const deleteArticleById = (id) => {
    ElMessageBox.confirm(
        '确定要删除这篇文章吗?',
        '警撅',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            deleteArticle(id)
        })
        .catch(() => {
            ElMessage.error("操作已取消")
        })
}


const deleteArticleByIdList=async ()=>{
    let idList=[]
    for(let i=0;i<myArticleList.value.length;++i){
        if(myArticleList.value[i].select===true){
            idList.push(myArticleList.value[i].id)
        }
    }
    let result = await deleteArticleByIdListService(idList)
    getMyArticleList()
}

// 根据id列表删除文章
const deleteArticleByList = () => {
    ElMessageBox.confirm(
        '确定要删除选中的文章吗?',
        '警撅',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            deleteArticleByIdList()
        })
        .catch(() => {
            ElMessage.error("操作已取消")
        })
}


// -------------------------------------------------页面跳转------------------------------------------------------------
// 点击文章后去编辑文章
const handleClickArticleToEdit = (id) => {
    router.push(`/uc/editArticle/${id}`)
}

// 跳转到新增文章页面
const gotoAddArticlePage=()=>{
    router.push('/uc/editarticle')
}

// 在新窗口查看文章
const openArticleNewWindow=(id)=>{
    window.open(`#/article/${id}`)
}

</script>
<template>
    <div :style="{ background: `white`, padding: `10px` }">
        <el-row :style="{ padding: `10px`, borderBottom: `1px solid #00000011` }" justify="space-between">
            <el-col :span="2">
                <el-checkbox v-model="selectAllList" label="全选" size="large" @change="handleAllSelect" />
            </el-col>
            <el-col :span="2" v-if="selectAllList">
                <el-button type="danger" size="large" plain @click="deleteArticleByList">删除选中</el-button>
            </el-col>
            <el-col :span="4">
                <el-input placeholder="输入关键词搜索" size="large"></el-input>
            </el-col>
            <el-col :span="2">
                <el-button size="large" type="primary" @click="gotoAddArticlePage">发布文章</el-button>
            </el-col>
            <el-col :span="3">
                <div class="flex flex-wrap gap-4 items-center">
                    <el-select v-model="categoryValue" placeholder="Select" size="large" @change="handleConditionChange">
                        <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName"
                            :value="item.id" />
                    </el-select>
                </div>
            </el-col>
            <el-col :span="3">
                <div class="flex flex-wrap gap-4 items-center">
                    <el-select v-model="statusValue" placeholder="Select" size="large" @change="handleConditionChange">
                        <el-option v-for="item in articleStatusList" :key="item.id" :label="item.name" :value="item.id" />
                    </el-select>
                </div>
            </el-col>
            <el-col :span="3">
                <div class="flex flex-wrap gap-4 items-center">
                    <el-select v-model="typeValue" placeholder="Select" size="large" @change="handleConditionChange">
                        <el-option v-for="item in articleTypeList" :key="item.id" :label="item.name" :value="item.id" />
                    </el-select>
                </div>
            </el-col>
            <el-col :span="3">
                <div class="flex flex-wrap gap-4 items-center">
                    <el-select v-model="orderRule" placeholder="Select" size="large">
                        <el-option v-for="item in orderRuleList" :key="item.id" :label="item.name" :value="item.id"
                            @change="handleConditionChange" />
                    </el-select>
                </div>
            </el-col>

        </el-row>
        <el-row>

            <div v-for="item in myArticleList" :key="item.id"
                :style="{ width: `100%`, height: `60px`, borderBottom: `1px solid #00000011`, padding: `10px` }">
                <el-row :style="{ width: `100%`, height: `100%` }" class="text-center">
                    <el-col :span="15" class="text-center">
                        <el-row class="text-center" :style="{ width: `100%` }">
                            <el-col :span="1" class="text-center">
                                <el-checkbox v-model="item.select" size="large"
                                    :style="{ width: `60px`, height: `60px` }" />
                            </el-col>
                            <el-col :span="23" class="text-center">
                                <el-row class="text-center">
                                    <el-col :span="24" class="text-center">
                                        <el-text @click="handleClickArticleToEdit(item.id)"
                                            :style="{ cursor: `pointer`, fontSize: `18px`, color: `black` }">
                                            {{ item.articleTitle }}
                                        </el-text>
                                    </el-col>
                                    <el-col :span="24" class="text-center">
                                        <el-text>
                                            访问量:{{ item.lookNum }}
                                        </el-text>
                                    </el-col>
                                </el-row>
                            </el-col>
                        </el-row>

                    </el-col>
                    <el-col :span="1" class="text-center">
                        <el-text>{{ getStatusNameById(item.status) }}</el-text>
                    </el-col>
                    <el-col :span="3" class="text-center">
                        <el-text>
                            {{ item.updateTime }}
                        </el-text>
                    </el-col>
                    <el-col :span="5" class="text-center">
                        <el-text>
                            <el-button type="success" plain @click="openArticleNewWindow(item.id)">查看</el-button>
                            <el-button type="primary" plain @click="handleClickArticleToEdit(item.id)">编辑</el-button>
                            <el-button type="danger" plain @click="deleteArticleById(item.id)">删除</el-button>
                        </el-text>
                    </el-col>
                </el-row>
            </div>
        </el-row>
        <el-row :style="{ padding: `10px` }">
            <div class="demo-pagination-block">
                <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize"
                    :page-sizes="[5, 10, 20, 30, 40, 50, 100, 200]" :small="small" :disabled="disabled"
                    :background="background" layout="total, sizes, prev, pager, next, jumper" :total="articleListLength"
                    @size-change="handleSizeChange" @current-change="handleCurrentChange" />
            </div>
        </el-row>
    </div>
</template>
<style>
.demo-pagination-block+.demo-pagination-block {
    margin-top: 10px;
}

.demo-pagination-block .demonstration {
    margin-bottom: 16px;
}

.text-center {
    display: flex;
    align-item: center;
    justifyContent: center;
    /* border: 1px solid red; */
}
</style>
