export default {
  /**
   * @description api请求基础路径
   */
  baseUrl: {
    dev: '/api',
    pro: '/',
  },
  /**
   * @description 登录方式 1.密码登录 2.验证码登录
   */
  loginType: 1,
  /**
   * @description 加密密钥
   */
  AES_KEY: 'mithrandir',
  /**
   * @description 加密偏移量
   */
  AES_IV: 'mithrandir',
  /**
   * @description 是否开启加密传输
   */
  SECRET_FLAG: true,
  /**
   * @description 是否开启验签
   */
  SIGN_FLAG: true,
  /**
   * @description home页
   */
  homePage: {
    name: 'Home',
    path: '/home',
    icon: 'iconfont icon-home',
    title: '首页',
  },
};
