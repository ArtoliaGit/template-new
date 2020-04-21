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
        <el-form-item prop="phone">
          <el-input
            v-model="form.phone"
            placeholder="请输入常用手机号"
          >
            <el-select
              v-model="form.country"
              slot="prepend"
              placeholder=""
              style="width: 120px;"
            >
              <el-option
                v-for="item in countryList"
                :label="item.cname"
                :value="item.country_id"
                :key="item.id"
              />
            </el-select>
          </el-input>
        </el-form-item>
        <el-form-item prop="valicode">
          <el-input
            v-model="form.valicode"
            placeholder="请输入验证码"
          >
            <el-button
              slot="append"
              @click="handleValicode"
              :class="{ 'valicode-btn': !disableGetCode }"
              :disabled="disableGetCode"
            >
              <span class="valicode-text">{{ valicodeText }}</span>
            </el-button>
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

export default {
  name: 'PhoneLogin',
  data() {
    return {
      form: {
        country: '86',
        phone: '',
        valicode: '',
      },
      rules: {
        phone: {
          required: true,
          message: '手机号不能为空',
          trigger: 'blur',
        },
        valicode: {
          required: true,
          message: '验证码不能为空',
          trigger: 'blur',
        },
      },
      countryList: [
        { id: 1, cname: '中国大陆', country_id: '86' },
        { id: 2, cname: '中国香港特别行政区', country_id: '852' },
        { id: 3, cname: '中国澳门特别行政区', country_id: '853' },
        { id: 4, cname: '中国台湾', country_id: '886' },
      ],
      valicodeText: '获取验证码',
      countdown: 10,
      disableGetCode: false,
    };
  },
  methods: {
    handleSubmit: debounce(function anonymous() {
      // TODO 登录事件
      this.$refs.form.validate(valid => {
        if (valid) {
          this.$message.success('登录成功');
        }
      });
    }, 100),
    handleValicode: debounce(function anonymous() {
      // TODO 获取验证码
      this.$refs.form.validateField('phone', valid => {
        if (!valid) {
          this.disableGetCode = true;
          this.$message.success('获取验证码');
          let countdown = this.countdown;
          const timer = setInterval(() => {
            if (countdown < 0) {
              clearInterval(timer);
              this.valicodeText = '重新获取验证码';
              this.disableGetCode = false;
            } else {
              this.valicodeText = `${countdown}s后重试`;
              countdown -= 1;
            }
          }, 1000);
        }
      });
    }, 100),
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
    .valicode-btn {
      background: #1890ff;
      color: white;
    }
    .valicode-text {
      width: 100px;
      display: inline-block;
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
