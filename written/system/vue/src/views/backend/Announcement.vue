<template>
  <div>

    <div class="card">
      <div style="display: flex; margin-bottom: 20px">
        <el-button @click="handleAdd" plain :icon="Plus" type="primary"></el-button>
        <el-button @click="delBatch" plain :icon="Minus" type="danger" style="margin-left: 18px"></el-button>
        <el-input v-model="data.title"
                  placeholder="输入标题" clearable
                  style="width: 250px; margin-left: 18px"></el-input>
        <el-button @click="load" plain :icon="Search" type="primary" style="margin-left: 18px"></el-button>
        <el-button @click="reset" plain :icon="Refresh" type="warning" style="margin-left: 18px"></el-button>
      </div>
      <div style="margin-bottom: 20px">
        <el-table @selection-change="handleSelectionChange" :data="data.tableData" stripe show-overflow-tooltip>
          <el-table-column type="selection" />
          <el-table-column prop="title" label="标题" />
          <el-table-column prop="content" label="正文" />
          <el-table-column prop="time" width="160" label="时间" />
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
      <div style="text-align: center; font-size: 18px; margin-top: 2px; margin-bottom: 34px">公告</div>
      <div style="display: flex; justify-content: center; margin-bottom: 40px">
        <div style="width: 80%">
          <el-input v-model="data.form.title" size="large" placeholder="标题" clearable style="margin-bottom: 10px"></el-input>
          <el-input v-model="data.form.content" type="textarea" :rows="8" placeholder="正文"></el-input>
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

const data = reactive({
  form: {},
  formVisible: false,
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  title: null,
  ids: []
})

const load = () => {
  request.get('/announcement/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      title: data.title
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
  request.post('/announcement/add', data.form).then(res => {
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
  request.put('/announcement/update', data.form).then(res => {
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
    request.delete('/announcement/delete/' + id).then(res => {
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
    request.delete("/announcement/delete/batch", {data: data.ids}).then(res => {
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

const reset = () => {
  data.title = null
  load()
}

load()
</script>