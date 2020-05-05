<template>
  <div class="link">
    <iframe src="http://www.baidu.com" />
  </div>
</template>

<script>
export default {
  name: 'Link',
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
