<template>
  <div>
    <el-menu-item
      :index="submenu.path"
      class="menu-item"
      v-if="!submenu.children || submenu.children.length === 0"
    >
      <i :class="submenu.icon || 'el-icon-eleme'" />
      <span slot="title">{{ submenu.title }}</span>
    </el-menu-item>
    <el-submenu
      :index="submenu.path"
      class="submenu"
      popper-append-to-body
      v-else
    >
      <template
        slot="title"
        class="title"
      >
        <i :class="submenu.icon" />
        <span>{{ submenu.title }}</span>
      </template>
      <sub-menu v-for="item in submenu.children" :key="item.id" :submenu="item" />
    </el-submenu>
  </div>
</template>

<script>
export default {
  name: 'SubMenu',
  data() {
    return {
    };
  },
  props: {
    submenu: {
      type: Object,
      default: () => {},
    },
  },
};
</script>

<style lang="scss">
.el-menu--collapse > div > .el-submenu > .el-submenu__title span,
.el-menu--collapse
  > div
  > .el-submenu
  > .el-submenu__title
  .el-submenu__icon-arrow {
  visibility: hidden;
}
</style>
