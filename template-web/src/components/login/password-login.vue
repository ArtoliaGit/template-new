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
            size="medium"
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
            size="medium"
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
            size="medium"
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
import CryptoJS from 'crypto-js';

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
      this.$refs.form.validate(valid => {
        if (valid) {
          const data = {
            username: this.form.username,
            password: CryptoJS.MD5(this.form.password).toString(),
          };
          const loading = this.$loading({
            lock: true,
            text: '正在登录...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.1)',
            customClass: 'loading',
          });
          this.handleLogin(data).then(() => {
            loading.close();
            this.$message.success('登录成功');
            this.handleGetUserInfo().then(() => {
              this.$router.push({ name: 'Home' });
            }).catch(e => {
              this.$message.error(e.message);
            });
          }).catch(e => {
            loading.close();
            this.$message.error(e.message);
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

<style lang="scss">
.loading {
  .el-loading-spinner {
    margin-top: -50px!important;
  }
  .el-icon-loading {
    font-size: 50px;
  }
  .el-loading-text {
    font-size: 20px;
  }
}
</style>
