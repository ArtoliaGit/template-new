const { sh } = require('tasksfile');
const chalk = require('chalk');
const connect = require('connect');
const serveStatic = require('serve-static');
const config = require('../vue.config.js');

const rawArgv = process.argv.slice(2);
const args = rawArgv.join(' ');

if (process.env.npm_config_preview || rawArgv.includes('--preview')) {
  const report = rawArgv.includes('--report');

  sh(`vue-cli-service build ${args}`);

  const port = 9526;
  const { publicPath } = config;

  const app = connect();

  app.use(
    publicPath,
    serveStatic('./dist', {
      index: ['index.html', '/'],
    }),
  );

  app.listen(port, () => {
    console.log('App running at:');
    console.log('- Preview: ', chalk`{rgb(0, 255, 255) http://localhost:${port}${publicPath}}`);
    if (report) {
      console.log('- Report:  ', chalk`{rgb(0, 255, 255) http://localhost:${port}${publicPath}report.html}`);
    }
  });
} else {
  sh(`vue-cli-service build ${args}`);
}
