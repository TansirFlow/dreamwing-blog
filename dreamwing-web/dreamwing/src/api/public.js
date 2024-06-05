import requestPublic from '@/utils/requestPublic.js'

export const getCategoryListService = () =>{
    return requestPublic.get('/public/category')
}
export const getArticleListService = () => {
    return requestPublic.get(`/article?pageNum=1&pageSize=10`);
}
export const getArticleStatusListService = () =>{
    return requestPublic.get(`/public/article/status`);
}
export const getArticleTypeListService = () =>{
    return requestPublic.get(`/public/article/type`);
}