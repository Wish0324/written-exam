<template>
  <div>
    <div class="error-page-background">
      <div>
        <div class="error-code">404</div>
        <div class="error-message">页面已迷失在数字宇宙中</div>
        <div style="text-align: center">
          <el-button @click="toHomepage" round class="homepage-button">主页</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import router from "@/router/index.js";

const toHomepage = () => {
  router.push('/backend/homepage')
}
</script>

<style scoped>
.error-page-background {
  display: flex;
  justify-content: center;
  height: 100vh;
  background-image: radial-gradient(circle, #F0F8FF, #FFE4E1);
}

.error-code {
  font-weight: bold;
  font-size: 200px;
  color: #EEEEEE;
  margin-top: 100px;
  margin-bottom: -20px;
}

.error-message {
  text-align: center;
  font-size: 22px;
  color: #CCCCCC;
}

.homepage-button {
  height: 50px;
  width: 150px;
  font-size: 16px;
  color: #A0A0A0;
  background-color: #C0C0C0;
  margin-top: 40px;
  transform: translateY(0);
  transition: all 320ms ease;
}

.homepage-button:hover {
  color: #FFFFFF;
  background-color: #4288B2;
  transform: translateY(-4px);
}
</style>