
const path = require('path');
const CompressionPlugin = require('compression-webpack-plugin');

const resolve = (dir) => path.join(__dirname, dir);

const BASE_URL = process.env.NODE_ENV === 'production'
  ? '/'
  : '/';

module.exports = {
  publicPath: BASE_URL,
  runtimeCompiler: false,
  lintOnSave: true,
  outputDir: 'dist',
  assetsDir: 'static',
  productionSourceMap: false,
  parallel: require('os').cpus().length > 1,

  chainWebpack: (config) => {
    config.resolve.alias
      .set('@', resolve('src'))
      .set('_c', resolve('src/components'));
  },
  configureWebpack: () => {
    if (process.env.NODE_ENV === 'production') {
      return {
        plugins: [new CompressionPlugin({
          test: /\.js$|\.html$|\.css/,
          threshold: 10240,
          deleteOriginalAssets: false,
          minRatio: 0.8,
          algorithm: 'gzip',
        })],
        performance: {
          maxEntrypointSize: 1024000 * 2,
          maxAssetSize: 2048000,
        },
      };
    }
  },
  devServer: {
    open: false,
    host: '0.0.0.0',
    port: 8080,
    https: false,
    hotOnly: true,
    disableHostCheck: true,
    bonjour: true,
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:9090',
        changeOrigin: true,
        pathRewrite: {
          '^/api': '',
        },
        logLevel: 'debug',
      },
      '/api/websocket': {
        target: 'ws://127.0.0.1:9090',
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': '',
        },
        logLevel: 'debug',
      },
    },
    before: () => {
      // 在其他中间件执行之前运行
    },
    after: () => {
      // 在其他中间件执行之后运行
    },
  },
};
