<script setup>
import { ref, computed, watchEffect, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getCategoryListService, getArticleStatusListService, getArticleTypeListService } from '@/api/public'
import { getMyAttachmentListByConditionService, deleteAttachmentServive, deleteAttachmentByIdListService, getAttachmentTypeListService,uploadFileService } from '@/api/usercenter/myAttachment'
import { ElMessage, ElMessageBox } from 'element-plus'
const router = useRouter()

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
    getMyAttachmentList()
}

// 切换页面后触发
const handleCurrentChange = (val) => {
    console.log(`current page: ${val}`)
    getMyAttachmentList()
}


// -------------------------------------------------------列表筛选，排序处理-----------------------------------------------------------
// 根据附件类型筛选
const typeValue = ref('全部类型')

// 附件类型列表
const attachmentTypeList = ref()

// 获取附件类型列表
const getAttachmentTypeList = async () => {
    let result = await getAttachmentTypeListService()
    console.log(result.data)
    attachmentTypeList.value = result.data
    attachmentTypeList.value.unshift('全部类型')
    console.log(attachmentTypeList.value)
}
getAttachmentTypeList()

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

const attachmentGetListByConditionData = ref({

})

// 我的附件列表
const myAttachmentList = ref([])
// 文章附件列表总长度
const attachmentListLength = ref()
// 获取附件列表
const getMyAttachmentList = async () => {
    attachmentGetListByConditionData.value = {
        pageNum: currentPage.value,
        pageSize: pageSize.value,
        attachmentType: typeValue.value === '全部类型' ? null : typeValue.value,
        orderRule: orderRule.value
    }
    let result = await getMyAttachmentListByConditionService(attachmentGetListByConditionData);
    attachmentListLength.value = result.data.total
    myAttachmentList.value = result.data.items
    for (let i = 0; i < myAttachmentList.value.length; ++i) {
        myAttachmentList.value[i].select = false
    }
}
getMyAttachmentList()



// 选中本页所有附件
const selectAllList = ref(false)

// 全选/取消全选
const handleAllSelect = () => {
    if (selectAllList.value === false) {
        for (let i = 0; i < myAttachmentList.value.length; ++i) {
            myAttachmentList.value[i].select = false
        }
    } else {
        for (let i = 0; i < myAttachmentList.value.length; ++i) {
            myAttachmentList.value[i].select = true
        }
    }
}

// 监听附件条目选中状态变化
watch(
    () => myAttachmentList.value, // 监听的数组
    (newArray, oldArray) => {
        let allFlag = true
        for (let i = 0; i < myAttachmentList.value.length; ++i) {
            if (myAttachmentList.value[i].select === false) {
                allFlag = false;
                break;
            }
        }
        if (myAttachmentList.value.length === 0) allFlag = false;
        selectAllList.value = allFlag;
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
    getMyAttachmentList()
}


// 删除附件操作
const deleteAttachment = async (id) => {
    let result = await deleteAttachmentServive(id);
    ElMessage.success("删除成功")
    getMyAttachmentList()
}

// 根据id删除附件
const deleteAttachmentById = (id) => {
    ElMessageBox.confirm(
        '确定要删除这个附件吗?',
        '警撅',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(() => {
            deleteAttachment(id)
        })
        .catch(() => {
            ElMessage.error("操作已取消")
        })
}


const deleteAttachmentByIdList = async () => {
    let idList = []
    for (let i = 0; i < myAttachmentList.value.length; ++i) {
        if (myAttachmentList.value[i].select === true) {
            idList.push(myAttachmentList.value[i].id)
        }
    }
    let result = await deleteAttachmentByIdListService(idList)
    getMyAttachmentList()
}

// 根据id列表删除附件
const deleteAttachmentByList = () => {
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
            deleteAttachmentByIdList()
        })
        .catch(() => {
            ElMessage.error("操作已取消")
        })
}


// -------------------------------------------------页面跳转------------------------------------------------------------
// 附件详情窗口是否可视
const lookDetailBoxVisable = ref(false)
// 存放详情窗口信息
const lookAttachmentDetailTemp = ref()
// 点击查看附件
const lookAttachmentDetail = (id) => {
    ElMessage.success("查看附件详情")
    lookAttachmentDetailTemp.value = myAttachmentList.value.find((element) => {
        return element.id === id;
    })
    lookDetailBoxVisable.value = true
}

// 跳转到新增文章页面
const uploadAttachment = () => {
    uploadBoxVisable.value = true
}

// 控制上传窗口显示与否
const uploadBoxVisable = ref(false)

// 上传板块的文件列表
const fileList = ref([
    
])

// 上传文件
const uploadFile = async (UploadRequestOptions) => {
    let files = UploadRequestOptions.file
    let imageUrl = await uploadFileService(files)
}

