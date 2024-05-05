import './assets/main.scss'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from '@/router'
import App from './App.vue'
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'
const app = createApp(App);
const pinia = createPinia()
const persist = createPersistedState()

// ------------------------------------完全可用-----------------------------------------
// import VMdPreview from '@kangc/v-md-editor/lib/preview';
// import '@kangc/v-md-editor/lib/style/preview.css';
// import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
// import '@kangc/v-md-editor/lib/theme/style/github.css';

// // highlightjs
// import hljs from 'highlight.js';

// VMdPreview.use(githubTheme, {
//   Hljs: hljs,
// });
// -------------------------------------------------------------------------------------

import MarkDownIt from 'markdown-it'
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index'
import createCopyCodePlugin from '@kangc/v-md-editor/lib/plugins/copy-code/index'
import createEmojiPlugin from '@kangc/v-md-editor/lib/plugins/emoji/index'
import createKatexPlugin from '@kangc/v-md-editor/lib/plugins/katex/npm'
import '@kangc/v-md-editor/lib/style/base-editor.css';
import '@kangc/v-md-editor/lib/style/preview.css'
import '@kangc/v-md-editor/lib/theme/style/vuepress.css'
import '@kangc/v-md-editor/lib/plugins/copy-code/copy-code.css'
import '@kangc/v-md-editor/lib/plugins/emoji/emoji.css'
import Prism from 'prismjs'
import 'prismjs/components/prism-json'
import 'katex/dist/katex.min.css'
import createMermaidPlugin from '@kangc/v-md-editor/lib/plugins/mermaid/cdn';
import '@kangc/v-md-editor/lib/plugins/mermaid/mermaid.css';
const md = new MarkDownIt()
VMdPreview.use(vuepressTheme, {
  Prism,
  extend(md) {
    // md为 markdown-it 实例，可以在此处进行修改配置,并使用 plugin 进行语法扩展
    // md.set(option).use(plugin);
  },
});
// 代码行数
VMdPreview.use(createLineNumbertPlugin())
// 代码快速复制
VMdPreview.use(createCopyCodePlugin())
// emoji
VMdPreview.use(createEmojiPlugin())
// KaTex
VMdPreview.use(createKatexPlugin())
// 画图
VMdPreview.use(createMermaidPlugin())
app.use(VMdPreview);


pinia.use(persist)
app.use(pinia)
app.use(router)
app.use(ElementPlus);
app.mount('#app')
