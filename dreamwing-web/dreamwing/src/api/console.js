import request from '@/utils/request.js'

export const getUserDetailService = () => {
    return request.get(`/user/info`);
}
