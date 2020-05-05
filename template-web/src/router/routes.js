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
        path: 'link',
        name: 'Link',
        meta: {
          title: '第三方页面',
          icon: 'el-icon-eleme',
        },
        component: () => import('@/views/link'),
      },
    ],
  },
];

export default routes;
