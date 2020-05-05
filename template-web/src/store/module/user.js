import { login, logout, getUserInfo } from '@/api/user';
import {
  setTokenToStorage,
  clearSessionStorage,
  clearLocalStorage,
} from '@/utils/common';
import router from '@/router';

export default {
  state: {
    username: '',
    userId: '',
    token: '',
    hasUserInfo: false,
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
      setTokenToStorage(token);
    },
    setHasUserInfo(state, hasUserInfo) {
      state.hasUserInfo = hasUserInfo;
    },
  },
  actions: {
    /**
     * @description 登录
     */
    handleLogin({ commit }, { username, password }) {
      return new Promise((resolve, reject) => {
        login({ username, password }).then(res => {
          if (res.code === 200) {
            commit('setToken', res.data);
            resolve(res);
          } else {
            reject(res);
          }
        }).catch(e => {
          reject(e);
        });
      });
    },
    /**
     * @description 退出登录
     */
    handleLogout({ commit }) {
      commit('setToken', '');
      clearSessionStorage();
      clearLocalStorage();
      router.push({ name: 'Login' });
    },
    /**
     * @description 获取个人信息
     */
    handleGetUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getUserInfo().then(res => {
          // TODO 获取个人信息
          if (res.code === 200) {
            commit('setUserId', res.userId);
            commit('setUsername', res.username);
            commit('setHasUserInfo', true);
            resolve();
          } else {
            reject(res);
          }
        });
      });
    },
  },
};
