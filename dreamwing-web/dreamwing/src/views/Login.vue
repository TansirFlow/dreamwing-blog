<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
const loginData = ref({
    username: '',
    password: ''
})
const login = () => {
    ElMessage.error(`账号：${loginData.value.username}\n密码：${loginData.value.password}\n`+'登录失败')
}
</script>

<template>
    <el-container>
        <el-row class="out-box-style">
            <el-col :style="{ display: `flex`, alignItems: `center`, justifyContent: `center` }">
                <el-row class="demo-radius"
                    :style="{ border: '0px solid red', width: `500px`, height: `380px` }">
                    <el-col :span="24" align="center" :style="{ border: '0px solid red' }">
                        <div class="radius"
                            :style="{ background: `white`, width: `90%`, height: `90%`, border: '0px solid red', borderRadius: `var(--el-border-radius-base)`, boxShadow: `var(--el-box-shadow)`, }">
                            <br>
                            <br>
                            <el-row :style="{ display: `flex`, alignItems: `center`, justifyContent: `center` }">
                                <el-col :span="18">
                                    <el-text class="mx-1" :style="{ fontSize: `32px` }">DreamWing</el-text><br><br>
                                    <!--此处填写卡片内容-->
                                    <el-form :model="loginData" label-width="auto" size="large">
                                        <el-form-item>
                                            <el-input :prefix-icon="User" placeholder="请输入用户名"
                                                v-model="loginData.username"></el-input>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-input :prefix-icon="Lock" placeholder="请输入密码"
                                                v-model="loginData.password" show-password></el-input>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button type="primary" :style="{ width: `100%` }"
                                                @click="login">登录</el-button>
                                                <el-row :style="{width:`100%`}">
                                                    <el-col :span="12" :style="{textAlign:`left`}">
                                                        <el-link href="https://element-plus.org" target="_blank">注册账号</el-link>
                                                    </el-col>
                                                    <el-col :span="12" :style="{textAlign:`right`}">
                                                        <el-link href="https://element-plus.org" target="_blank">忘记密码</el-link>
                                                    </el-col>
                                                </el-row>
                                        </el-form-item>
                                    </el-form>
                                </el-col>
                            </el-row>
                        </div>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </el-container>
</template>

<style>
/* * {
    border: 1px solid red;'1px solid red'
} */

.out-box-style {
    height: 100vh;
    width: 100vw;
}

.demo-radius .title {
    color: var(--el-text-color-regular);
    font-size: 18px;
    margin: 10px 0;
}

.demo-radius .value {
    color: var(--el-text-color-primary);
    font-size: 16px;
    margin: 10px 0;
}

.demo-radius .radius {
    height: 40px;
    width: 70%;
    border: 1px solid var(--el-border-color);
    border-radius: 0;
    margin-top: 20px;
}
</style>