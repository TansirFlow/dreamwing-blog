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
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
const md = new MarkDownIt()
import Prism from 'prismjs';
VMdPreview.use(vuepressTheme, {
  Prism,
  extend(md) {
    // md为 markdown-it 实例，可以在此处进行修改配置,并使用 plugin 进行语法扩展
    // md.set(option).use(plugin);
  },
});

app.use(VMdPreview);


pinia.use(persist)
app.use(pinia)
app.use(router)
app.use(ElementPlus);
app.mount('#app')
