/**
 * @description 从sessionStorage中获取token
 * @returns token
 */
export function getTokenFromStorage() {
  return sessionStorage.getItem('token');
}

/**
 * @description 存储token到sessionStorage中
 * @param {string} token
 */
export function setTokenToStorage(token) {
  sessionStorage.setItem('token', token);
}

/**
 * @description 从sessionStorage中移除token
 */
export function removeTokenFromStorage() {
  sessionStorage.removeItem('token');
}

/**
 * @description 从sessionStorage中获取对象
 * @param {string} key 键值
 * @returns object
 */
export function getObjFromSessionStorage(key) {
  const value = sessionStorage.getItem(key);
  try {
    return JSON.parse(value);
  } catch (e) {
    return value;
  }
}

/**
 * @description 存储值到sessionStorage中
 * @param {string} key 键值
 * @param {*} value 值
 */
export function setObjToSessionStorage(key, value) {
  if (typeof value === 'object') {
    sessionStorage.setItem(key, JSON.stringify(value));
  } else {
    sessionStorage.setItem(key, value);
  }
}

/**
 * @description 从sessionStorage中移除
 * @param {string} key 键值
 */
export function removeObjFromSessionStorage(key) {
  sessionStorage.removeItem(key);
}

/**
 * @description 清空sessionStorage
 */
export function clearSessionStorage() {
  sessionStorage.clear();
}

/**
 * @description 从localStorage中获取对象
 * @param {string} key 键值
 * @returns object
 */
export function getObjFromLocalStorage(key) {
  const value = localStorage.getItem(key);
  try {
    return JSON.parse(value);
  } catch (e) {
    return value;
  }
}

/**
 * @description 存储值到localStorage中
 * @param {string} key 键值
 * @param {*} value 值
 */
export function setObjToLocalStorage(key, value) {
  if (typeof value === 'object') {
    localStorage.setItem(key, JSON.stringify(value));
  } else {
    localStorage.setItem(key, value);
  }
}

/**
 * @description 从localStorage中移除
 * @param {string} key 键值
 */
export function removeObjFromLocalStorage(key) {
  localStorage.removeItem(key);
}

/**
 * @description 清空localStorage
 */
export function clearLocalStorage() {
  localStorage.clear();
}
