import { getBreadCrumbListByRouter } from '@/utils/common';
import routes from '@/router/routes';
import config from '@/config';

export default {
  state: {
    menuList: [],
    isCollapse: false,
    breadCrumbList: [],
  },
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
  },
  actions: {
    handleBreadCrumbList({ commit }, paths) {
      const breadCrumbList = getBreadCrumbListByRouter(routes, paths);
      breadCrumbList.unshift({
        ...config.homePage,
        to: { path: config.homePage.path },
      });
      commit('setBreadCrumbList', breadCrumbList);
    },
  },
};
