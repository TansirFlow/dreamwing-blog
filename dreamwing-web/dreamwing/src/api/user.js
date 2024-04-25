import request from '@/utils/request.js'

export const getRegisterVerifyCodeService = (registerData) => {
    const params = new URLSearchParams()
    params.append("username", registerData.value.username)
    params.append("email", registerData.value.email)
    return request.post('/user/register/getVerifyCode', params);
}

export const userRegisterService = (registerData) => {
    return request.post('/user/register', JSON.stringify(registerData.value),{headers:{'content-type':"application/json"}});
}


export const userLoginService = (loginData) => {
    const params = new URLSearchParams()
    for (let key in loginData) {
        params.append(key, loginData[key]);
    }
    return request.post('/user/login', params)
}