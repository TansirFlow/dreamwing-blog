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


import MyInfoVue from '@/views/usercenter/MyInfo.vue'
import MyArticleVue from '@/views/usercenter/MyArticle.vue'
import UserEditArticleVue from '@/views/usercenter/EditArticle.vue'
import MyAttachmentVue from '@/views/usercenter/MyAttachment.vue'


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
            { path: '/console/editarticle/:id', name: '编辑文章2', component: ConsoleEditArticleVue },
            { path: '/console/editarticle', name: '新增文章2', component: ConsoleEditArticleVue }
        ]
    },
    {
        path: '/uc', name: 'usercenter', component: UserCenterVue, meta: { keepAlive: true }, children: [
            { path: '/uc/info', name: '个人中心', component: MyInfoVue },
            { path: '/uc/article', name: '我的文章', component: MyArticleVue },
            { path: '/uc/editarticle/:id', name: '编辑文章', component: UserEditArticleVue },
            { path: '/uc/editarticle', name: '添加文章', component: UserEditArticleVue },
            { path:'/uc/attachment',name:'我的附件',component:MyAttachmentVue }
        ]
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes: routes,

})

//路由拦截
router.beforeEach((to, from, next) => {
    //从cacheList中的任何一个页面返回，当前页面缓存
    const cacheList = to.meta.cacheList
    if (cacheList) {
        if (cacheList.indexOf(from.name) > -1) {
            to.meta.keepAlive = true
        } else {
            //如果没有纳进cacheList缓存需求，就不缓存
            if (from.name) {
                to.meta.keepAlive = false
            }
            // 导航跳转需要缓存处理
            if (from.meta.cacheList) {
                to.meta.keepAlive = true
            }
        }
    }
    next()
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