<template>
    <div>
        <div v-for="anchor in titleList" :style="{ padding: `10px 0 10px ${anchor.indent * 20}px` }"
            @click="rollTo(anchor, index)" :class="index === heightTitle ? 'title-active' : ''">
            <a style="cursor: pointer">{{ anchor.title }}</a>
        </div>
        <v-md-preview :text="text" ref="editor" />
    </div>
</template>
<script>
import { ref, onMounted, nextTick } from 'vue';
import { getArticleDetail } from "@/api/blog";
// markdown-文章内容
const article = ref()
// markdown-对象
const editor = ref(null)
// markdown-文章标题列表
const titleList = ref([])
// markdown-获取内容
async function articleData(DetailID) {
  ……
}
// markdown-生成标题
async function getTitle() {
  ……
}
// markdown-当前高亮的标题index
const heightTitle = ref(0)
// markdown-标题跳转
const rollTo = (anchor, index) => {
    // 获取要跳转的标签的lineIndex
    const { lineIndex } = anchor;
    // 查找lineIndex对应的元素对象
    const heading = editor.value.querySelector(
        `.v-md-editor-preview [data-v-md-line="${lineIndex}"]`
    );
    // 页面跳转
    if (heading) {
        heading.scrollIntoView({ behavior: "smooth", block: "start" })
    }
    // 修改当前高亮的标题
    heightTitle.value = index
}
onMounted(async () => {
    // 获取第一篇文章内容
    await articleData(1)
    // 生成文章标题列表
    await getTitle()
})
</script>
<style></style>