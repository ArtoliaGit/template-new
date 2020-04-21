import { getUrlParams } from '../src/utils/tools';

export const login = req => {
  console.log(req);
  console.log(getUrlParams(req.url));
  return {
    code: 200,
    message: '登录成功',
    data: {
      userId: 1,
      token: 'abc',
    },
  };
};

export const getUserInfo = req => {
  return {
    code: 200,
    message: '获取个人信息成功',
    data: {
      userId: 1,
    },
  };
};
