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
                    <el-menu-item index="1" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>个人中心</span>
                    </el-menu-item>
                    <el-menu-item index="2" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Document />
                        </el-icon>
                        <span>我的文章</span>
                    </el-menu-item>
                    <el-menu-item index="3" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <ChatLineSquare />
                        </el-icon>
                        <span>我的说说</span>
                    </el-menu-item>
                    <el-menu-item index="4" :style="{ fontSize: `18px`, height: `50px` }">
                        <el-icon>
                            <Folder />
                        </el-icon>
                        <span>我的附件</span>
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