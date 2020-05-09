import Layout from '@/components/layout';
import config from '@/config';

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login'),
  },
  {
    path: '/',
    name: 'Layout',
    redirect: config.homePage.path,
    component: Layout,
    meta: {
      hideInMenu: true,
    },
    children: [
      {
        path: config.homePage.path,
        name: config.homePage.name,
        meta: {
          title: config.homePage.title,
          hideInMenu: true,
          cache: true,
          icon: config.homePage.icon,
        },
        component: () => import('@/views/home'),
      },
    ],
  },
  {
    path: '/system',
    name: 'System',
    component: Layout,
    children: [
      {
        path: 'menuManage',
        name: 'MenuManage',
        meta: {
          title: '菜单管理',
          icon: 'el-icon-eleme',
        },
        component: () => import('@/views/menu'),
      },
      {
        path: 'link/:id',
        name: 'Link',
        meta: {
          title: '其他页面',
          icon: 'el-icon-eleme',
        },
        component: () => import('@/views/link'),
      },
      {
        path: 'user',
        name: 'User',
        meta: {
          title: '用户管理',
          icon: 'el-icon-eleme',
        },
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/user'),
      },
      {
        path: 'userEdit',
        name: 'UserEdit',
        meta: {
          title: '用户编辑',
          icon: 'el-icon-eleme',
          hideInMenu: true,
        },
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/user-edit'),
      },
      {
        path: 'role',
        name: 'Role',
        meta: {
          title: '角色管理',
          icon: 'el-icon-eleme',
          cache: true,
        },
        component: () => import(/* webpackChunkName: "role" */ '@/views/role'),
      },
    ],
  },
  {
    path: '/401',
    name: 'error_401',
    meta: {
      hideInMenu: true,
    },
    component: () => import(/* webpackChunkName: "401" */ '@/views/error-page/401'),
  },
  {
    path: '/500',
    name: 'error_500',
    meta: {
      hideInMenu: true,
    },
    component: () => import(/* webpackChunkName: "500" */ '@/views/error-page/500'),
  },
  {
    path: '*',
    name: 'error_404',
    meta: {
      hideInMenu: true,
    },
    component: () => import(/* webpackChunkName: "404" */ '@/views/error-page/404'),
  },
];

export default routes;
