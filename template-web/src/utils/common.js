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

/**
 * 从路由中获取面包屑列表
 * @param {Array} routes 路由列表
 * @param {Array} paths 选择的菜单路径
 */
export function getBreadCrumbListByRouter(routes, paths) {
  const getBreadCrumb = (routesVal, pathsVal) => {
    let breadCrumbList = [];
    routesVal.forEach((item) => {
      if (pathsVal.includes(item.path)
        && !item.hideInBread) {
        breadCrumbList.push({
          name: item.name,
          path: item.path,
          icon: item.icon || '',
          title: item.title || '',
        });
        if (item.children && item.children.length > 0) {
          breadCrumbList = breadCrumbList.concat(getBreadCrumb(item.children, pathsVal));
        }
      }
    });
    return breadCrumbList;
  };
  return getBreadCrumb(routes, paths);
}

/**
 * 存储tagList到sessionStorage
 * @param {Array} tagList
 */
export function setTagListToStorage(tagList) {
  sessionStorage.setItem('tagList', JSON.stringify(tagList));
}

/**
 * 从sessionStorage获取tagList
 */
export function getTagListByStorage() {
  const tagList = sessionStorage.getItem('tagList');
  if (tagList) {
    return JSON.parse(tagList);
  }
  return [];
}
