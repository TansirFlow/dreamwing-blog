import { createRouter, createWebHashHistory } from 'vue-router'
import LoginVue from '@/views/Login.vue'
import RegisterVue from '@/views/Register.vue'
import LayoutVue from '@/views/Layout.vue'
import ArticleVue from '@/views/Article.vue'
import ConsoleVue from '@/views/Console.vue'
import UserCenterVue from '@/views/UserCenter.vue'
import PanelVue from '@/views/console/Panel.vue'
import ArticleManageVue from '@/views/console/ArticleManage.vue'
import TalkManageVue from '@/views/console/TalkManage.vue'
import CommentManageVue from '@/views/console/CommentManage.vue'
import AttachmentManageVue from '@/views/console/AttachmentManage.vue'
import UserManageVue from '@/views/console/UserManage.vue'
import SystemSettingVue from '@/views/console/SystemSetting.vue'
import SystemAllVue from '@/views/console/SystemAll.vue'
import ConsoleEditArticleVue from '@/views/console/EditArticle.vue'

const routes = [
    { path: '/', name: 'index', component: LayoutVue, meta: { keepAlive: true } },
    { path: '/login', name: 'login', component: LoginVue, meta: { keepAlive: true } },
    { path: '/register', name: 'register', component: RegisterVue, meta: { keepAlive: true } },
    {
        path: '/article/:id',
        name: 'articleDetail',
        component: ArticleVue,
        meta: {
            keepAlive: true
        },
        // beforeEnter: (to, from) => {
        //     to.meta.keepAlive = (to.name === 'articleDetail' && from.name === 'index') ? false : true
        //     return true
        // },
    },
    {
        path: '/console', name: '后台管理', component: ConsoleVue, meta: { keepAlive: true }, children: [
            { path: '/console/panel', name: '仪表盘', component: PanelVue },
            { path: '/console/article', name: '文章管理', component: ArticleManageVue },
            { path: '/console/talk', name: '说说管理', component: TalkManageVue },
            { path: '/console/comment', name: '评论管理', component: CommentManageVue },
            { path: '/console/attachment', name: '附件管理', component: AttachmentManageVue },
            { path: '/console/user', name: '用户管理', component: UserManageVue },
            { path: '/console/setting', name: '系统设置', component: SystemSettingVue },
            { path: '/console/all', name: '系统概览', component: SystemAllVue },
            { path: '/console/editArticle', name: '编辑文章', component: ConsoleEditArticleVue }
        ]
    },
    { path: '/usercenter', name: 'usercenter', component: UserCenterVue, meta: { keepAlive: true } }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes: routes,

})

export default router

/*
{path:'/',component:LayoutVue,redirect:'/article/category',children:[
        {path:'/article/category',component:ArticleCategoryVue},
        {path:'/article/Manage',component:ArticleManageVue},
        {path:'/user/Avatar',component:UserAvatarVue},
        {path:'/user/Info',component:UserInfoVue},
        {path:'/user/ReserPassword',component:UserResetPasswordVue},
    ]}
 */