import request from '@/utils/request.js'
import requestPublic from '@/utils/requestPublic.js'

export const addCommentSelfService=(newCommentSelfData)=>{
    return request.post("/comment/add", JSON.stringify(newCommentSelfData), { headers: { 'content-type': "application/json" } })
}
