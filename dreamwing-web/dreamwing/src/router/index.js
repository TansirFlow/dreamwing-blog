import {createRouter,createWebHashHistory} from 'vue-router'
import LoginVue from '@/views/Login.vue'

const routes=[
    {path:'/login',component:LoginVue},
]

const router=createRouter({
    history:createWebHashHistory(),
    routes:routes
})

export default router