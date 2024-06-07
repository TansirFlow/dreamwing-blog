import requestPublic from '@/utils/requestPublic.js'
import request from '@/utils/request.js'
export const getCategoryListService = () => {
    return requestPublic.get('/public/category')
}//1
export const getArticleListService = () => {
    return requestPublic.get(`/public/article`
        , {
            params: {
                pageNum: '1',
                pageSize: '10'
            }
        }
    );
}
export const getArticleDetailService=(id)=>{
    return requestPublic.get(`/public/article/${id}`);
}
export const getArticleStatusListService = () => {
    return requestPublic.get(`/public/article/status`);
}
export const getArticleTypeListService = () => {
    return requestPublic.get(`/public/article/type`);
}