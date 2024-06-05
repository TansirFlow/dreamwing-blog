<script setup>
import { reactive, computed, ref } from 'vue'
import {
    Iphone,
    Location,
    OfficeBuilding,
    Tickets,
    User,
} from '@element-plus/icons-vue'
import { changePwdService,getUserDetailService } from '@/api/usercenter/myInfo'


const iconStyle = computed(() => {
    const marginMap = {
        large: '8px',
        default: '6px',
        small: '4px',
    }
    return {
        marginRight: marginMap['large'] || marginMap.default,
    }
})
const size = ref('large')
const blockMargin = computed(() => {
    const marginMap = {
        large: '32px',
        default: '28px',
        small: '24px',
    }
    return {
        marginTop: marginMap[size.value] || marginMap.default,
    }
})


const changePwdData = ref({
    "old_pwd": "",
    "new_pwd": "",
    "repeat_new_pwd": ""
})

const confirmChangePwd = async () => {
    let result = await changePwdService(changePwdData)
}

const dialogFormVisible = ref(false)
const formLabelWidth = '140px'

const updateInfoData=ref({
    "nickname":"",
    "email":"",
    "signature":""
})

const openUpdateInfoWindow=()=>{
    dialogFormVisible.value=true
    updateInfoData.value.nickname=userDetailInfo.value.nickname
    updateInfoData.value.email=userDetailInfo.value.email
    updateInfoData.value.signature=userDetailInfo.value.signature
}

const userDetailInfo=ref({})

const getUserDetail=async ()=>{
    let result=await getUserDetailService()
    console.log("result",result)
    userDetailInfo.value=result.data
    console.log("userDetailInfo",userDetailInfo)
}

async function userDetail() {// markdown-获取内容
    await getUserDetail()// axios获取内容
}
userDetail()

</script>
<template>
    <el-row :style="{ width: `100%`, height: `100%`, border: `0px solid red` }">
        <el-row :style="{ width: `100%`, height: `80px`, border: `0px solid red` }">
            <el-avatar :size="80" />
            <el-text>{{ userDetailInfo.nickname }}<br>@{{ userDetailInfo.username }}</el-text>
        </el-row>
        <el-row :style="{ width: `100%`, height: `85%`, border: `0px solid red` }">
            <el-tabs type="border-card" :style="{ width: `100%` }">
                <el-tab-pane label="详情">
                    <el-descriptions class="margin-top" title="我的信息" :column="1" :style="{ width: `600px` }" border>
                        <template #extra>
                            <el-button type="primary" @click="openUpdateInfoWindow">修改信息</el-button>
                        </template>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    <el-icon :style="iconStyle">
                                        <user />
                                    </el-icon>
                                    昵称
                                </div>
                            </template>
                            {{ userDetailInfo.nickname }}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    <el-icon :style="iconStyle">
                                        <iphone />
                                    </el-icon>
                                    用户名
                                </div>
                            </template>
                            {{ userDetailInfo.username }}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    <el-icon :style="iconStyle">
                                        <location />
                                    </el-icon>
                                    邮箱
                                </div>
                            </template>
                            {{ userDetailInfo.email }}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    <el-icon :style="iconStyle">
                                        <tickets />
                                    </el-icon>
                                    个性签名
                                </div>
                            </template>
                            {{ userDetailInfo.signature }}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    <el-icon :style="iconStyle">
                                        <office-building />
                                    </el-icon>
                                    角色
                                </div>
                            </template>
                            {{ userDetailInfo.role }}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    <el-icon :style="iconStyle">
                                        <office-building />
                                    </el-icon>
                                    注册时间
                                </div>
                            </template>
                            {{ userDetailInfo.createTime }}
                        </el-descriptions-item>
                    </el-descriptions></el-tab-pane>
                <el-tab-pane label="修改密码">
                    <el-form :model="changePwdData" label-width="auto" style="max-width:640px">
                        <el-form-item label="旧密码">
                            <el-input v-model="changePwdData.old_pwd" type="password"></el-input>
                        </el-form-item>
                        <el-form-item label="新密码">
                            <el-input v-model="changePwdData.new_pwd" type="password"></el-input>
                        </el-form-item>
                        <el-form-item label="重复新密码">
                            <el-input v-model="changePwdData.repeat_new_pwd" type="password"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="confirmChangePwd">修改</el-button>
                        </el-form-item>
                    </el-form>

                </el-tab-pane>
                <el-tab-pane label="双因素认证">双因素认证</el-tab-pane>
            </el-tabs>

        </el-row>

    </el-row>


    <el-dialog v-model="dialogFormVisible" title="更新个人信息" width="500">
        <el-form :model="updateInfoData">
            <el-form-item label="昵称" :label-width="formLabelWidth">
                <el-input v-model="updateInfoData.nickname" autocomplete="off" />
            </el-form-item>
            <el-form-item label="邮箱" :label-width="formLabelWidth">
                <el-input v-model="updateInfoData.email" autocomplete="off" />
            </el-form-item>
            <el-form-item label="个性签名" :label-width="formLabelWidth">
                <el-input v-model="updateInfoData.signature" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="dialogFormVisible = false">
                    确定
                </el-button>
            </div>
        </template>
    </el-dialog>

</template>
<style>
.el-descriptions {
    margin-top: 20px;
}

.cell-item {
    display: flex;
    align-items: center;
}

.margin-top {
    margin-top: 20px;
}
</style>
