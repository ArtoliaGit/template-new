import { MessageBox } from 'element-ui';

const beforeClose = {
  before_close_normal: (resolve) => {
    MessageBox.confirm('确定要关闭这一页吗', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }).then(() => {
      resolve(true);
    }).catch(() => {
      resolve(false);
    });
  },
};

export default beforeClose;
