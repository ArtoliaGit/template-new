<template>
  <div class="page">
    <el-card>
      <el-row class="filter-container">
        <el-input
          placeholder="角色名"
          v-model="query.roleName"
          size="small"
          style="width: 120px;"
          class="filter-item"
        />
        <el-button
          type="primary"
          size="small"
          class="filter-item"
          icon="el-icon-search"
          style="margin-left: 10px;"
          @click="handleSearch"
        >
          查询
        </el-button>
        <el-button
          type="primary"
          size="small"
          class="filter-item"
          icon="el-icon-plus"
          @click="handleAdd"
        >
          新增
        </el-button>
      </el-row>
      <el-row>
        <el-table
          :data="tableData"
          border
          highlight-current-row
          fit
          :height="maxHeight"
          style="width: 100%"
          header-cell-class-name="table-header-custom"
          v-loading="tableLoading"
          size="small"
          ref="table"
        >
          <el-table-column label="角色名" prop="roleName" />
          <el-table-column label="角色编码" prop="roleCode" />
          <el-table-column label="角色描述" prop="roleDescription" />
          <el-table-column label="状态" prop="status" :formatter="getStatusDic" />
          <el-table-column label="创建时间" prop="createTime" />
          <el-table-column label="操作" align="center" width="200">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button
                type="primary"
                size="mini"
                @click="handleEditResource(scope.row)"
              >
                菜单权限
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="page.page"
          :page-size="page.pageSize"
          :page-sizes="page.pageSizes"
          :total="page.total"
          background
          layout="total, sizes, prev, pager, next, jumper, slot"
        >
          <el-button size="mini" icon="el-icon-refresh" @click="refresh" plain>刷新</el-button>
        </el-pagination>
      </el-row>
    </el-card>

    <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      width="40%"
    >
      <el-form
        :model="form"
        ref="form"
        :rules="rules"
        size="small"
        :label-width="labelWidth"
        style="width: 80%;"
      >
        <el-form-item label="角色名" prop="roleName">
          <el-input type="string" v-model="form.roleName" />
        </el-form-item>
        <el-form-item label="角色编码" prop="roleCode">
          <el-input type="string" v-model="form.roleCode" />
        </el-form-item>
        <el-form-item label="角色描述" prop="roleDescription">
          <el-input type="string" v-model="form.roleDescription" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" style="width: 100%;">
            <el-option
              v-for="item in statusDic"
              :key="item.key"
              :label="item.text"
              :value="item.key"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleSave">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="编辑菜单权限"
      :visible.sync="resourceFormVisible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      width="30%"
      center
    >
      <el-tree
        :data="treeData"
        show-checkbox
        node-key="id"
        ref="tree"
        highlight-current
        :props="defaultProps"
        :default-checked-keys="defaultChecked"
      />
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleSaveResource">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getRoleList,
  save,
  remove,
  saveResource,
} from '@/api/role';
import { getList } from '@/api/menu';
import mixins from '@/utils/mixins/commonMixin';

export default {
  name: 'Role',
  mixins: [mixins],
  data() {
    return {
      tableData: [],
      statusDic: [
        { key: 1, text: '正常' },
        { key: 2, text: '禁用' },
      ],
      page: {
        page: 1,
        pageSize: 10,
        total: 0,
        pageSizes: [10, 20, 30, 40, 50],
      },
      tableLoading: false,
      form: {
        roleId: '',
        roleCode: '',
        roleName: '',
        roleDescription: '',
        status: 1,
        createTime: '',
        resources: [],
      },
      rules: {
        roleName: [{ required: true, message: '不能为空', trigger: 'blur' }],
        roleCode: [{ required: true, message: '不能为空', trigger: 'blur' }],
        roleDescription: [{ required: true, message: '不能为空', trigger: 'blur' }],
      },
      dialogFormVisible: false,
      labelWidth: '120px',
      op: 'create',
      title: '新建',
      query: {
        roleName: '',
      },
      otherHeight: 125,
      resourceFormVisible: false,
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'title',
      },
      defaultChecked: [],
    };
  },
  methods: {
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.getTableData();
    },
    handleCurrentChange(val) {
      this.page.page = val;
      this.getTableData();
    },
    getTableData() {
      const params = {
        page: this.page.page,
        pageSize: this.page.pageSize,
        roleName: this.query.roleName,
      };
      this.tableLoading = true;
      getRoleList(params).then((res) => {
        this.tableLoading = false;
        if (res.code === 200) {
          this.tableData = res.data;
          this.page.total = res.total;
        } else {
          this.tableData = [];
        }
      });
    },
    handleSearch() {
      this.page.page = 1;
      this.getTableData();
    },
    handleSave() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.form.createUser = this.$store.state.user.username;
          save(this.form).then((res) => {
            if (res.code === 200) {
              this.$message({
                type: 'success',
                message: '保存成功',
              });
              this.handleCancel();
              this.getTableData();
            } else {
              this.$message({
                type: 'error',
                message: '保存失败',
              });
            }
          });
        }
      });
    },
    resetForm() {
      this.form = {
        roleId: '',
        roleName: '',
        roleCode: '',
        roleDescription: '',
        status: 1,
        createTime: '',
        createUser: this.$store.state.user.userName,
        resource: [],
      };
    },
    handleAdd() {
      this.resetForm();
      this.op = 'create';
      this.title = '新建';
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
      });
    },
    handleEdit(val) {
      this.resetForm();
      this.form = Object.assign(this.form, val);
      this.op = 'update';
      this.title = '编辑';
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs.form.clearValidate();
      });
    },
    handleCancel() {
      this.dialogFormVisible = false;
      this.resourceFormVisible = false;
    },
    refresh() {
      this.getTableData();
    },
    handleEditResource(val) {
      this.$nextTick(() => {
        if (this.$refs.tree) {
          this.$refs.tree.setCheckedKeys([]);
        }
      });

      this.form.roleId = val.roleId;
      this.defaultChecked = this.getResource(val.resource);
      this.resourceFormVisible = true;
    },
    getMenuList() {
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
    handleSaveResource() {
      const checkNodes = this.$refs.tree.getCheckedNodes();
      const halfCheckNodes = this.$refs.tree.getHalfCheckedNodes();
      this.form.resource = checkNodes.map(item => item);
      this.form.resource = this.form.resource.concat(halfCheckNodes.map(item => item));

      saveResource(this.form).then((res) => {
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '保存成功',
          });
          this.resourceFormVisible = false;
          this.getTableData();
        }
      });
    },
    getResource(resource) {
      const changeResource = [];
      const filterResource = list => list.forEach((item) => {
        if (item.children && item.children.length > 0) {
          filterResource(item.children);
        } else if (resource.some(r => r.id === item.id)) {
          changeResource.push(item.id);
        }
      });
      filterResource(this.treeData);
      return changeResource;
    },
    getStatusDic(row, column, cellValue, index) {
      const result = this.statusDic.filter(item => cellValue === item.key);
      if (result.length > 0) {
        return result[0].text;
      }
      return '';
    },
  },
  mounted() {
    this.getMenuList();
    this.getTableData();
  },
};
</script>

<style lang="scss" scoped>
.page {
  height: 100%;
  .el-card {
    height: 100%;
  }
}
.el-tree {
  min-height: 300px;
  overflow-y: auto;
  max-height: 300px;
  border: 1px solid #e6e6e6;
}
</style>
