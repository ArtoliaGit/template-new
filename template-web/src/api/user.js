import http from '@/utils/http';

/**
 * @description 登录接口
 */
export const login = params => http.get({
  url: '/login',
  params,
});

/**
 * @description 登出接口
 */
export const logout = params => http.get({
  url: '/logout',
  params,
});

/**
 * @description 获取个人信息
 */
export const getUserInfo = params => http.get({
  url: '/getUserInfo',
  params,
});
