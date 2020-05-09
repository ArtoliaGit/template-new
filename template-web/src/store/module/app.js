import { getBreadCrumbListByRouter, setTagListToStorage } from '@/utils/common';
import config from '@/config';

const getDefaultState = () => ({
  menuList: [],
  resource: [],
  isCollapse: false,
  breadCrumbList: [],
  cacheList: [],
  tagList: [
    {
      path: config.homePage.path,
      name: config.homePage.name,
      title: config.homePage.title,
      isClose: false,
    },
  ],
});

export default {
  state: getDefaultState(),
  mutations: {
    setMenuList(state, menuList) {
      state.menuList = menuList;
    },
    setIsCollapse(state, isCollapse) {
      state.isCollapse = isCollapse;
    },
    setBreadCrumbList(state, breadCrumbList) {
      state.breadCrumbList = breadCrumbList;
    },
    setResource(state, resource) {
      state.resource = resource;
    },
    setCacheList(state, name) {
      state.cacheList.push(name);
    },
    setTagList(state, tagList) {
      state.tagList = tagList;
      setTagListToStorage(state.tagList);
    },
    resetAppState(state) {
      Object.assign(state, getDefaultState());
    },
  },
  actions: {
    handleBreadCrumbList({ commit, state }, paths) {
      const breadCrumbList = getBreadCrumbListByRouter(state.menuList, paths);
      breadCrumbList.unshift({
        ...config.homePage,
        to: { path: config.homePage.path },
      });
      commit('setBreadCrumbList', breadCrumbList);
    },
    handleSetMenuList({ commit }, resource) {
      commit('setResource', resource);
      const getStructData = (data, id) => {
        let children = [];
        if (data.some(item => item.parentId === id)) {
          children = data.filter(item => item.parentId === id)
            .map(item => ({ ...item, children: getStructData(data, item.id) }))
            .sort((a, b) => a.sort - b.sort);
        }
        return children;
      };
      const menuList = getStructData(resource, 0);
      commit('setMenuList', menuList);
    },
    handleCloseTag({ commit, state }, data) {
      const tags = state.tagList.slice();
      const index = state.tagList.findIndex(item => item.path === data);
      tags.splice(index, 1);
      commit('setTagList', tags);
    },
  },
};
