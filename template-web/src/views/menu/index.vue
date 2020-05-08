<template>
  <div class="menuManage">
    <el-card :body-style="{ height: '100%' }">
      <el-row style="height: 100%;">
        <el-col :span="8" class="left-content">
          <div class="title">菜单维护</div>
          <el-tree
            ref="tree"
            :data="treeData"
            :props="defaultProps"
            :expand-on-click-node="false"
            :highlight-current="true"
            v-loading="loading"
            @node-contextmenu="handleContextmenu"
            @node-click="handleNodeClick"
          />
        </el-col>
        <el-col :span="16" class="right-content">
          <div class="title">菜单详情</div>
          <el-form
            ref="form"
            :model="form"
            :rules="rules"
            label-width="100px"
            label-position="right"
            size="small"
            style="width: 100%; margin-top: 50px;"
          >
            <el-row>
              <el-col :span="12">
                <el-form-item label="上级菜单" prop="parentName">
                  <el-input v-model="form.parentName" disabled placeholder="" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="菜单名" prop="title">
                  <el-input v-model="form.title" placeholder="" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="菜单编码" prop="name">
                  <el-input v-model="form.name" placeholder="" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="菜单路径" prop="path">
                  <el-input v-model="form.path" placeholder="" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="菜单图标" prop="icon">
                  <el-input v-model="form.icon" placeholder="" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="第三方链接" prop="link">
                  <el-input v-model="form.link" placeholder="" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="排序" prop="sort">
                  <el-input v-model="form.sort" placeholder="" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否激活" prop="activite">
                  <el-switch
                    v-model="form.activite"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    :active-value="1"
                    :inactive-value="0"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item align="right">
              <el-button type="primary" @click="submit">保存</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-card>
    <context-menu
      :target="contextMenuTarget"
      :menu-list="contextMenuList"
      @menu-click="handleMenuClick"
    />
  </div>
</template>

<script>
import ContextMenu from '@/components/context-menu';
import { save, getList, remove } from '@/api/menu';

export default {
  name: 'MenuManage',
  components: {
    ContextMenu,
  },
  data() {
    const checkNumber = (rule, value, callback) => {
      if (value !== undefined && value !== null) {
        const regx = new RegExp(/^([0-9]|[1-9][0-9]+)$/);
        if (!regx.test(String(value))) {
          callback('请输入数字');
        }
      }
      callback();
    };
    return {
      contextMenuTarget: null,
      contextMenuList: [
        { value: 'a', label: '添加子菜单' },
        { value: 'b', label: '添加主菜单' },
        { value: 'c', label: '删除菜单' },
      ],
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'title',
      },
      form: {
        id: '',
        parentId: 0,
        parentName: '',
        title: '',
        name: '',
        path: '',
        icon: '',
        link: '',
        sort: '',
        activite: 1,
        create_time: '',
      },
      rules: {
        title: [{ required: true, message: '不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '不能为空', trigger: 'blur' }],
        path: [{ required: true, message: '不能为空', trigger: 'blur' }],
        sort: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { validator: checkNumber, trigger: 'change' },
        ],
      },
      currentNode: null,
      loading: false,
    };
  },
  mounted() {
    this.contextMenuTarget = this.$refs.tree.$el;
    this.getTreeData();
  },
  methods: {
    handleContextmenu(e, val, node, comp) {
      this.currentNode = val;
    },
    handleNodeClick(val, node, comp) {
      Object.keys(this.form).forEach(item => {
        this.form[item] = val[item];
      });
    },
    handleMenuClick(val) {
      if (val === 'a') {
        if (this.treeData.length === 0) {
          this.$message.info('请先添加主菜单');
        }
        this.resetForm();
        this.form.parentId = this.currentNode.id;
        this.form.parentName = this.currentNode.title;

        if (!this.currentNode.children || this.currentNode.children.length === 0) {
          this.form.sort = 1;
        } else {
          this.form.sort = Math.max(...this.currentNode.children.map(item => item.sort)) + 1;
        }
      } else if (val === 'b') {
        this.resetForm();
        if (this.treeData.length === 0) {
          this.form.sort = 1;
        } else {
          this.form.sort = Math.max(...this.treeData.map(item => item.sort)) + 1;
        }
      } else if (val === 'c') {
        this.removeNode();
      }
    },
    resetForm() {
      this.form = {
        id: '',
        parentId: 0,
        parentName: '',
        title: '',
        name: '',
        path: '',
        icon: '',
        link: '',
        sort: '',
        activite: 1,
        create_time: '',
      };
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
      });
    },
    getTreeData() {
      const params = {
        activite: 1,
      };
      this.loading = true;
      getList(params).then(res => {
        this.loading = false;
        if (res.code === 200) {
          this.treeData = this.getStructData(res.data, 0);
        } else {
          this.treeData = [];
        }
      });
    },
    getStructData(data, id) {
      let children = [];
      if (data.some(item => item.parentId === id)) {
        children = data.filter(item => item.parentId === id)
          .map(item => ({ ...item, children: this.getStructData(data, item.id) }))
          .sort((a, b) => a.sort - b.sort);
      }
      return children;
    },
    submit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          save(this.form).then(res => {
            if (res.code === 200) {
              this.$message.success('保存成功');
              this.form.id = res.data.id;
              this.getTreeData();
            } else {
              this.$message.error('保存失败');
            }
          });
        }
      });
    },
    removeNode() {
      this.$confirm('是否删除菜单？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        remove({ id: this.currentNode.id }).then(res => {
          if (res.code === 200) {
            this.getTreeData();
            this.$message.success('删除成功');
          } else {
            this.$message.error('删除失败');
          }
        });
      }).catch(() => {});
    },
  },
};
</script>

<style lang="scss" scoped>
.menuManage {
  height: 100%;
  .el-card {
    height: 100%;
    .left-content {
      height: 100%;
      border-right: 1px solid #eee;
      padding: 10px;
    }
    .right-content {
      padding: 10px;
    }
  }
  .title {
    border-left: 4px solid #409eff;
    padding-left: 10px;
    font-size: 1.25em;
    margin-bottom: 10px;
  }
}
</style>
