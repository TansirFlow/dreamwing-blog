import request from '@/utils/request.js'
import requestPublic from '@/utils/requestPublic.js'

export const getArticleListService = () => {
    return requestPublic.get(`/article?pageNum=1&pageSize=10`);
}
export const getCategoryListService = () =>{
    return requestPublic.get('/category/list')
}
export const getUserDetailService = () => {
    return request.get(`/user/info`);
}
