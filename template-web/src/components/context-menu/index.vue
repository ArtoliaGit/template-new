<template>
  <div
    :style="style"
    v-if="show"
    class="right-menu"
    @mousedown.stop
    @contextmenu.prevent
  >
    <slot />
    <div
      v-for="item in menuList"
      :key="item.value"
      @click="e => handleItemClick(e, item.value)"
    >
      {{ item.label }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'ContextMenu',
  data() {
    return {
      triggerShowFn: () => {},
      triggerHideFn: () => {},
      x: null,
      y: null,
      style: {},
      binded: false,
      show: false,
    };
  },
  props: {
    target: {
      type: Element,
      default: null,
    },
    menuList: {
      type: Array,
      default: () => [],
    },
  },
  mounted() {
  },
  destroyed() {
    this.unbindEvents();
  },
  watch: {
    show(show) {
      if (show) {
        this.bindHideEvents();
      } else {
        this.unbindHideEvents();
      }
    },
    target: {
      handler(target) {
        this.bindEvents();
      },
      immediate: true,
    },
  },
  methods: {
    // 初始化事件
    bindEvents() {
      if (!this.menuList || this.menuList.length === 0) {
        return;
      }
      this.$nextTick(() => {
        if (!this.target || this.binded) return;
        this.triggerShowFn = this.contextMenuHandler.bind(this);
        this.target.addEventListener('contextmenu', this.triggerShowFn, {
          capture: true,
        });
        this.binded = true;
      });
    },
    // 取消绑定事件
    unbindEvents() {
      if (!this.target) return;
      this.binded = false;
      this.target.removeEventListener('contextmenu', this.triggerShowFn);
    },
    // 绑定隐藏菜单事件
    bindHideEvents() {
      this.triggerHideFn = this.clickDocumentHandler.bind(this);
      document.addEventListener('mousedown', this.triggerHideFn);
      document.addEventListener('mousewheel', this.triggerHideFn);
    },
    // 取消绑定隐藏菜单事件
    unbindHideEvents() {
      document.removeEventListener('mousedown', this.triggerHideFn);
      document.removeEventListener('mousewheel', this.triggerHideFn);
    },
    // 鼠标按压事件处理器
    clickDocumentHandler(e) {
      this.show = false;
    },
    // 右键事件事件处理
    contextMenuHandler(e) {
      this.x = e.clientX;
      this.y = e.clientY;
      this.layout();
      this.show = true;
      e.preventDefault();
    },
    // 布局
    layout() {
      this.style = {
        left: `${this.x}px`,
        top: `${this.y}px`,
      };
    },
    handleItemClick(e, val) {
      this.$emit('menu-click', val);
      this.show = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.right-menu {
  position: fixed;
  background: #fff;
  border: solid 1px rgba(0, 0, 0, .2);
  border-radius: 3px;
  z-index: 999;
}
.right-menu>div {
  // width: 100px;
  height: 28px;
  line-height: 28px;
  text-align: left;
  display: block;
  color: #1a1a1a;
  text-decoration: none;
  cursor: pointer;
  white-space: nowrap;
  padding: 0 5px;
  font-size: 14px;
}
.right-menu>div:hover {
  background: #42b983;
  color: #fff;
}
</style>
