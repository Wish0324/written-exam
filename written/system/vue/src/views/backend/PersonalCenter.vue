<template>
  <div>

    <div class="card" style="display: flex; justify-content: center">
      <div style="width: 50%">
        <el-upload :on-success="handleFileUpload" :action="baseUrl + '/files/upload'"
                   :show-file-list="false"
                   class="avatar-uploader">
          <img v-if="data.user.avatar" :src="data.user.avatar" alt="" class="avatar" />
          <el-icon v-else class="icon"><Plus /></el-icon>
        </el-upload>
        <el-input v-model="data.user.username" size="large" placeholder="账号" disabled style="margin-bottom: 20px"></el-input>
        <el-input v-model="data.user.name" size="large" placeholder="姓名" clearable style="margin-bottom: 20px"></el-input>
        <el-input v-model="data.user.phone" size="large" placeholder="电话" clearable style="margin-bottom: 20px"></el-input>
        <el-input v-model="data.user.email" size="large" placeholder="邮箱" clearable style="margin-bottom: 20px"></el-input>
        <div style="text-align: center; margin-bottom: 40px">
          <el-button @click="dialogVisible" plain size="large" type="warning" style="width: 100%">修改密码</el-button>
        </div>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button @click="update" plain type="primary" style="height: 40px; width: 100px; font-size: 15px">保存</el-button>
        </div>
      </div>
    </div>

    <el-dialog v-model="data.formVisible" destroy-on-close align-center width="40%">
      <div style="text-align: center; font-size: 18px; margin-top: 2px; margin-bottom: 34px">修改密码</div>
      <div style="display: flex; justify-content: center; margin-bottom: 22px">
        <div style="width: 80%">
          <el-form :model="data.user" :rules="data.rules" ref="formRef">
            <el-form-item prop="password" style="margin-bottom: 20px">
              <el-input v-model="data.user.password" size="large" placeholder="原密码" clearable show-password></el-input>
            </el-form-item>
            <el-form-item prop="newPassword" style="margin-bottom: 20px">
              <el-input v-model="data.user.newPassword" size="large" placeholder="新密码" clearable show-password></el-input>
            </el-form-item>
            <el-form-item prop="confirmNewPassword">
              <el-input v-model="data.user.confirmNewPassword" size="large" placeholder="新密码" clearable show-password></el-input>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div style="text-align: center; margin-bottom: 24px">
        <el-button @click="data.formVisible = false"
                   plain type="primary"
                   style="height: 40px; width: 80px; margin-right: 75px">取消</el-button>
        <el-button @click="updatePassword"
                   plain type="primary"
                   style="height: 40px; width: 80px; margin-left: 75px">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";

const baseUrl = import.meta.env.VITE_BASE_URL

const formRef = ref()

const validatePass = (rule, value, callback) => {
  if (!value) {
    callback(new Error('输入新密码'))
  } else {
    if (value !== data.user.newPassword) {
      callback(new Error("两次密码不一致"))
    }
    callback()
  }
}

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  formVisible: false,
  rules: {
    password: [{ required: true, message: '输入原密码', trigger: 'blur' }],
    newPassword: [{ required: true, message: '输入新密码', trigger: 'blur' }],
    confirmNewPassword: [{ validator: validatePass, trigger: 'blur' }],
  }
})

const dialogVisible = () => {
  data.formVisible = true
  data.user.password = ''
  data.user.newPassword = ''
  data.user.confirmNewPassword = ''
}

const handleFileUpload = (res) => {
  data.user.avatar = res.data
}

const emit = defineEmits(['updateUser'])

const update = () => {
  if (data.user.role === 'ADMIN') {
    request.put('/admin/update', data.user).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        localStorage.setItem('xm-user', JSON.stringify(data.user))
        emit('updateUser')
      } else {
        ElMessage.error(res.message)
      }
    })
  }
}

const updatePassword = () => {
  formRef.value.validate(valid => {
    if (valid) {
      request.put('/updatePassword', data.user).then(res => {
        if (res.code === '200') {
          ElMessage.success('操作成功')
          logout()
        } else {
          ElMessage.error(res.message)
        }
      })
    }
  })
}

const logout = () => {
  localStorage.removeItem('xm-user')
  router.push('/login')
}
</script>

<style>
.avatar-uploader {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.avatar-uploader .el-upload {
  position: relative;
  overflow: hidden;
  cursor: pointer;
  border: 1px dashed #C0C0C0;
  border-radius: 10px;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: #4288B2;
}

.avatar {
  height: 100px;
  width: 100px;
}

.el-icon.icon {
  height: 100px;
  width: 100px;
  font-size: 30px;
  color: #C0C0C0;
}

.el-icon.icon:hover {
  color: #4288B2;
}
</style>