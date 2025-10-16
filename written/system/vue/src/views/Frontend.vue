<template>
  <div>

    <div class="frontend-header">
      <div class="frontend-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">5D</div>
      </div>
      <div class="frontend-header-center">
        <el-menu router :default-active="router.currentRoute.value.path" mode="horizontal">
          <el-menu-item index="/frontend/homepage">主页</el-menu-item>
          <el-menu-item index="/frontend/collection">收藏</el-menu-item>
        </el-menu>
      </div>
      <div class="frontend-header-right">
        <div v-if="!data.user.id">
          <el-button @click="router.push('/login')"
                     plain type="primary"
                     style="height: 40px; width: 80px">登录</el-button>
          <el-button @click="router.push({ path: '/login', query: { showRegister: true } })"
                     plain type="primary"
                     style="height: 40px; width: 80px; margin-left: 10px">注册</el-button>
        </div>
        <div v-else>
        <el-dropdown>
          <div class="user">
            <img v-if="data.user.avatar" :src="data.user.avatar" alt="">
            <img v-else src="@/assets/imgs/avatar.png" alt="">
            <span>{{ data.user.name }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="router.push('/frontend/personal-center')">个人中心</el-dropdown-item>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        </div>
      </div>
    </div>

    <div class="frontend-body">
      <RouterView @updateUser="updateUser" />
    </div>

  </div>
</template>

<script setup>
import router from "@/router/index.js";
import { reactive } from "vue";

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
</script>

<style scoped>
@import "@/assets/css/frontend.css";
</style>