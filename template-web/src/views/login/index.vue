<template>
  <div class="page">
    <password-login v-if="loginType === 1" />
    <phone-login v-if="loginType === 2" />
  </div>
</template>

<script>
import config from '@/config';
import PasswordLogin from '@/components/login/password-login';
import PhoneLogin from '@/components/login/phone-login';

export default {
  name: 'Login',
  components: {
    PasswordLogin,
    PhoneLogin,
  },
  data() {
    return {
      loginType: config.loginType,
    };
  },
  mounted() {

  },
  methods: {

  },
};
</script>

<style lang="scss" scoped>
.page {
  width: 100%;
  height: 100%;
}
</style>
