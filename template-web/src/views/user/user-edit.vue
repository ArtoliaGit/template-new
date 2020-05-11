<template>
  <div class="page">
    <el-card>
      <el-row class="filter-container">
        <el-button
          type="primary"
          size="small"
          class="filter-item"
          style="margin-left: 10px;"
          @click="handleSave"
        >
          保存
        </el-button>
        <el-button
          type="primary"
          size="small"
          class="filter-item"
          @click="handleCancel"
        >
          返回
        </el-button>
      </el-row>
      <el-row>
        <el-form
          ref="form"
          :model="form"
          label-width="100px"
          label-position="left"
          size="small"
          style="width: 65%;"
        >
          <el-row :gutter="40">
            <el-col :span="12">
              <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="姓名" prop="personName">
                <el-input v-model="form.person.personName" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="40">
            <el-col :span="12">
              <el-form-item label="性别" prop="gender">
                <el-select v-model="form.person.gender" style="width: 100%;">
                  <el-option
                    v-for="item in genderDic"
                    :key="item.key"
                    :label="item.text"
                    :value="item.key"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="出生日期" prop="birthday">
                <el-date-picker
                  v-model="form.person.birthday"
                  type="date"
                  style="width: 100%;"
                  value-format="yyyy-MM-dd HH:mm:ss"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="40">
            <el-col :span="12">
              <el-form-item label="联系电话" prop="phone">
                <el-input v-model="form.person.phone" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.person.email" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="地址" prop="address">
                <el-input v-model="form.person.address" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="40">
            <el-col :span="24">
              <el-form-item label="角色配置">
                <el-transfer
                  v-model="selectedRole"
                  :data="roleDic"
                  style="height: 200px; white-space: nowrap;"
                  :titles="['角色列表', '已选角色']"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import {
  getRoleList,
} from '@/api/role';
import {
  getUserById,
  save,
} from '@/api/user';

export default {
  name: 'UserEdit',
  data() {
    return {
      form: {
        userId: '',
        username: '',
        person: {
          personName: '',
          empId: '',
          userId: '',
          gender: '',
          birthday: '',
          phone: '',
          email: '',
          address: '',
          createUser: '',
        },
        status: '1',
        roles: [],
        createUser: '',
      },
      genderDic: [
        { key: '1', text: '男' },
        { key: '2', text: '女' },
      ],
      selectedRole: [],
      roleDic: [],
    };
  },
  methods: {
    handleSave() {
      const data = this.form;
      data.roles = this.selectedRole.map(item => ({ roleId: item }));
      this.$refs.form.validate((valid) => {
        if (valid) {
          data.createUser = this.$store.state.user.username;
          save(data).then((res) => {
            if (res.code === 200) {
              this.$message({
                type: 'success',
                message: '保存成功',
              });
              this.handleCancel();
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
    handleCancel() {
      this.$store.dispatch('handleCloseTag', this.$route.path);
      this.$router.push({ name: 'User' });
    },
    getRoleDic() {
      const params = {
        page: 1,
        pageSize: 1000,
      };
      getRoleList(params).then((res) => {
        if (res.code === 200) {
          this.roleDic = res.data.map(item => ({ key: item.roleId, label: item.roleName }));
        }
      });
    },
    getFormData() {
      const params = {
        userId: this.form.userId,
      };
      getUserById(params).then((res) => {
        if (res.code === 200) {
          this.form = { ...this.form, ...res.data };
          this.selectedRole = this.form.roles.map(item => item.roleId);
        }
      });
    },
  },
  mounted() {
    this.getRoleDic();
    if (this.$route.query.userId) {
      this.form.userId = this.$route.query.userId;
      this.getFormData();
    }
  },
};
</script>

<style lang="scss" scoped>
.page {
  height: 100%;
}
.el-card {
  height: 100%;
  overflow: auto;
}
.el-transfer {
  text-align: left;
}
</style>

<style>
.el-checkbox {
  display: block;
}
</style>
