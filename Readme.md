# DreamWing博客
> 整体采用Vue+SpringBoot前后端分离模式
## 前端
> 前端以Vue3为核心，部署在Nginx服务器上反向代理转发请求，单页面应用中采用VueRouter路由，Pinia作为存储库，Axios作为HTTP请求库，调用后端提供的Restful风格接口实现通信，使用byteMD组件库用于博客文章的编辑与渲染
使用到的技术栈
1. Vue3
2. Element Plus
3. Vue Router
4. axios

## 后端
> 后端在SpringBoot的基础上，整合了Mybatis管理数据库，使用JWT令牌进行权限管理和鉴定，利用redis作为缓存服务器，同时在代码中集成了针对缓存问题的工具类，提高项目吞吐量
使用到的技术栈
1. SpringBoot
2. MySQL
3. Redis
4. MyBatis
5. JWT
