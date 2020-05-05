<template>
  <div class="side-trigger">
    <i
      class="iconfont icon-nav"
      :style="{ transform: (isCollapse ? 'rotate(90deg)' : 'rotate(0deg)') }"
      @click="handleClick"
    />
  </div>
</template>

<script>
export default {
  name: 'SideTrigger',
  computed: {
    isCollapse() {
      return this.$store.state.app.isCollapse;
    },
  },
  methods: {
    handleClick() {
      this.$store.commit('setIsCollapse', !this.$store.state.app.isCollapse);
      this.$emit('on-change', !this.$store.state.app.isCollapse);
    },
  },
};
</script>

<style lang="scss" scoped>
.side-trigger {
  height: 100%;
  display: flex;
  align-items: center;
  i {
    font-size: 30px;
    cursor: pointer;
    display: inline-block;
    transition: all 0.28s ease;
  }
}
</style>
