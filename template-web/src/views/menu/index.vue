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
            @node-contextmenu="handleContextmenu"
          />
        </el-col>
        <el-col :span="16" class="right-content">
          <div class="title">菜单详情</div>
          <el-form
            ref="form"
            :model="form"
            label-width="200px"
            label-position="right"
            size="small"
            style="width: 65%; margin-top: 100px;"
          >
            <el-form-item label="父菜单">
              <el-input v-model="form.parentId" placeholder="" />
            </el-form-item>
            <el-form-item label="菜单名">
              <el-input v-model="form.title" placeholder="" />
            </el-form-item>
            <el-form-item label="菜单编码">
              <el-input v-model="form.name" placeholder="" />
            </el-form-item>
            <el-form-item label="菜单路径">
              <el-input v-model="form.path" placeholder="" />
            </el-form-item>
            <el-form-item label="菜单图标">
              <el-input v-model="form.icon" placeholder="" />
            </el-form-item>
            <el-form-item label="第三方链接">
              <el-input v-model="form.link" placeholder="" />
            </el-form-item>
            <el-form-item label="是否激活">
              <el-input v-model="form.activite" placeholder="" />
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

export default {
  name: 'MenuManage',
  components: {
    ContextMenu,
  },
  data() {
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
        label: 'label',
      },
      form: {
        id: '',
        parentId: '',
        title: '',
        name: '',
        path: '',
        icon: '',
        link: '',
        activite: 1,
        create_time: '',
      },
    };
  },
  mounted() {
    this.contextMenuTarget = this.$refs.tree.$el;
  },
  methods: {
    handleContextmenu(e, val, node, comp) {
      // this.contextMenuTarget = comp.$el;
    },
    handleMenuClick(val) {
      if (val === 'a') {
        if (this.treeData.length === 0) {
          this.$message.info('请先添加主菜单');
        }
      }
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
      padding: 20px;
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
