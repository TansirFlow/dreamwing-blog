import request from '@/utils/request.js'
import requestPublic from '@/utils/requestPublic.js'
export const getArticleListService = () => {
    return requestPublic.get(`/article?pageNum=1&pageSize=10`);
}
