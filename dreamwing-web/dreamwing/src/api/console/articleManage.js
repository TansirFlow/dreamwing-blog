import request from '@/utils/request.js'

export const getMyArticleListService = (currentPage, pageSize) => {
    return request.get(`/article?pageNum=${currentPage.value}&pageSize=${pageSize.value}`);
}

export const getMyArticleListByConditionService = (searchCondition) => {
    console.log(searchCondition.value)
    return request.post(`/article/list/admin`, JSON.stringify(searchCondition.value), { headers: { 'content-type': "application/json" } })
}

export const deleteArticleServive=(id)=>{
    return request.post(`/article/delete/admin/${id}`)
}

export const deleteArticleByIdListService=(idList)=>{
    return request.post(`/article/delete/list/admin`, JSON.stringify(idList), { headers: { 'content-type': "application/json" } })
}