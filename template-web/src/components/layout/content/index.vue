<template>
  <transition
    name="fade-transform"
    mode="out-in"
  >
    <keep-alive :include="cacheList">
      <router-view />
    </keep-alive>
  </transition>
</template>

<script>
export default {
  name: 'Content',
  data() {
    return {
      cacheList: [],
    };
  },
};
</script>
