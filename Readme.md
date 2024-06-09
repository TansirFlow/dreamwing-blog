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
> 后端在SpringBoot框架开发，整合了MyBatis管理MySQL数据库，使用JWT令牌进行权限管理和鉴定，

> 利用Redis作为缓存服务器实现网站配置信息存储、登陆状态记录和博客文章的访问量统计，同时集成针对缓存问题的工具类来提高项目吞吐量

> 集成了Amazon S3 API依赖，直接使用外部对象存储来管理文件，项目中主要使用自建minio开源对象存储，降低存储成本

> SpringBoot集成邮件发送，使用自建Maddy Mail Server邮箱实现系统外部邮件通知的发送

使用到的技术栈
1. SpringBoot
2. MySQL
3. Redis
4. MyBatis
5. JWT
