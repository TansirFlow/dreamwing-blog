<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { User, Lock,Message,CircleCheck } from '@element-plus/icons-vue'
import router from '@/router'
import { getRegisterVerifyCodeService, userRegisterService } from '@/api/user';



// 注册表单数据
const registerData = ref({
    username: '',
    inputPassword: '',
    rePassword:'',
    email: '',
    verifyCode: '',
})

// 获取注册的验证码
const getRegisterVerifyCode=async ()=>{
    let result=await getRegisterVerifyCodeService(registerData);
    ElMessage.success(result.msg?result.msg:'操作成功');
}

// 注册操作
const register=async()=>{
    let result=await userRegisterService(registerData);
    ElMessage.success(result.msg?result.msg:'操作成功');
}

// 检查密码输入是否匹配（后续使用检验规则来替代）
const checkRePassword=(rule,value,callback)=>{
    if(value===''){
        callback(new Error('请再次输入密码'))
    }else if(value!==registerData.value.inputPassword){
        callback(new Error('两次密码不同'))
    }else{
        callback()
    }
}

// 表单输入校验规则
const rules={
    username:[
        {required:true,message:'请输入用户名',trigger:'blur'},
        {min:3,max:16,message:'长度为3~16位非空字符',trigger:'blur'},
    ],
    inputPassword:[
        {required:true,message:'请输入密码',trigger:'blur'},
        {min:6,max:18,message:'长度为6~18位非空字符',trigger:'blur'},
    ],
    rePassword:[
        {validator:checkRePassword,trigger:'blur'},
    ],
    email:[
        {required:true,message:'请输入邮箱',trigger:'blur'},
        {type:'email',message:'不是合法的邮箱地址',trigger:'blur'},
    ],
    verifyCode:[
        {required:true,message:'请输入验证码',trigger:'blur'}
    ]
}
</script>

<template>
    <el-container  @keyup.enter="register">
        <el-row class="out-box-style">
            <el-col :style="{ display: `flex`, alignItems: `center`, justifyContent: `center` }">
                <el-row class="demo-radius" :style="{ border: '0px solid red', width: `500px`, height: `600px` }">
                    <el-col align="center" :style="{ border: '0px solid red' }">
                        <div class="radius"
                            :style="{ background: `white`, width: `90%`, height: `90%`, border: '0px solid red', borderRadius: `var(--el-border-radius-base)`, boxShadow: `var(--el-box-shadow)`, }">
                            <br>
                            <br>
                            <el-row justify="center">
                                <el-col :span="18">
                                    <el-text class="mx-1" :style="{ fontSize: `32px` }">DreamWing</el-text><br><br>
                                    <!--此处填写卡片内容-->
                                    <el-form :model="registerData" label-width="auto" size="large" :rules="rules">
                                        <el-form-item :gutter="10" prop="username">
                                            <el-input :prefix-icon="User" placeholder="用户名" v-model="registerData.username"></el-input>
                                        </el-form-item>
                                        <el-form-item :gutter="10" prop="inputPassword">
                                            <el-input :prefix-icon="Lock" placeholder="密码" v-model="registerData.inputPassword" show-password></el-input>
                                        </el-form-item>
                                        <el-form-item :gutter="10" prop="rePassword">
                                            <el-input :prefix-icon="Lock" placeholder="确认密码" v-model="registerData.rePassword" show-password></el-input>
                                        </el-form-item>
                                        <el-form-item :gutter="10" prop="email">
                                            <el-input :prefix-icon="Message" placeholder="邮箱" v-model="registerData.email"></el-input>
                                        </el-form-item>
                                        <el-form-item prop="verifyCode">
                                            <el-row :gutter="0" :style="{ width: `100%` }" justify="space-between">
                                                <el-col :span="16" justify="start">
                                                    <el-input :prefix-icon="CircleCheck" placeholder="验证码"
                                                        v-model="registerData.verifyCode"></el-input>
                                                </el-col>
                                                <el-col :span="7" justify="end">
                                                    <el-button type="primary"
                                                        :style="{ border: `0px solid red`, width: `100%` }" @click="getRegisterVerifyCode">点击获取</el-button>
                                                </el-col>
                                            </el-row>
                                        </el-form-item>
                                        <el-form-item>
                                            <el-button type="primary" :style="{ width: `100%` }"
                                                @click="register">注册</el-button>
                                        </el-form-item>
                                        <el-form-item :style="{justifyContent: `flex-end`}">
                                            <el-row justify="center" :style="{width:`100%`}">
                                                <el-col :span="24">
                                                    <el-link href="" target="_blank" @click="router.push('/login')">已有账号？去登录-></el-link>
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