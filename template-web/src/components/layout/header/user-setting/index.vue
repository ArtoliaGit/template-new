<template>
  <div class="user-setting">
    <el-dropdown
      class="avator-container"
      placement="bottom"
      @command="handleCommand"
    >
      <div class="avator-wrapper">
        <img
          src="../../../../assets/avator.jpg"
          class="user-avator"
        >
      </div>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="logout">
          退出登录
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: 'UserSetting',
  methods: {
    handleLogout() {
      this.$store.dispatch('handleLogout');
    },
    handleCommand(command) {
      if (command === 'logout') {
        this.handleLogout();
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.user-setting {
  height: 100%;
  width: 60px;
  display: inline-block;
  right: 0;
  position: absolute;
  display: flex;
  align-items: center;
  user-select: none;
  .avator-container {
    .user-avator {
      width: 40px;
      border-radius: 50%;
    }
  }
}
</style>
