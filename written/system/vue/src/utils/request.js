import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";

const request = axios.create({
    baseURL: import.meta.env.VITE_BASE_URL,
    timeout: 30000
})

request.interceptors.request.use(
    config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8'
    let user = JSON.parse(localStorage.getItem("xm-user") || '{}')
    config.headers['token'] = user.token || ''
    return config
    },
    error => {
    return Promise.reject(error)
    }
)

request.interceptors.response.use(
    response => {
        let res = response.data
        if (response.config.responseType === 'blob') {
            return res
        }
        if (res.code === '401') {
            ElMessage.error(res.message)
            router.push('/login')
        }
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res
        }
        return res
    },
    error => {
        if (error.response.status === 404) {
            ElMessage.error('请求接口未找到')
        } else if (error.response.status === 500) {
            ElMessage.error('系统错误')
        } else {
            console.error(error.message)
        }
        return Promise.reject(error)
    }
)

export default request