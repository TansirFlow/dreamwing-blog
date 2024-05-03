import request from '@/utils/request.js'

export const getArticleListService = () => {
    return request.get(`/article?pageNum=1&pageSize=10`);
}
