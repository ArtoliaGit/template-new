<template>
  <div class="link">
    <iframe :src="url" v-if="url" />
    <div v-else>
      没有链接
    </div>
  </div>
</template>

<script>
export default {
  name: 'Link',
  data() {
    return {
    };
  },
  computed: {
    url() {
      return this.$route.meta.link;
    },
  },
};
</script>

<style lang="scss" scoped>
.link {
  height: 100%;
  width: 100%;
  iframe {
    height: 100%;
    width: 100%;
    border: none;
  }
}
</style>
