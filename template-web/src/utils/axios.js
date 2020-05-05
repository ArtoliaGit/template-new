import axios from 'axios';
import dayjs from 'dayjs';
import _ from 'lodash';
import { Message } from 'element-ui';
import { getTokenFromStorage } from '@/utils/common';
import { encrypt, decrypt, md5 } from '@/utils/encrypt';
import globalConfig from '@/config';
import store from '@/store';

class HttpRequest {
  constructor(baseUrl = '/') {
    this.baseUrl = baseUrl;
  }

  /**
   * @description 初始化配置
   */
  getInitConfig() {
    const config = {
      baseURL: this.baseUrl,
      timeout: 30000,
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
      const { data } = res;
      if (globalConfig.SECRET_FLAG && data.data) {
        data.data = decrypt(data.data);
      }
      console.log(res.config.url, data);
      return data;
    }, (error) => {
      const errorInfo = error.response;
      if (errorInfo.status === 404) {
        Message.error(`接口${errorInfo.config.url}没有找到`);
      } else if (errorInfo.status >= 500) {
        Message({
          message: '服务错误',
          type: 'error',
          duration: 5 * 1000,
        });
      } else if (errorInfo.status === 403) {
        store.dispatch('handleLogout');
        Message.error('请重新登录');
      }

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
      data: config.data,
      params: config.params,
    };
    console.log(info);
  }

  request(options) {
    const instance = axios.create();

    options = { ...this.getInitConfig(), ...options };

    // 加密post请求的数据
    if (globalConfig.SECRET_FLAG && options.method === 'post' && options.data) {
      const secretData = encrypt(options.data);
      options.data = {
        secretData,
      };
    }

    // 生成签名
    if (globalConfig.SIGN_FLAG) {
      const timeStamp = new Date().getTime();
      const token = getTokenFromStorage();
      let params = options.params || '';
      let data = options.data || '';
      if (params) {
        params = Object.entries(params).map(item => `${item[0]}=${item[1]}`).join('&');
      }
      if (data && typeof (data) === 'object') {
        data = JSON.stringify(data);
      }
      const signStr = md5(`${token}${params}${data}${timeStamp}`);
      options = _.merge(options, {
        headers: {
          timeStamp,
          sign: signStr,
        },
      });
    }

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
