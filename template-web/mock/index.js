import Mock from 'mockjs';
import { login, getUserInfo } from './login';

Mock.setup({
  timeout: 1000,
});

Mock.mock(/\/login/, 'get', login);
Mock.mock(/\/getUserInfo/, 'get', getUserInfo);

export default Mock;
