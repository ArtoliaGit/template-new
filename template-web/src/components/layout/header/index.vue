<template>
  <el-row class="header-bar">
    <el-col :span="1">
      <SideTrigger />
    </el-col>
    <el-col :span="9">
      <BreadCrumb />
    </el-col>
    <el-col :span="14">
      <UserSetting />
    </el-col>
  </el-row>
</template>

<script>
import SideTrigger from './side-trigger';
import BreadCrumb from './bread-crumb';
import UserSetting from './user-setting';

export default {
  name: 'HeaderBar',
  components: {
    SideTrigger,
    BreadCrumb,
    UserSetting,
  },
  props: {

  },
};
</script>

<style lang="scss" scoped>
.header-bar {
  width: 100%;
  height: 100%;
  user-select: none;
  .el-col {
    height: 100%;
  }
  .el-col:nth-child(3) {
    position: relative;
  }
}
</style>
