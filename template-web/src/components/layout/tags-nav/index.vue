<template>
  <div class="tags-nav">
    <div>
      <el-button @click="handleLeftClick">
        <i style="font-size: 18px" class="el-icon-arrow-left" />
      </el-button>
    </div>
    <div>
      <div
        ref="scroller"
        :style="getLeft"
        @mousewheel="handleMousewheel"
        @DOMMouseScroll="handleMousewheel"
      >
        <div
          class="tag" v-for="(item, index) in tagList"
          :key="item.path"
          @click="handleSelect(item)"
        >
          <div :style="{ background: activePage === item.path ? '#2d8cf0' : '#e8eaec' }" />
          <span>{{ item.title }}</span>
          <i v-if="item.isClose" @click.stop="handleCloseTag(index)" class="el-icon-close" />
        </div>
      </div>
    </div>
    <div>
      <el-button @click="handleRightClick">
        <i style="font-size: 18px" class="el-icon-arrow-right" />
      </el-button>
    </div>
    <div>
      <el-dropdown size="small" @command="handleCommand">
        <el-button size="small" type="text" style="height: 100%;">
          <i style="font-size: 18px" class="el-icon-circle-close" />
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="all">关闭所有</el-dropdown-item>
          <el-dropdown-item command="other">关闭其他</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex';
import config from '@/config';
import { getTagListByStorage } from '@/utils/common';
import beforeClose from '@/router/before-close';

export default {
  name: 'TagsNav',
  data() {
    return {
      isClose: false,
      left: 0,
      activePage: config.homePage.path,
    };
  },
  computed: {
    getLeft() {
      return { left: `${this.left}px` };
    },
    tagList() {
      return this.$store.state.app.tagList;
    },
  },
  watch: {
    $route(newRoute) {
      if (!this.tagList.find(item => item.path === newRoute.path)) {
        this.setTagList([...this.tagList, {
          path: newRoute.path,
          name: newRoute.name,
          title: newRoute.meta.title,
          meta: newRoute.meta,
          isClose: true,
        }]);
      }
      this.activePage = newRoute.path;
    },
  },
  methods: {
    ...mapMutations(['setTagList']),
    handleMousewheel(e) {
      this.move(e.wheelDelta);
    },
    handleLeftClick() {
      this.move(-1);
    },
    handleRightClick() {
      this.move(1);
    },
    move(delta) {
      const outerWidth = this.$refs.scroller.parentElement.offsetWidth;
      const innerWidth = this.$refs.scroller.offsetWidth;
      const { offsetLeft } = this.$refs.scroller;
      if (delta < 0 && (outerWidth - innerWidth < offsetLeft)) {
        this.left = Math.max(this.left - 30, outerWidth - innerWidth);
      } else if (delta > 0 && this.left < 0) {
        this.left = Math.min(this.left + 30, 0);
      }
    },
    handleCommand(command) {
      if (command === 'all') {
        this.setTagList(this.tagList.slice(0, 1));
        if (this.activePage !== config.homePage.path) {
          this.activePage = this.tagList[0].path;
          this.$router.push({ path: this.activePage });
        }
      } else if (command === 'other') {
        if (this.activePage === config.homePage.path) {
          this.setTagList(this.tagList.slice(0, 1));
        } else {
          this.setTagList([
            this.tagList[0],
            ...this.tagList.filter(item => item.path === this.activePage),
          ]);
        }
      }
    },
    handleCloseTag(index) {
      const current = this.tagList[index];
      if (current.meta
        && current.meta.beforeCloseName
        && current.meta.beforeCloseName in beforeClose) {
        new Promise(beforeClose[current.meta.beforeCloseName]).then((close) => {
          if (close) {
            this.close(index);
          }
        });
      } else {
        this.close(index);
      }
    },
    close(index) {
      if (this.activePage === this.tagList[index].path) {
        this.activePage = this.tagList[index - 1].path;
      }
      const tags = this.tagList.slice();
      tags.splice(index, 1);
      this.setTagList(tags);
      this.$router.push({ path: this.activePage });
    },
    handleSelect(item) {
      this.activePage = item.path;
      this.$router.push({ path: item.path });
    },
  },
  mounted() {
    let tagList = this.tagList.slice();
    if (tagList.length === 1) {
      tagList = tagList.concat(
        getTagListByStorage().filter(item => item.path !== config.homePage.path),
      );
    }
    this.setTagList(tagList);
    this.activePage = this.$route.path;
  },
};
</script>

<style lang="scss" scoped>
.tags-nav {
  z-index: 99;
  width: 100%;
  height: 40px;
  position: relative;
  border-top: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
  user-select: none;
  >div {
    position: absolute;
    display: inline-block;
    height: 100%;
  }
  >div:nth-child(1) {
    left: 0;
    z-index: 999;
    .el-button {
      height: 100%;
      border: none;
      padding: 0 5px;
      border-right: 1px solid #f0f0f0;
      border-radius: 0;
    }
  }
  >div:nth-child(2) {
    left: 29px;
    right: 60px;
    box-shadow: inset 0 0 3px 2px hsla(0,0%,39.2%,.1);
    display: flex;
    align-items: center;
    background: #f0f0f0;
    overflow: hidden;
    >div {
      display: flex;
      align-items: center;
      padding: 1px 4px 0;
      position: absolute;
      overflow: visible;
      white-space: nowrap;
      -webkit-transition: left .3s ease;
      transition: left .3s ease;
      .tag {
        border: 1px solid #e8eaec;
        white-space: nowrap;
        border-radius: 4px;
        margin-left: 5px;
        height: 30px;
        line-height: 30px;
        font-size: 14px;
        background: white;
        cursor: pointer;
        >div:nth-child(1) {
          width: 10px;
          height: 10px;
          border-radius: 50%;
          background: #2d8cf0;
          position: absolute;
          top: 50%;
          transform: translateY(-50%);
          margin: 0 10px;
        }
        span {
          margin-left: 30px;
          margin-right: 10px;
        }
        i {
          margin-right: 10px;
        }
        i:hover {
          font-weight: bold;
        }
      }
    }
  }
  >div:nth-child(3) {
    right: 30px;
    z-index: 999;
    background: white;
    .el-button {
      height: 100%;
      border: none;
      padding: 0 5px;
      border-right: 1px solid #f0f0f0;
      border-left: 1px solid #f0f0f0;
      border-radius: 0;
    }
  }
  >div:nth-child(4) {
    right: 0;
    width: 30px;
    z-index: 999;
    background: white;
    .el-dropdown {
      height: 100%;
    }
    .el-button {
      border: none;
      width: 30px;
    }
  }
}
</style>
