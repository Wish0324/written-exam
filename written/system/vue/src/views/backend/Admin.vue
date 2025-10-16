<template>
  <div>

    <div class="card">
      <div style="display: flex; margin-bottom: 20px">
        <el-button @click="handleAdd" plain :icon="Plus" type="primary"></el-button>
        <el-button @click="delBatch" plain :icon="Minus" type="danger" style="margin-left: 18px"></el-button>
        <el-input v-model="data.name"
                  placeholder="输入姓名" clearable
                  style="width: 250px; margin-left: 18px"></el-input>
        <el-button @click="load" plain :icon="Search" type="primary" style="margin-left: 18px"></el-button>
        <el-button @click="reset" plain :icon="Refresh" type="warning" style="margin-left: 18px"></el-button>
      </div>
      <div style="margin-bottom: 20px">
        <el-table @selection-change="handleSelectionChange" :data="data.tableData" stripe show-overflow-tooltip>
          <el-table-column type="selection" />
          <el-table-column prop="username" label="账号" />
          <el-table-column prop="name" label="姓名" />
          <el-table-column prop="avatar" label="头像">
            <template v-slot="scope">
              <el-image v-if="scope.row.avatar" :src="scope.row.avatar"
                        :preview-src-list="[scope.row.avatar]" preview-teleported
                        style="display: block; height: 32px; width: 32px; border-radius: 4px"></el-image>
            </template>
          </el-table-column>
          <el-table-column prop="phone" label="电话" />
          <el-table-column prop="email" label="邮箱" />
          <el-table-column fixed="right" width="160" label="操作">
            <template v-slot="scope">
              <el-button @click="handleEdit(scope.row)" plain :icon="Edit" type="primary"></el-button>
              <el-button @click="del(scope.row.id)" plain :icon="Delete" type="danger" style="margin-left: 18px"></el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="data.total">
        <el-pagination @current-change="load"
                       v-model:current-page="data.pageNum" :page-size="data.pageSize" :total="data.total"
                       layout="prev, pager, next" background />
      </div>
    </div>

    <el-dialog v-model="data.formVisible" destroy-on-close align-center width="40%">
      <div style="text-align: center; font-size: 18px; margin-top: 2px; margin-bottom: 34px">管理员</div>
      <div style="display: flex; justify-content: center; margin-bottom: 40px">
        <div style="width: 80%">
          <el-input v-model="data.form.username" size="large" placeholder="账号" clearable style="margin-bottom: 10px"></el-input>
          <el-input v-model="data.form.name" size="large" placeholder="姓名" clearable style="margin-bottom: 10px"></el-input>
          <div style="display: flex">
            <el-upload :on-success="handleFileUpload" :action="baseUrl + '/files/upload'" list-type="picture">
              <el-button plain type="primary" style="height: 40px">头像</el-button>
            </el-upload>
            <div style="font-size: 12px; color: #C0C0C0; margin-top: 10px; margin-left: 10px">小于等于1MB的JPG/PNG文件</div>
          </div>
          <el-input v-model="data.form.phone" size="large" placeholder="电话" clearable style="margin-bottom: 10px"></el-input>
          <el-input v-model="data.form.email" size="large" placeholder="邮箱" clearable></el-input>
        </div>
      </div>
      <div style="text-align: center; margin-bottom: 24px">
        <el-button @click="data.formVisible = false"
                   plain type="primary"
                   style="height: 40px; width: 80px; margin-right: 75px">取消</el-button>
        <el-button @click="save"
                   plain type="primary"
                   style="height: 40px; width: 80px; margin-left: 75px">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script setup>
import {Delete, Edit, Minus, Plus, Refresh, Search} from "@element-plus/icons-vue";
import { reactive } from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";

const baseUrl = import.meta.env.VITE_BASE_URL

const data = reactive({
  form: {},
  formVisible: false,
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  name: null,
  ids: []
})

const load = () => {
  request.get('/admin/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list || []
      data.total = res.data?.total
    }
  })
}

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const add = () => {
  request.post('/admin/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.message)
    }
  })
}

const update = () => {
  request.put('/admin/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      data.formVisible = false
      load()
    }
  })
}

const save = () => {
  data.form.id ? update() : add()
}

const del = (id) => {
  ElMessageBox.confirm('确定删除吗', '警告', { type: 'warning' }).then(res => {
    request.delete('/admin/delete/' + id).then(res => {
      if (res.code === '200') {
        ElMessage.success("操作成功")
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}

const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning("未选择数据")
    return
  }
  ElMessageBox.confirm('确定删除吗', '警告', { type: 'warning' }).then(res => {
    request.delete("/admin/delete/batch", {data: data.ids}).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        load()
      } else {
        ElMessage.error(res.message)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}

const handleSelectionChange = (rows) => {
  data.ids = rows.map(v => v.id)
}

const handleFileUpload = (res) => {
  data.form.avatar = res.data
}

const reset = () => {
  data.name = null
  load()
}

load()
</script>