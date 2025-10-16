<template>
  <div class="backend-background">

    <div class="backend-header">
      <div class="backend-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">5D</div>
      </div>
      <div class="backend-header-center"></div>
      <div class="backend-header-right">
        <el-dropdown>
          <div class="user">
            <img v-if="data.user.avatar" :src="data.user.avatar" alt="">
            <img v-else src="@/assets/imgs/avatar.png" alt="">
            <span>{{ data.user.name }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/backend/personal-center')">个人中心</el-dropdown-item>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <div class="backend-body">
      <div class="backend-body-left">
        <el-menu router :default-active="router.currentRoute.value.path">
          <el-menu-item index="/backend/homepage">
            <el-icon><HomeFilled /></el-icon>
            <span>主页</span>
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <el-icon><Platform /></el-icon>
              <span>信息</span>
            </template>
            <el-menu-item index="/backend/announcement">公告</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon><UserFilled /></el-icon>
              <span>角色</span>
            </template>
            <el-menu-item index="/backend/admin">管理员</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div>
      <div class="backend-body-right">
        <RouterView @updateUser="updateUser" />
      </div>
    </div>

  </div>
</template>

<script setup>
import { reactive } from "vue";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}')
})

const logout = () => {
  localStorage.removeItem('xm-user')
  router.push('/login')
}

const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('xm-user') || '{}')
}

if (!data.user.id) {
  logout()
  ElMessage.error('账户未登录')
}
</script>

<style scoped>
@import "@/assets/css/backend.css";
</style>