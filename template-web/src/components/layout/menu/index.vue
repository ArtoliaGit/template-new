<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <el-menu
      ref="menu"
      mode="vertical"
      :router="true"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409eff"
      :collapse-transition="true"
      :collapse="isCollapse"
      :default-active="routeIndex"
      :unique-opened="false"
    >
      <sub-menu v-for="item in menuList" :key="item.id" :submenu="item" />
    </el-menu>
  </el-scrollbar>
</template>

<script>
import SubMenu from './sub-menu';

export default {
  name: 'Menu',
  components: {
    SubMenu,
  },
  props: {
    isCollapse: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
    };
  },
  computed: {
    menuList() {
      return this.$store.state.app.menuList;
    },
    routeIndex() {
      return this.$route.path;
    },
  },
  mounted() {

  },
};
</script>

<style lang="scss">
.el-menu {
  border: none;
  height: 100%;
  width: 220px;
}
.el-menu--collapse {
  width: 60px;
}
.scrollbar-wrapper {
  overflow-x: hidden !important;
}
.el-scrollbar__bar.is-vertical {
  right: 0px;
}

.el-scrollbar {
  height: 100%;
}
</style>
