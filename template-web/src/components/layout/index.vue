<template>
  <div class="layout">
    <el-container>
      <el-aside>
        <Menu :is-collapse="isCollapse" />
      </el-aside>
      <el-container>
        <el-header>
          <Header />
        </el-header>
        <el-main>
          <main-content />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import MainContent from './content';
import Menu from './menu';
import Header from './header';

export default {
  name: 'Layout',
  components: {
    MainContent,
    Menu,
    Header,
  },
  data() {
    return {
    };
  },
  computed: {
    isCollapse() {
      return this.$store.state.app.isCollapse;
    },
  },
  methods: {

  },
};
</script>

<style lang="scss">
@import './layout';
</style>
