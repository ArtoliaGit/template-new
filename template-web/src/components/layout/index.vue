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
          <tags-nav />
          <div style="height: calc(100% - 40px); padding: 5px;">
            <main-content />
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import MainContent from './content';
import Menu from './menu';
import Header from './header';
import TagsNav from './tags-nav';

export default {
  name: 'Layout',
  components: {
    MainContent,
    Menu,
    Header,
    TagsNav,
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
