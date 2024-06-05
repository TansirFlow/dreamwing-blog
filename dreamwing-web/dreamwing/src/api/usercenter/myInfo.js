import request from '@/utils/request.js'


export const getUserDetailService = () => {
    return request.get(`/user/info`);
}

export const changePwdService = (changePwdData) => {
    console.log(changePwdData.value)
    return request.post('/user/changePwd',JSON.stringify(changePwdData.value), { headers: { 'content-type': "application/json" } })
}