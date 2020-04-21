import Vue from 'vue';
import VueRouter from 'vue-router';
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';
import store from '@/store';
import {
  getTokenFromStorage,
} from '@/utils/common';
import routes from './routes';

Vue.use(VueRouter);

NProgress.configure({ showSpinner: false });

const router = new VueRouter({
  routes,
});

router.beforeEach((to, from, next) => {
  NProgress.start();
  const token = getTokenFromStorage();
  if (token && to.name === 'Login') {
    next('/');
  } else if (token) {
    next();
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
