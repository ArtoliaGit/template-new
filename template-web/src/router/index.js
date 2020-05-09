import Vue from 'vue';
import VueRouter from 'vue-router';
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';
import store from '@/store';
import {
  getTokenFromStorage,
  setTokenToStorage,
} from '@/utils/common';
import routes from './routes';

Vue.use(VueRouter);

NProgress.configure({ showSpinner: false });

const router = new VueRouter({
  routes,
});

const turnTo = (to, next) => {
  const resource = store.state.app.resource.find(item => to.path === item.path);
  if (resource || to.meta.hideInMenu) {
    if (resource) {
      to.meta.link = resource.link;
      to.meta.cache = resource.cache || to.meta.cache;
      to.meta.hideInBread = resource.hideInBread || to.meta.hideInBread;
      to.meta.hideInMenu = resource.hideInMenu || to.meta.hideInMenu;
      to.meta.title = resource.title || to.meta.title;
    }
    if (to.meta.cache) {
      store.commit('setCacheList', to.name);
    }
    next();
  } else {
    next({ replace: true, name: 'error_401' });
  }
};

router.beforeEach((to, from, next) => {
  NProgress.start();
  const token = getTokenFromStorage();
  if (token && to.name === 'Login') {
    next('/');
    if (from.name === 'Home') {
      NProgress.done();
    }
  } else if (token && !store.state.user.hasUserInfo) {
    store.dispatch('handleGetUserInfo').then(res => {
      turnTo(to, next);
    }).catch(() => {
      setTokenToStorage('');
      next({ name: 'login' });
    });
  } else if (token) {
    turnTo(to, next);
  } else if (to.name !== 'Login') {
    next('/login');
  } else if (to.name === 'Login') {
    next();
  }
});

router.afterEach(() => {
  NProgress.done();
});

export default router;
