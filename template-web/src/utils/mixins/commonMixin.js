export default {
  data() {
    return {
      maxHeight: 0,
    };
  },
  methods: {
    resize() {
      if (this.otherHeight) {
        this.maxHeight = document.getElementById('page-content').getBoundingClientRect().height - this.otherHeight;
      }
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.resize();
    });
    window.onresize = () => {
      this.resize();
    };
  },
  beforeDestroy() {
    window.onresize = null;
  },
};
