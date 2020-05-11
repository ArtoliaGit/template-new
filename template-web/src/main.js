import Vue from 'vue';
import ElementUI from 'element-ui';
import _ from 'lodash';
import dayjs from 'dayjs';

import './styles/element-variables.scss'; // 引入element样式文件
import './styles/index.scss';
import '@/assets/icon-font/iconfont.css'; // 阿里图标库

import App from './App.vue';
import router from './router';
import store from './store';
import config from './config';

/* eslint global-require: "off" */
// if (process.env.NODE_ENV !== 'production') require('../mock');

Vue.prototype.$config = config;
Vue.prototype.$dayjs = dayjs;

Vue.use(ElementUI, {
  size: 'small',
});
Vue.use(_);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
