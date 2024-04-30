import request from '@/utils/request.js'

export const getArticleByIdService = (id) => {
    return request.get(`/article/${id}`);
}
