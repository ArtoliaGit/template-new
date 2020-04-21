import axios from 'axios';
import dayjs from 'dayjs';
import { Message } from 'element-ui';
import { getTokenFromStorage } from '@/utils/common';

class HttpRequest {
  constructor(baseUrl = '/') {
    this.baseUrl = baseUrl;
  }

  /**
   * @description 初始化配置
   */
  getInitConfig() {
    const config = {
      baseUrl: this.baseUrl,
      timeout: 1000,
      headers: {
        // TODO 加入请求头配置
      },
    };
    return config;
  }

  interceptors(instance) {
    /**
     * @description 请求拦截
     */
    instance.interceptors.request.use((config) => {
      // 请求时加上token
      const token = getTokenFromStorage();
      if (token) {
        config.headers['X-Token'] = token;
      }
      return config;
    }, (error) => {
      console.log(error);
      return Promise.reject(error);
    });

    /**
     * @description 响应拦截
     */
    instance.interceptors.response.use((res) => {
      const { data, status } = res;
      return data;
    }, (error) => {
      const errorInfo = error.response;
      Message({
        message: '服务错误',
        type: 'error',
        duration: 5 * 1000,
      });
      this.addErrorLog(errorInfo);
      return Promise.reject(error);
    });
  }

  /**
   * @description 记录错误日志
   * @param {Object} errorInfo 错误信息
   */
  addErrorLog(errorInfo) {
    const {
      statusText, status, request: { responseURL }, config,
    } = errorInfo;
    const info = {
      method: config.method,
      date: dayjs().format('YYYY-MM-DD HH:mm:ss'),
      code: status,
      message: statusText,
      url: responseURL,
      data: config.data && JSON.parse(config.data),
      params: config.params,
    };
    console.log(info);
  }

  request(options) {
    const instance = axios.create();
    options = { ...this.getInitConfig(), ...options };
    this.interceptors(instance);
    return instance(options);
  }

  get(options) {
    options.method = 'get';
    return this.request(options);
  }

  post(options) {
    options.method = 'post';
    return this.request(options);
  }
}

export default HttpRequest;
