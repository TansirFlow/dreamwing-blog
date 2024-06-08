import request from '@/utils/request.js'

export const getMyArticleListService = (currentPage, pageSize) => {
    return request.get(`/article?pageNum=${currentPage.value}&pageSize=${pageSize.value}`);
}

export const getMyArticleListByConditionService = (searchCondition) => {
    console.log(searchCondition.value)
    return request.post(`/article/list`, JSON.stringify(searchCondition.value), { headers: { 'content-type': "application/json" } })
}

export const deleteArticleServive=(id)=>{
    return request.post(`/article/delete/${id}`)
}

export const deleteArticleByIdListService=(idList)=>{
    return request.post(`/article/delete/list`, JSON.stringify(idList), { headers: { 'content-type': "application/json" } })
}