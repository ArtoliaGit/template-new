import { login, logout, getUserInfo } from '@/api/user';

export default {
  state: {
    username: '',
    userId: '',
    token: '',
  },
  mutations: {
    setUsername(state, username) {
      state.username = username;
    },
    setUserId(state, userId) {
      state.userId = userId;
    },
    setToken(state, token) {
      state.token = token;
    },
  },
  actions: {
    /**
     * @description 登录
     */
    handleLogin({ commit }, { username, password }) {
      return new Promise((resolve, reject) => {
        login({ username, password }).then(res => {
          console.log({ res });
          if (res.code === 200) {
            console.log('登录成功');
            commit('setToken', res.data.token);
            resolve(res);
          } else {
            reject(res);
          }
        }).catch(e => {
          console.log('登录失败');
          reject(e);
        });
      });
    },
    /**
     * @description 退出登录
     */
    handleLogout({ commit }) {
      console.log('退出登录');
      commit('setToken', '');
    },
    /**
     * @description 获取个人信息
     */
    handleGetUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getUserInfo().then(res => {
          // TODO 获取个人信息
        });
      });
    },
  },
};
