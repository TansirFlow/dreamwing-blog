import {createRouter,createWebHashHistory} from 'vue-router'
import LoginVue from '@/views/Login.vue'
import RegisterVue from '@/views/Register.vue'

const routes=[
    {path:'/login',component:LoginVue},
    {path:'/register',component:RegisterVue}
]

const router=createRouter({
    history:createWebHashHistory(),
    routes:routes
})

export default router