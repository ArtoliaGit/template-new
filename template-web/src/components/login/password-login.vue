<template>
  <div class="login">
    <el-card class="login-card">
      <div>
        <span>系统登录</span>
      </div>
      <el-form
        :model="form"
        ref="form"
        :rules="rules"
        @keydown.enter.native="handleSubmit"
      >
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
          >
            <template slot="prepend">
              <i class="el-icon-user-solid" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="form.password"
            placeholder="请输入密码"
            :type="hidePassword ? 'password' : 'text'"
          >
            <template slot="prepend">
              <i class="el-icon-lock" />
            </template>
            <div
              slot="suffix"
              class="eye"
              @click="handleClickEye"
            >
              <i
                class="iconfont"
                :class="{ 'icon-eyeclose-fill': hidePassword, 'icon-eye-fill': !hidePassword }"
              />
            </div>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            class="login-button"
            @click.native.prevent="handleSubmit"
          >
            <span>登</span><span>录</span>
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { debounce } from 'lodash';
import { mapActions } from 'vuex';

export default {
  name: 'PasswordLogin',
  data() {
    return {
      form: {
        username: '',
        password: '',
      },
      rules: {
        username: {
          required: true,
          message: '账号不能为空',
          trigger: 'blur',
        },
        password: {
          required: true,
          message: '密码不能为空',
          trigger: 'blur',
        },
      },
      hidePassword: true,
    };
  },
  methods: {
    ...mapActions(['handleLogin', 'handleGetUserInfo']),
    handleSubmit: debounce(function anonymous() {
      // TODO 登录事件
      this.$refs.form.validate(valid => {
        if (valid) {
          this.handleLogin(this.form).then(() => {
            this.$message.success('登录成功');
            this.handleGetUserInfo().then(() => {
              this.$router.push({ name: 'Login' });
            });
          });
        }
      });
    }, 100),
    handleClickEye() {
      this.hidePassword = !this.hidePassword;
    },
  },
};
</script>

<style lang="scss" scoped>
.login {
  width: 100%;
  height: 100%;
  position: fixed;
}
.login-card {
  width: 400px;
  height: 260px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  justify-content: center;
  .el-card__body {
    >div:nth-child(n) {
      margin-top: 20px;
      margin-left: 10px;
      margin-right: 10px;
    }
    >div:first-child {
      margin-top: 0;
      font-size: 24px;
      font-weight: bold;
      user-select: none;
      margin-bottom: 20px;
    }
    .eye {
      height: 100%;
      margin-right: 10px;
      display: flex;
      align-items: center;
      cursor: pointer;
    }
  }
}
.login-button {
  width: 100%;
  padding-top: 10px;
  padding-bottom: 10px;
  font-size: 18px;
  font-weight: bold;
  span>span:first-child {
    letter-spacing: 50px;
  }
}
</style>
