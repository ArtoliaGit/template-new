import http from '@/utils/http';

/**
 * @description 登录接口
 */
export const login = data => http.post({
  url: '/authentication/login',
  data,
});

/**
 * @description 登出接口
 */
export const logout = params => http.get({
  url: '/authentication/logout',
  params,
});

/**
 * @description 获取个人信息
 */
export const getUserInfo = params => http.get({
  url: '/user/getUserInfo',
  params,
});

export const getUserList = params => http.request({
  url: '/user/getList',
  params,
  method: 'get',
});

export const save = data => http.request({
  url: '/user/save',
  data,
  method: 'post',
});

export const remove = params => http.request({
  url: '/user/remove',
  params,
  method: 'get',
});

export const resetPassword = data => http.request({
  url: '/user/resetPassword',
  data,
  method: 'post',
});

export const getUserById = params => http.request({
  url: '/user/getUserById',
  params,
  method: 'get',
});
