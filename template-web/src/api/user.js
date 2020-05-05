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
