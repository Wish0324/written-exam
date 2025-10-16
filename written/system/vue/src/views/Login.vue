<template>
  <div class="login-background">
    <div class="flipped-card" :class="{ flipped: !data.isLogin }">

      <div class="flipped-card-front">
        <div class="login-box">
          <div style="text-align: center; font-weight: bold; font-size: 22px; color: #4288B2; margin-bottom: 60px">登 录</div>
          <el-form :model="data.loginForm" :rules="data.rules" ref="loginForm">
            <el-form-item prop="username" style="margin-bottom: 20px">
              <el-input v-model="data.loginForm.username"
                        :prefix-icon="User" placeholder="账号" clearable
                        size="large"></el-input>
            </el-form-item>
            <el-form-item prop="password" style="margin-bottom: 20px">
              <el-input v-model="data.loginForm.password"
                        :prefix-icon="Key" placeholder="密码" clearable show-password
                        size="large"></el-input>
            </el-form-item>
            <el-form-item prop="role" style="margin-bottom: 60px">
              <el-select v-model="data.loginForm.role" :suffix-icon="Postcard" placeholder="身份" size="large">
                <el-option value="ADMIN" label="管理员"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <el-button @click="login"
                     plain size="large" type="primary"
                     style="width: 100%; font-size: 16px; margin-bottom: 20px">登 录</el-button>
          <el-button @click="flip"
                     plain size="large" type="primary"
                     style="width: 100%; font-size: 16px; margin-left: 0">注 册</el-button>
        </div>
      </div>

      <div class="flipped-card-back">
        <div class="register-box">
          <div style="text-align: center; font-weight: bold; font-size: 22px; color: #4288B2; margin-bottom: 30px">注 册</div>
          <el-form :model="data.registerForm" :rules="data.rules" ref="registerForm">
            <el-form-item prop="username" style="margin-bottom: 20px">
              <el-input v-model="data.registerForm.username"
                        :prefix-icon="User" placeholder="账号" clearable
                        size="large"></el-input>
            </el-form-item>
            <el-form-item prop="password" style="margin-bottom: 20px">
              <el-input v-model="data.registerForm.password"
                        :prefix-icon="Key" placeholder="密码" clearable show-password
                        size="large"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPassword" style="margin-bottom: 20px">
              <el-input v-model="data.registerForm.confirmPassword"
                        :prefix-icon="Key" placeholder="密码" clearable show-password
                        size="large"></el-input>
            </el-form-item>
            <el-form-item prop="role" style="margin-bottom: 60px">
              <el-select v-model="data.registerForm.role" :suffix-icon="Postcard" placeholder="身份" size="large">
                <el-option value="ADMIN" label="管理员"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <el-button @click="register"
                     plain size="large" type="primary"
                     style="width: 100%; font-size: 16px; margin-bottom: 20px">注 册</el-button>
          <el-button @click="flip"
                     plain size="large" type="primary"
                     style="width: 100%; font-size: 16px; margin-left: 0">登 录</el-button>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { reactive, ref, watch } from "vue";
import { useRoute } from 'vue-router';
import {Key, Postcard, User} from "@element-plus/icons-vue";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";
import request from "@/utils/request.js";

const route = useRoute()

const validatePass = (rule, value, callback) => {
  if (!value) {
    callback(new Error('输入密码'))
  } else {
    if (value !== data.registerForm.password) {
      callback(new Error("两次密码不一致"))
    }
    callback()
  }
}

const data = reactive({
  isLogin: true,
  loginForm: {},
  registerForm: {},
  rules: {
    username: [{ required: true, message: '输入账号', trigger: 'blur' }],
    password: [{ required: true, message: '输入密码', trigger: 'blur' }],
    role: [{ required: true, message: '选择身份', trigger: 'blur' }],
    confirmPassword: [{ validator: validatePass, trigger: 'blur' }]
  }
})

const loginForm = ref()
const registerForm = ref()

watch(() => route.query.showRegister, (newVal) => {
  if (newVal === 'true') {
    data.isLogin = false
  }
}, { immediate: true })

const flip = () => {
  data.isLogin = !data.isLogin
  router.replace({ query: null })
}

const login = () => {
  loginForm.value.validate(valid => {
    if (valid) {
      request.post('/login', data.loginForm).then(res => {
        if (res.code === '5004') {
          ElMessage.error('账户不存在')
        }
        if (res.code === '5003') {
          ElMessage.error('账号或密码错误')
        }
        if (res.code === '200') {
          ElMessage.success('操作成功')
          localStorage.setItem('xm-user', JSON.stringify(res.data))
          router.push('/backend/homepage')
        }
      })
    }
  })
}

const register = () => {
  registerForm.value.validate(valid => {
    if (valid) {
      request.post('/register', data.registerForm).then(res => {
        if (res.code === '200') {
          ElMessage.success('操作成功')
        }
      })
    }
  })
}
</script>

<style scoped>
.login-background {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-image: radial-gradient(circle, #F0F8FF, #FFE4E1);
  perspective: 2000px;
}

.flipped-card {
  position: relative;
  height: 540px;
  width: 660px;
  transition: transform 800ms;
  transform-style: preserve-3d;
}

.flipped-card.flipped {
  transform: rotateY(180deg);
}

.flipped-card-front, .flipped-card-back {
  position: absolute;
  height: 100%;
  width: 100%;
  backface-visibility: hidden;
}

.flipped-card-back {
  transform: rotateY(180deg);
}

.login-box, .register-box {
  height: 100%;
  width: 100%;
  border-radius: 60px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: aliceblue;
}

.login-box {
  padding: 60px 100px;
}

.register-box {
  padding: 30px 100px;
}
</style>