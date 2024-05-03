<script setup>
import {
    Menu as IconMenu,
    Location,
    Odometer,
    Document,
    ChatLineRound,
    Folder,
    User,
    Setting,
    FullScreen,
    ChatLineSquare
} from '@element-plus/icons-vue'

import { } from '@vicons/tabler'
import { } from '@vicons/fluent'
import { } from '@vicons/ionicons4'
import { ExitOutline } from '@vicons/ionicons5'
import { } from '@vicons/antd'
import { } from '@vicons/material'
import { } from '@vicons/fa'
import { } from '@vicons/carbon'
import { Icon } from '@vicons/utils'

const handleOpen = (key, keyPath) => {
    console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
    console.log(key, keyPath)
}
</script>
<template>
    <div class="common-layout">
        <el-container>
            <el-aside width="250px" :style="{ background: `white`, height: `100vh`, border: `0px solid red` }">
                <el-menu :style="{ height: `80vh` }" default-active="1" class="el-menu-vertical-demo" @open="handleOpen"
                    @close="handleClose">
                    <br>
                    <el-text class="mx-1" type="primary"
                        :style="{ fontSize: `36px`, display: 'flex', alignItem: `center`, justifyContent: 'center', height: `50px` }">DW
                        BLOG</el-text>
                    <br>
                    <div :style="{ display: 'flex', alignItem: `center`, justifyContent: 'center', height: `60px` }">
                        <el-input v-model="input" style="width: 90%;height:40px;" placeholder="搜索" />
                    </div>
                    <el-menu-item index="1" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Odometer />
                        </el-icon>
                        <span>仪表盘</span>
                    </el-menu-item>
                    <el-menu-item index="2" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Document />
                        </el-icon>
                        <span>文章管理</span>
                    </el-menu-item>
                    <el-menu-item index="3" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <ChatLineSquare />
                        </el-icon>
                        <span>说说管理</span>
                    </el-menu-item>
                    <el-menu-item index="4" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <ChatLineRound />
                        </el-icon>
                        <span>评论管理</span>
                    </el-menu-item>
                    <el-menu-item index="5" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Folder />
                        </el-icon>
                        <span>附件管理</span>
                    </el-menu-item>
                    <el-menu-item index="6" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>用户管理</span>
                    </el-menu-item>
                    <el-menu-item index="7" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Setting />
                        </el-icon>
                        <span>系统设置</span>
                    </el-menu-item>
                    <el-menu-item index="8" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <FullScreen />
                        </el-icon>
                        <span>系统概览</span>
                    </el-menu-item>
                </el-menu>
                <div
                    :style="{ position: `relative`, width: `100%`, height: `60px`, left: '0', bottom: '-130px', border: `0px solid red` }">
                    <el-row :style="{ height: `100%` }">
                        <el-col :span="6" :style="{ border: `0px solid red`}">
                            <el-avatar :size="50" :src="circleUrl" />
                        </el-col>
                        <el-col :span="11" :style="{ border: `0px solid red`}">
                            <el-text class="mx-1" :style="{fontSize:`18px`}">Tansor</el-text>
                            <el-tag type="primary">超级管理员</el-tag>
                        </el-col>
                        <el-col :span="7" :style="{ border: `0px solid red`,lineHeight:`0.8`,textAlign:`center`}">
                            <br>
                            <Icon size="30">
                                <ExitOutline />
                            </Icon>
                        </el-col>
                    </el-row>
                </div>
            </el-aside>
            <el-container>
                <el-header :style="{background:`white`,display: 'flex', alignItem: `center`}">
                    <el-text class="mx-1" :style="{fontSize:`24px`,color:`black`}">Tansor</el-text>
                </el-header>
                <el-main>Main</el-main>
            </el-container>
        </el-container>
    </div>

</template>
<style></style>