// 古希腊掌管文件上传的神
const handleUploadChange = (uploadFile, uploadFiles) => {
    fileList.value = fileList.value.slice(-3)
}
</script>
<template>
    <div :style="{ background: `white`, padding: `10px` }">
        <el-row :style="{ padding: `10px`, borderBottom: `1px solid #00000011` }" justify="space-between">
            <el-col :span="2">
                <el-checkbox v-model="selectAllList" label="全选" size="large" @change="handleAllSelect" />
            </el-col>
            <el-col :span="2" v-if="selectAllList">
                <el-button type="danger" size="large" plain @click="deleteAttachmentByList">删除选中</el-button>
            </el-col>
            <el-col :span="4">
                <el-input placeholder="输入关键词搜索" size="large"></el-input>
            </el-col>
            <el-col :span="2">
                <el-button size="large" type="primary" @click="uploadAttachment">上传附件</el-button>
            </el-col>
            <el-col :span="3">
                <div class="flex flex-wrap gap-4 items-center">
                    <el-select v-model="typeValue" placeholder="Select" size="large" @change="handleConditionChange">
                        <el-option v-for="item in attachmentTypeList" :key="item" :label="item" :value="item" />
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

            <div v-for="item in myAttachmentList" :key="item.id"
                :style="{ width: `100%`, height: `60px`, borderBottom: `1px solid #00000011`, padding: `10px` }">
                <el-row :style="{ width: `100%`, height: `100%` }" class="text-center">
                    <el-col :span="16" class="text-center">
                        <el-row class="text-center" :style="{ width: `100%` }">
                            <el-col :span="1" class="text-center">
                                <el-checkbox v-model="item.select" size="large"
                                    :style="{ width: `60px`, height: `60px` }" />
                            </el-col>
                            <el-col :span="23" class="text-center">
                                <el-row class="text-center">
                                    <el-col :span="24" class="text-center">
                                        <el-text @click="lookAttachmentDetail(item.id)"
                                            :style="{ cursor: `pointer`, fontSize: `18px`, color: `black` }">
                                            {{ item.fileName }}
                                        </el-text>
                                        <el-tag>{{ item.type }}</el-tag>
                                    </el-col>
                                    <el-col :span="24" class="text-center">
                                        <el-text>
                                            URL:{{ item.url }}
                                        </el-text>
                                    </el-col>
                                </el-row>
                            </el-col>
                        </el-row>

                    </el-col>
                    <el-col :span="3" class="text-center">
                        <el-text>
                            {{ item.createTime }}
                        </el-text>
                    </el-col>
                    <el-col :span="5" class="text-center">
                        <el-text>
                            <el-button type="success" plain @click="lookAttachmentDetail(item.id)">详情</el-button>
                            <el-button type="primary" plain>下载</el-button>
                            <el-button type="danger" plain @click="deleteAttachmentById(item.id)">删除</el-button>
                        </el-text>
                    </el-col>
                </el-row>
            </div>
        </el-row>
        <el-row :style="{ padding: `10px` }">
            <div class="demo-pagination-block">
                <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize"
                    :page-sizes="[5, 10, 20, 30, 40, 50, 100, 200]" :small="small" :disabled="disabled"
                    :background="background" layout="total, sizes, prev, pager, next, jumper" :total="attachmentListLength"
                    @size-change="handleSizeChange" @current-change="handleCurrentChange" />
            </div>
        </el-row>
    </div>

    <el-dialog v-model="lookDetailBoxVisable" title="查看附件详情" width="650">
        <el-descriptions class="margin-top" title="" :column="1" :style="{ width: `600px` }" border>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon :style="iconStyle">
                            <user />
                        </el-icon>
                        文件名
                    </div>
                </template>
                {{ lookAttachmentDetailTemp.fileName }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon :style="iconStyle">
                            <iphone />
                        </el-icon>
                        文件类型
                    </div>
                </template>
                {{ lookAttachmentDetailTemp.type }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon :style="iconStyle">
                            <location />
                        </el-icon>
                        URL
                    </div>
                </template>
                {{ lookAttachmentDetailTemp.url }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon :style="iconStyle">
                            <tickets />
                        </el-icon>
                        创建时间
                    </div>
                </template>
                {{ lookAttachmentDetailTemp.createTime }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon :style="iconStyle">
                            <office-building />
                        </el-icon>
                        上传用户
                    </div>
                </template>
                {{ lookAttachmentDetailTemp.userName }}
            </el-descriptions-item>
            <el-descriptions-item v-if="lookAttachmentDetailTemp.type === '图片'">
                <template #label>
                    <div class="cell-item">
                        <el-icon :style="iconStyle">
                            <office-building />
                        </el-icon>
                        图片预览
                    </div>
                </template>
                <el-image :src="lookAttachmentDetailTemp.url" />
            </el-descriptions-item>
        </el-descriptions>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="lookDetailBoxVisable = false">取消</el-button>
                <el-button type="primary" @click="lookDetailBoxVisable = false">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

    <el-dialog v-model="uploadBoxVisable" title="上传附件" width="500">

        <el-upload v-model:file-list="fileList" class="upload-demo" :http-request="uploadFile" :on-change="handleUploadChange">
            <el-button type="primary">点击上传</el-button>
            <template #tip>
                <div class="el-upload__tip">
                    选择文件
                </div>
            </template>
        </el-upload>
        <template #footer>
            <div class="dialog-footer">
                <el-button type="primary" @click="uploadBoxVisable = false">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>
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
