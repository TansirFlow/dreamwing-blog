import request from '@/utils/request.js'

export const getMyAttachmentListService = (currentPage, pageSize) => {
    return request.get(`/article?pageNum=${currentPage.value}&pageSize=${pageSize.value}`);
}

export const getMyAttachmentListByConditionService = (searchCondition) => {
    console.log(searchCondition.value)
    return request.post(`/attachment/list`, JSON.stringify(searchCondition.value), { headers: { 'content-type': "application/json" } })
}

export const deleteAttachmentServive = (id) => {
    return request.post(`/attachment/delete/${id}`)
}

export const deleteAttachmentByIdListService = (idList) => {
    return request.post(`/attachment/delete/list`, JSON.stringify(idList), { headers: { 'content-type': "application/json" } })
}

export const getAttachmentTypeListService = () => {
    return request.get(`/attachment/typeList`)
}

export const uploadFileService = (file) => {
    console.log(file)
    const formData = new FormData();
    formData.append('file', file);
    return request.post(`/attachment/upload`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}