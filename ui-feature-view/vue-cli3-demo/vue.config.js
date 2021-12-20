module.exports = {
    pages: {
      index: {
        // page 的入口
        entry: 'src/pages/index/main.js',
        // 模板来源
        template: 'public/index.html',
        // 在 dist/index.html 的输出
        filename: 'index.html',
        // 当使用 title 选项时，
        // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
        title: 'Index Page',
        // 在这个页面中包含的块，默认情况下会包含
        // 提取出来的通用 chunk 和 vendor chunk。
        chunks: ['chunk-vendors', 'chunk-common', 'index']
      },
      iview: {
        // page 的入口
        entry: 'src/pages/iview/main.js',
        // 模板来源
        template: 'src/pages/iview/index.html',
        // 在 dist/index.html 的输出
        filename: 'iview.html',
        // 当使用 title 选项时，
        // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
        title: 'MyIview',
        // 在这个页面中包含的块，默认情况下会包含
        // 提取出来的通用 chunk 和 vendor chunk。
        chunks: ['chunk-vendors', 'chunk-common', 'iview']
      },
      router: {
        // page 的入口
        entry: 'src/pages/router/main.js',
        // 模板来源
        template: 'src/pages/router/index.html',
        // 在 dist/router.html 的输出
        filename: 'router.html',
        // 当使用 title 选项时，
        // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
        title: 'MyRouter',
        // 在这个页面中包含的块，默认情况下会包含
        // 提取出来的通用 chunk 和 vendor chunk。
        chunks: ['chunk-vendors', 'chunk-common', 'router']
      },
      slot: {
        // page 的入口
        entry: 'src/pages/slot/main.js',
        // 模板来源
        template: 'src/pages/slot/index.html',
        // 在 dist/router.html 的输出
        filename: 'slot.html',
        // 当使用 title 选项时，
        // template 中的 title 标签需要是 <title><%= htmlWebpackPlugin.options.title %></title>
        title: 'MySlot',
        // 在这个页面中包含的块，默认情况下会包含
        // 提取出来的通用 chunk 和 vendor chunk。
        chunks: ['chunk-vendors', 'chunk-common', 'slot']
      }
    },
    devServer: {
      proxy: {
        '/keep': {
          target: 'http://music.163.com/'
        }
      }
    }
  }