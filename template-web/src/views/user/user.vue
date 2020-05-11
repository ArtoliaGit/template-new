<template>
  <div class="page">
    <el-card>
      <el-row class="filter-container">
        <el-input
          placeholder="用户名"
          v-model="query.username"
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
          :max-height="maxHeight"
          :height="maxHeight"
          style="width: 100%"
          header-cell-class-name="table-header-custom"
          v-loading="tableLoading"
          size="small"
        >
          <el-table-column label="用户名" prop="username" />
          <el-table-column label="姓名" prop="person.personName" />
          <el-table-column label="联系电话" prop="person.phone" />
          <el-table-column label="状态" prop="status" :formatter="getStatusDic" />
          <el-table-column label="创建时间" prop="createTime" />
          <el-table-column label="操作" align="center" width="250">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button
                type="primary"
                size="mini"
                @click="handleUpdatePassword(scope.row.userId)"
              >
                重置密码
              </el-button>
              <el-button
                type="danger"
                size="mini"
                @click="handleDelete(scope.row.userId)"
              >
                禁用
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
  </div>
</template>

<script>
import {
  getUserList,
  save,
  remove,
  resetPassword,
} from '@/api/user';
import mixins from '@/utils/mixins/commonMixin';

export default {
  name: 'User',
  mixins: [mixins],
  data() {
    return {
      tableData: [],
      statusDic: [
        { key: 2, text: '禁用' },
        { key: 1, text: '正常' },
      ],
      page: {
        page: 1,
        pageSize: 10,
        total: 0,
        pageSizes: [10, 20, 30, 40, 50],
      },
      tableLoading: false,
      query: {
        username: '',
      },
      otherHeight: 125,
      roleDic: [],
      organDic: [],
      typeDic: [],
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
        username: this.query.username,
      };
      this.tableLoading = true;
      getUserList(params).then((res) => {
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
    handleAdd() {
      this.$router.push({ name: 'UserEdit' });
    },
    handleEdit(val) {
      this.$router.push({ name: 'UserEdit', query: { userId: val.userId } });
    },
    handleDelete(id) {
      remove({ userId: id }).then((res) => {
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '禁用成功',
          });
        }
        this.getTableData();
      });
    },
    refresh() {
      this.getTableData();
    },
    handleUpdatePassword(id) {
      const user = {
        userId: id,
      };
      resetPassword(user).then((res) => {
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '密码重置成功!',
          });
        }
      });
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
    this.getTableData();
  },
  activated() {
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
</style>
