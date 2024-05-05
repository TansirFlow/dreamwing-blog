import request from '@/utils/request.js'

export const uploadImageService = (file) => {
    console.log("准备开始")
    console.log(file)
    const formData = new FormData();
    formData.append('file', file);
    return request.post(`/attachment/upload`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}