import request from '@/utils/request.js'
import requestPublic from '@/utils/requestPublic.js'

export const getArticleByIdService = (id) => {
    return requestPublic.get(`/article/${id}`);
}
