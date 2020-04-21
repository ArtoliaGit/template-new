import config from '@/config';
import HttpRequest from './axios';

const baseUrl = process.env.NODE_ENV === 'production'
  ? config.baseUrl.pro
  : config.baseUrl.dev;

const http = new HttpRequest(baseUrl);
export default http;
