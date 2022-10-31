/** @format */

const path = require('path');
const CompressionPlugin = require('compression-webpack-plugin');
const TerserPlugin = require('terser-webpack-plugin');
const PurgecssPlugin = require('purgecss-webpack-plugin');
const glob = require('glob-all');
const whitelister = require('purgecss-whitelister');

const isProductionEnvFlag = process.env.NODE_ENV === 'production';

const custompaths = {
  host: 'http://localhost:8081/',
  port: 8081,
  publicdomain: 'localhost:8081',
};

module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  lintOnSave: true,
  runtimeCompiler: false,

  // generate sourceMap for production build?
  productionSourceMap: process.env.NODE_ENV !== 'production',

  chainWebpack: (config) => {
    config.resolve.alias
      .set('vue$', 'vue/dist/vue.esm.js')
      .set('@utils', path.join(__dirname, 'src/utils'))
      .set('@assets', path.join(__dirname, 'src/assets'))
      .set('@models', path.join(__dirname, 'src/models'))
      .set('@plugins', path.join(__dirname, 'src/plugins'))
      .set('@services', path.join(__dirname, 'src/services'))
      .set('@components', path.join(__dirname, 'src/components'))
      .set('@css', path.join(__dirname, 'src/css'))
      .set('@views', path.join(__dirname, 'src/views'));

    const splitOptions = config.optimization.get('splitChunks');
    config.optimization.splitChunks(
      Object.assign({}, splitOptions, {
        maxAsyncRequests: 16,
        maxInitialRequests: 5,
        minChunks: 1,
        minSize: 50000,
        maxSize: 245000,
        automaticNameDelimiter: '~',
        name: (module, chunks, cacheGroupKey) => {
          const allChunksNames = chunks.map((chunk) => chunk.name).join('-');
          return allChunksNames;
        },
        cacheGroups: {
          default: false,
          common: {
            name: 'chunk-common',
            minChunks: 2,
            priority: -20,
            chunks: 'initial',
            reuseExistingChunk: true,
          },
          element: {
            name: 'element',
            test: /[\\/]node_modules[\\/]element-ui[\\/]/,
            chunks: 'initial',
            priority: -30,
          },
        },
      })
    );
  },

  transpileDependencies: ['vuetify'],
  configureWebpack: {
    plugins: [
      // https://github.com/FullHuman/purgecss/issues/67
      isProductionEnvFlag
        ? new PurgecssPlugin({
            paths: glob.sync(
              [
                path.join(__dirname, './public/index.html'),
                path.join(__dirname, './**/*.vue'),
                path.join(__dirname, './src/**/*.js'),
                path.join(__dirname, './node_modules/vuetify/src/**/*.ts'),
              ],
              { nodir: true }
            ),
            whitelist: whitelister('node_modules/vuetify/dist/vuetify.min.css'),
          })
        : () => {},
      isProductionEnvFlag ? new CompressionPlugin() : () => {},
      isProductionEnvFlag ? new TerserPlugin() : () => {},
    ],
  },

  // use thread-loader for babel & TS in production build
  // enabled by default if the machine has more than 1 cores
  parallel: require('os').cpus().length > 1,

  // configure webpack-dev-server behavior
  devServer: {
    allowedHosts: ['.localhost', '127.0.0.1'],
    open: process.platform === 'darwin',
    host: '0.0.0.0',
    port: custompaths.port,
    devMiddleware: {
      publicPath: custompaths.host,
    },
    https: false,
    hot: 'only',
    proxy: null, // string | Object
  },

  // options for 3rd party plugins
  pluginOptions: {},
};
