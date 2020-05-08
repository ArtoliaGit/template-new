import http from '@/utils/http';

export const save = data => http.post({
  url: '/menu/save',
  data,
});

export const getList = params => http.get({
  url: '/menu/getList',
  params,
});

export const remove = params => http.get({
  url: '/menu/remove',
  params,
});
