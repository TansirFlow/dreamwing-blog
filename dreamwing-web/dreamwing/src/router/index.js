import { createRouter, createWebHashHistory } from 'vue-router'
import LoginVue from '@/views/Login.vue'
import RegisterVue from '@/views/Register.vue'
import LayoutVue from '@/views/Layout.vue'
import ArticleVue from '@/views/Article.vue'

const routes = [
    { path: '/', name:'index' ,component: LayoutVue, meta: { keepAlive: true } },
    { path: '/login',name:'login' ,component: LoginVue, meta: { keepAlive: true } },
    { path: '/register',name:'register' , component: RegisterVue, meta: { keepAlive: true } },
    {
        path: '/article/:id',
        name:'articleDetail',
        component: ArticleVue,
        meta: {
            keepAlive: true
        },
        // beforeEnter: (to, from) => {
        //     to.meta.keepAlive = (to.name === 'articleDetail' && from.name === 'index') ? false : true
        //     return true
        // },
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes: routes,

})

export default router