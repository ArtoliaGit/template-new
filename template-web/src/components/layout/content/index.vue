<template>
  <div id="page-content" class="page-content">
    <transition
      name="fade-transform"
      mode="out-in"
    >
      <keep-alive :include="cacheList">
        <router-view />
      </keep-alive>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'Content',
  data() {
    return {
    };
  },
  computed: {
    cacheList() {
      return this.$store.state.app.cacheList;
    },
  },
};
</script>

<style lang="scss" scoped>
.page-content {
  width: 100%;
  height: 100%;
}
</style>
