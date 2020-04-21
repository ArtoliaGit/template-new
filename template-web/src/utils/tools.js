/**
 * 解析url参数
 * @param {String} url 地址
 */
export function getUrlParams(url) {
  const regx = new RegExp(/.*?\?\w+=\w+(&\w+=\w+)*$/);
  if (!regx.test(url)) return {};
  const subfix = url.split('?')[1];
  const params = subfix.split('&');
  const result = {};
  params.forEach(item => {
    const items = item.split('=');
    result[items[0]] = items[1];
  });
  return result;
}
