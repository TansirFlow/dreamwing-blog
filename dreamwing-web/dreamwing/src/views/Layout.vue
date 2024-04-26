<script setup>
import { ref } from 'vue'
import { CirclePlus, Search, Link, House, Clock, User } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
const activeIndex = ref('1')
const handleSelect = (key, keyPath) => {
    console.log(key, keyPath)
}

const dialogVisible = ref(false)

const searchInput = ref('')

const gridData = [
    {
        date: '2016-05-02',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District',
    },
    {
        date: '2016-05-04',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District',
    },
    {
        date: '2016-05-01',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District',
    },
    {
        date: '2016-05-03',
        name: 'John Smith',
        address: 'No.1518,  Jinshajiang Road, Putuo District',
    },
]

const tableData = [
    {
        date: '2016-05-03',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-02',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-04',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-01',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
]


const contents = [
    {
        id: '1',
        title: '计算机科学与技术学院举办创新创业校友联盟筹备会暨校企合作论坛',
        info: '4月20日下午，计算机科学与技术学院在磬苑校区理工D楼318会议室举办创新创业校友联盟筹备会暨校企合作论坛，1988届校友、安徽省友联电力电子工程有限公司总经理林雪松，2010届校友、安徽墨荷信息技术有限公司总经理汪伯宇等10位校友参加活动。学院党委书记张兴义，副院长汪志宏、吕钊、江波和学院校友工作联络人、部分辅导员参加。活动由汪志宏主持。',
        viewNum: 30,
        talkNum: 9,
        createTime: '2023-1-1',
        author: '作者1',
        tags: [
            'tag1', 'tag2', 'tag3'
        ]
    },
    {
        id: '2',
        title: '我院生物信息感知与人机交互团队成果被国际顶级会议IJCAI 2024录用',
        info: '近期，我院生物信息感知与人机交互研究所(IIP-HCI)范存航副教授、吕钊教授指导的论文《DBPNet: Dual-Branch Parallel Network with Temporal-Frequency Fusion for Auditory Attention Detection》被国际人工智能联合会议（IJCAI）2024（CCF A类会议）录用。论文的第一作者为我院2021级本科生倪钦科（信息技术英才班）和2022级研究生张宏玉。IJCAI是人工智能领域中最主要的学术会议之一，是CCF推荐A类会议，一直享有盛名，2023年该会议接收稿件4566份，录用643份，录用率仅为14%。鸡尾酒会场景通常指的是在多人说话的环境中，需要从众多声音中分辨出并专注于某个特定的说话人的声音。这种场景下的挑战在于，存在多个声音源时，如何有效地分离和提取出听者感兴趣的目标声音源。这就是为什么需要解决鸡尾酒会场景问题。听觉注意力检测技术提供了一种解决方案，它利用大脑信号来模拟人类听觉系统中的“注意力”过程，通过这种方式，可以识别并定位在多人说话环境中哪个说话人引起了听者的关注，即确定当前的“目标”说话人。这对于改善听障人士的听觉感知能力、提升听力辅助设备的性能以及开发更智能的语音交互系统都具有重要意义。该论文提出了一种时频融合的双分支并行网络实现基于EEG的高性能听觉注意检测，该方法在显著提高了听觉注意解码精度的同时，减少了训练参数，有助于推动AAD任务的未来研究。',
        viewNum: 12,
        talkNum: 20,
        createTime: '2023-1-1',
        author: '作者2',
        tags: [
            'tag1', 'tag2', 'tag3'
        ]
    },
    {
        id: '3',
        title: '我院2位学者入选爱思唯尔2023“中国高被引学者”榜单',
        info: '2024年3月27日，爱思唯尔（Elsevier）发布2023“中国高被引学者” (Highly Cited Chinese Researchers) 榜单，来自496所高校、企业及科研机构的5801人上榜，其中计算机科学与技术学科共262人上榜。我院张兴义教授、田野副教授2位学者入选计算机科学与技术学科榜单。 爱思唯尔“中国高被引学者”榜单不仅致力于系统性地展示中国科研领域的人才分布现状，而且精准呈现各机构、高校优势学科构成及学术影响力，以及在关键技术研究和各重点领域的顶尖人才。该榜单采用多种指标，展示了学者的研究成果在学术或科研领域的影响力，帮助学者建立学术声誉。',
        viewNum: 1,
        talkNum: 5,
        createTime: '2023-1-1',
        author: '作者3',
        tags: [
            'tag1', 'tag2', 'tag3'
        ]
    },
    {
        id: '3',
        title: '我院2位学者入选爱思唯尔2023“中国高被引学者”榜单',
        info: '2024年3月27日，爱思唯尔（Elsevier）发布2023“中国高被引学者” (Highly Cited Chinese Researchers) 榜单，来自496所高校、企业及科研机构的5801人上榜，其中计算机科学与技术学科共262人上榜。我院张兴义教授、田野副教授2位学者入选计算机科学与技术学科榜单。 爱思唯尔“中国高被引学者”榜单不仅致力于系统性地展示中国科研领域的人才分布现状，而且精准呈现各机构、高校优势学科构成及学术影响力，以及在关键技术研究和各重点领域的顶尖人才。该榜单采用多种指标，展示了学者的研究成果在学术或科研领域的影响力，帮助学者建立学术声誉。',
        viewNum: 1,
        talkNum: 5,
        createTime: '2023-1-1',
        author: '作者3',
        tags: [
            'tag1', 'tag2', 'tag3'
        ]
    },
    {
        id: '3',
        title: '我院2位学者入选爱思唯尔2023“中国高被引学者”榜单',
        info: '2024年3月27日，爱思唯尔（Elsevier）发布2023“中国高被引学者” (Highly Cited Chinese Researchers) 榜单，来自496所高校、企业及科研机构的5801人上榜，其中计算机科学与技术学科共262人上榜。我院张兴义教授、田野副教授2位学者入选计算机科学与技术学科榜单。 爱思唯尔“中国高被引学者”榜单不仅致力于系统性地展示中国科研领域的人才分布现状，而且精准呈现各机构、高校优势学科构成及学术影响力，以及在关键技术研究和各重点领域的顶尖人才。该榜单采用多种指标，展示了学者的研究成果在学术或科研领域的影响力，帮助学者建立学术声誉。',
        viewNum: 1,
        talkNum: 5,
        createTime: '2023-1-1',
        author: '作者3',
        tags: [
            'tag1', 'tag2', 'tag3'
        ]
    },
    {
        id: '3',
        title: '我院2位学者入选爱思唯尔2023“中国高被引学者”榜单',
        info: '2024年3月27日，爱思唯尔（Elsevier）发布2023“中国高被引学者” (Highly Cited Chinese Researchers) 榜单，来自496所高校、企业及科研机构的5801人上榜，其中计算机科学与技术学科共262人上榜。我院张兴义教授、田野副教授2位学者入选计算机科学与技术学科榜单。 爱思唯尔“中国高被引学者”榜单不仅致力于系统性地展示中国科研领域的人才分布现状，而且精准呈现各机构、高校优势学科构成及学术影响力，以及在关键技术研究和各重点领域的顶尖人才。该榜单采用多种指标，展示了学者的研究成果在学术或科研领域的影响力，帮助学者建立学术声誉。',
        viewNum: 1,
        talkNum: 5,
        createTime: '2023-1-1',
        author: '作者3',
        tags: [
            'tag1', 'tag2', 'tag3'
        ]
    },
]

const pictures = ref([
    {
        id: 1,
        path: '../assets/1.webp'
    },
    {
        id: 2,
        path: '../assets/2.jpg'
    },
    {
        id: 3,
        path: '../assets/3.webp'
    },
    {
        id: 4,
        path: '../assets/4.webp'
    },
])
const exportImgSrc = (src) => {
    if (src) {
        return new URL(`${src}`, import.meta.url).href;
    } else {
        return new URL(`../assets/icon/default.png`, import.meta.url).href;
    }
}
</script>

<template>
    <div style="position: fixed;z-index:999;border:0px solid red;width:100%;height:100px">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :ellipsis="false"
            @select="handleSelect" style="height: 70px;position:flxed" background-color="rgba(255,255,255,0.5)"
            :style="{ backdropFilter: `blur(10px)` }">
            <el-menu-item index="0">
                <img style="width: 100px" src="../assets/element-plus-logo.svg" alt="Element logo" />
            </el-menu-item>
            <el-menu-item index="4" style="font-size: 21px;">
                <el-icon :size="20">
                    <House />
                </el-icon>
                首页
            </el-menu-item>
            <el-menu-item index="5" style="font-size: 21px;">
                <el-icon :size="20">
                    <Link />
                </el-icon>
                友链
            </el-menu-item>
            <div class="flex-grow" />
            <el-sub-menu index="1">
                <template #title><el-avatar/></template>
                <el-menu-item index="1-1">管理后台</el-menu-item>
                <el-menu-item index="1-2">个人中心</el-menu-item>
                <el-menu-item index="1-3">退出登录</el-menu-item>
            </el-sub-menu>
            <el-menu-item index="2">
                <el-button size="large" :icon="Search" circle @click="dialogVisible = true" />
            </el-menu-item>
            <el-menu-item index="3">
                <el-button size="large" :icon="CirclePlus" type="primary" round>发布文章</el-button>
            </el-menu-item>
        </el-menu>
    </div>

    <el-scrollbar height="100vh">
        <div class="block text-center">
            <el-carousel motion-blur height="100vh">
                <el-carousel-item v-for="(picture,key) in pictures" :key="key">
                    <img style="width: 100%;height: 100%;" :src="exportImgSrc(picture.path)" />
                </el-carousel-item>
            </el-carousel>
        </div>
        <el-row :style="{ border: `0px solid red`, top: `100px` }" justify="center">
            <el-col :style="{ border: `0px solid red` }" :span="18">
                <el-row :style="{ border: `0px solid red` }" :gutter="0" justify="space-between">
                    <el-col :style="{ border: `0px solid red`, background: `none` }" :span="17"><!--文章条目-->
                        <div v-for="item in contents" :key="item.id">
                            <el-card style="width: 96%;">
                                <div class="card-header">
                                    <el-text class="mx-1" size="large" style="font-size: 21px;line-height: 2;"
                                        truncated>
                                        <b>{{ item.title }}</b>
                                    </el-text>
                                </div>
                                <el-text line-clamp="2" :style="{ whiteSpace: `normal` }" size="large" truncated>
                                    {{ item.info }}
                                </el-text>
                                <template #footer>
                                    <el-row justify="space-between">
                                        <el-col :span="3" style="color:#b1b3b8">
                                            <el-text class="mx-1" type="info" size="large" truncated style="cursor: pointer;" @click="ElMessage.success(item.author)">
                                                <el-icon>
                                                    <User />
                                                </el-icon>
                                                {{ item.author }}
                                            </el-text>
                                        </el-col>
                                        <el-col :span="17">
                                            <el-space size="large">
                                                <el-check-tag :checked="true" type="primary" v-for="tags in item.tags" @click="ElMessage.success(tags)">{{ tags }}</el-check-tag>
                                            </el-space>
                                        </el-col>
                                        <el-col :span="4" style="color:#b1b3b8">
                                            <el-text class="mx-1" type="info" style="float: right" size="large"
                                                truncated>
                                                <el-icon>
                                                    <Clock />
                                                </el-icon>
                                                {{ item.createTime }}
                                            </el-text>
                                        </el-col>
                                    </el-row>
                                </template>
                            </el-card>
                            <br>
                        </div>
                    </el-col>
                    <el-col :style="{ border: `0px solid red` }" :span="7"><!--右侧栏-->
                        <el-card style="width: 100%;">
                            <el-row justify="center">
                                <img style="width: 100px" src="../assets/element-plus-logo.svg" alt="Element logo" />
                            </el-row>
                        </el-card>
                    </el-col>
                </el-row>
                <el-footer class="footer">
                    <!-- 版权信息 -->
                    © 2024 Tansor Blog. All rights reserved.
                </el-footer>
            </el-col>
        </el-row>
    </el-scrollbar>


    <el-dialog v-model="dialogVisible" title="搜索" width="500">
        <el-row :gutter="10" justify="space-between">
            <el-col :span="20">
                <el-input v-model="searchInput" style="width: 100%" placeholder="输入搜索内容" />
            </el-col>
            <el-col :span="4">
                <el-button type="primary" style="width: 100%" plain>搜索</el-button>
            </el-col>
        </el-row>
        <el-row>
            <el-col>
                <el-table :data="tableData" style="width: 100%">
                    <el-table-column prop="date" label="序号" width="120px" />
                    <el-table-column prop="name" label="标题" width="100px" />
                    <el-table-column prop="address" label="匹配" />
                </el-table>
            </el-col>
        </el-row>
    </el-dialog>
    <el-backtop :right="100" :bottom="100" />
</template>

<style>
.flex-grow {
    flex-grow: 1;
}

.footer {
    text-align: center;
    background-color: #f5f5f5;
    padding: 20px;
}
</style>
