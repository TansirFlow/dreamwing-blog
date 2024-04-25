//定制请求的实例

//导入axios  npm install axios
import axios from 'axios';
import { ElMessage } from 'element-plus'
import { useTokenStore } from '@/stores/token.js'
import { showFullScreenLoading,tryHideFullScreenLoading } from '@/utils/loading.js'
import router from '@/router'
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = '/api';
const instance = axios.create({ baseURL })

instance.interceptors.request.use(
    (config) => {
        showFullScreenLoading()
        const tokenStore = useTokenStore()
        if (tokenStore.token) {
            //在pinia中定义的响应式数据不需要value就可以使用
            config.headers.Authorization = tokenStore.token
        }
        return config;
    },
    (err) => {
        Promise.reject(err)
    }
)
//添加响应拦截器
instance.interceptors.response.use(
    result => {
        tryHideFullScreenLoading();
        if (result.data.code === 0) {
            return result.data;
        }
        ElMessage.error(result.data.message ? result.data.message : "操作失败")
        return Promise.reject(result.data)
    },
    err => {
        tryHideFullScreenLoading();
        if (err.response.status === 401) {
            ElMessage.error('请先登录')
            router.push('/login')
        } else {
            ElMessage.error("服务异常")
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;