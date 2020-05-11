import { login, logout, getUserInfo } from '@/api/user';
import {
  setTokenToStorage,
  clearSessionStorage,
  clearLocalStorage,
} from '@/utils/common';
import router from '@/router';

const getDefaultState = () => ({
  username: '',
  userId: '',
  token: '',
  role: null,
  roleList: [],
  personName: '',
  hasUserInfo: false,
});

export default {
  state: getDefaultState(),
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
    setRole(state, role) {
      state.role = role;
    },
    setRoleList(state, roleList) {
      state.roleList = roleList;
    },
    setPersonName(state, personName) {
      state.personName = personName;
    },
    resetUserState(state) {
      Object.assign(state, getDefaultState());
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
      commit('resetUserState');
      commit('resetAppState');
      router.push({ name: 'Login' });
    },
    /**
     * @description 获取个人信息
     */
    handleGetUserInfo({ commit, dispatch, rootState }) {
      return new Promise((resolve, reject) => {
        getUserInfo().then(res => {
          // TODO 获取个人信息
          if (res.code === 200) {
            commit('setUserId', res.data.userId);
            commit('setUsername', res.data.username);
            commit('setHasUserInfo', true);
            commit('setRoleList', res.data.roles);
            if (res.data.roles && res.data.roles.length > 0) {
              commit('setRole', res.data.roles[0]);
              if (res.data.roles[0].resource) {
                dispatch('handleSetMenuList', res.data.roles[0].resource);
              }
            }
            if (res.data.person) {
              commit('setPersonName', res.data.person.personName);
            }
            resolve();
          } else {
            reject(res);
          }
        });
      });
    },
  },
};
