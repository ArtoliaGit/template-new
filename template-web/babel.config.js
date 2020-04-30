const plugins = [
  '@vue/babel-plugin-transform-vue-jsx',
  ['transform-es2015-arrow-functions', { spec: true }],
];
// 生产环境移除console
if (process.env.NODE_ENV === 'production') {
  plugins.push('transform-remove-console');
}
module.exports = {
  plugins,
  presets: [
    '@vue/cli-plugin-babel/preset',
  ],
};
