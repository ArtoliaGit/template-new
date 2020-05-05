import CryptoJS from 'crypto-js';
import config from '@/config';
import { isNotEmpty } from './tools';

/**
 * @description 加密方法
 * @param {*} data 加密对象
 * @returns 密文
 */
export function encrypt(data, aesIv = config.AES_IV) {
  if (isNotEmpty(data)) {
    const key = CryptoJS.MD5(config.AES_KEY);
    const iv = CryptoJS.MD5(aesIv);
    let str = '';
    if (typeof (data) === 'string') {
      str = data;
    } else if (typeof (data) === 'object') {
      str = JSON.stringify(data);
    }
    const encrypted = CryptoJS.AES.encrypt(str, key, {
      iv,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7,
    });
    return encrypted.toString();
  }
  return '';
}

/**
 * @description 解密方法
 * @param {string} data 密文
 * @returns 明文
 */
export function decrypt(data, aesIv = config.AES_IV) {
  if (isNotEmpty(data)) {
    const key = CryptoJS.MD5(config.AES_KEY);
    const iv = CryptoJS.MD5(aesIv);
    const base64 = CryptoJS.enc.Base64.parse(data);
    const str = CryptoJS.enc.Base64.stringify(base64);
    const bytes = CryptoJS.AES.decrypt(str, key, {
      iv,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7,
    });
    return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
  }
  return '';
}

/**
 * @description 获取MD5值
 */
export function md5(data) {
  return CryptoJS.MD5(data).toString();
}
