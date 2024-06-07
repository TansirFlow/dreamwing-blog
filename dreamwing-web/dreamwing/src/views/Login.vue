<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'
import router from '@/router'
import { userLoginService } from '@/api/user';
import { useTokenStore } from '@/stores/token.js'

const tokenStore=useTokenStore()


// 登录信息
const loginData = ref({
    username: '',
    inputPassword: '',
})


// 登录并存储token到pinia
const login =async () => {
    let result=await userLoginService(loginData);
    ElMessage.success(result.msg?result.msg:'操作成功');
    tokenStore.setToken(result.data)
    ElMessage.success(tokenStore.token);
    router.push('/')
}


// 表单输入匹配规则
const rules={
    username:[
        {required:true,message:'请输入用户名',trigger:'blur'},
        {min:3,max:16,message:'长度为3~16位非空字符',trigger:'blur'},
    ],
    inputPassword:[
        {required:true,message:'请输入密码',trigger:'blur'},
        {min:6,max:18,message:'长度为6~18位非空字符',trigger:'blur'},
    ],
}

</script>

<template>
    <el-container @keyup.enter="login">
        <el-row class="out-box-style">
            <el-col :style="{ display: `flex`, alignItems: `center`, justifyContent: `center` }">
                <el-row class="demo-radius" :style="{ border: '0px solid red', width: `500px`, height: `400px` }">
                    <el-col align="center" :style="{ border: '0px solid red' }">
                        <div class="radius"
                            :style="{ background: `white`, width: `90%`, height: `90%`, border: '0px solid red', borderRadius: `var(--el-border-radius-base)`, boxShadow: `var(--el-box-shadow)`, }">
                            <br>
                            <br>
                            <el-row justify="center">
                                <el-col :span="18">
                                    <el-text class="mx-1" :style="{ fontSize: `32px` }">DreamWing</el-text><br><br>
                                    <!--此处填写卡片内容-->
                                    <el-form :model="loginData" label-width="auto" size="large" :rules="rules">
                                        <el-form-item :gutter="10" prop="username">
                                            <el-input :prefix-icon="User" placeholder="用户名" v-model="loginData.username"></el-input>
                                        </el-form-item>
                                        <el-form-item :gutter="10" prop="inputPassword">
                                            <el-input :prefix-icon="Lock" placeholder="密码" v-model="loginData.inputPassword" show-password></el-input>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button type="primary" :style="{ width: `100%` }"
                                                @click="login">登录</el-button>
                                        </el-form-item>
                                        <el-form-item :style="{justifyContent: `flex-end`}">
                                            <el-row justify="center" :style="{width:`100%`}">
                                                <el-col :span="24">
                                                    <el-link href="" target="_blank" @click="router.push('/register')">没有账号？去注册</el-link>
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