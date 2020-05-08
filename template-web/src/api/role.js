import http from '@/utils/http';

export const save = data => http.post({
  url: '/role/save',
  data,
});

export const getRoleList = params => http.get({
  url: '/role/getRoleList',
  params,
});

export const remove = params => http.get({
  url: '/role/remove',
  params,
});

export const saveResource = data => http.request({
  url: '/role/saveResource',
  data,
  method: 'post',
});
