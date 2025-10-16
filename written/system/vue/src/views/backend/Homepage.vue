<template>
  <div>

    <div class="card" style="display: flex; justify-content: center">
      <div style="width: 80%">
        <div class="homepage-title">公告</div>
        <el-timeline style="margin-left: -40px">
          <el-timeline-item v-for="(item, index) in data.announcementData"
                            :timestamp="item.time" :key="index"
                            placement="top" size="large" type="info">
            <div class="homepage-card">
              {{ item.content }}
            </div>
          </el-timeline-item>
        </el-timeline>
      </div>
    </div>

  </div>
</template>

<script setup>
import { reactive } from "vue";
import {ElMessage} from "element-plus";
import request from "@/utils/request.js";

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  announcementData: []
})

const loadAnnouncement = () => {
  request.get('/announcement/selectAll').then(res => {
    if (res.code === '200') {
      data.announcementData = res.data
    } else {
      ElMessage.error(res.message)
    }
  })
}

loadAnnouncement()
</script>

<style scoped>
.homepage-title {
  text-align: center;
  font-weight: bold;
  font-size: 20px;
  color: #4288B2;
  margin-top: 2px;
  margin-bottom: 20px;
}

.el-timeline-item {
  padding-bottom: 8px;
}

:deep(.el-timeline-item__tail) {
  border-left: 2px solid #C0C0C0;
}

:deep(.el-timeline-item__timestamp) {
  font-size: 14px;
  margin-bottom: 12px;
}

.homepage-card {
  width: 100%;
  border-radius: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  font-size: 15px;
  background-color: #FFFFFF;
  padding: 20px;
}
</style>