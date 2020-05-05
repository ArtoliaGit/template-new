<template>
  <div class="bread-crumb">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item
        v-for="item in breadCrumbList"
        :key="item.name"
        :to="item.to"
      >
        <i :class="item.icon" />
        <span>{{ item.title }}</span>
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  name: 'BreadCrumb',
  data() {
    return {
    };
  },
  computed: {
    breadCrumbList() {
      return this.$store.state.app.breadCrumbList;
    },
  },
  watch: {
    $route(newRoute) {
      const paths = newRoute.matched.map(item => item.path);
      this.$store.dispatch('handleBreadCrumbList', paths);
    },
  },
  mounted() {
    const paths = this.$route.matched.map(item => item.path);
    this.$store.dispatch('handleBreadCrumbList', paths);
  },
};
</script>

<style lang="scss" scoped>
.bread-crumb {
  height: 100%;
  display: flex;
  align-items: center;
  font-size: 20px;
  span {
    font-size: 16px;
    >span {
      margin-left: 5px;
    }
  }
}
</style>
