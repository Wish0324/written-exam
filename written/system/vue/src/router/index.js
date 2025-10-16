import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/backend/homepage' },
    {
      path: '/backend', component: () => import('@/views/Backend.vue'),
      children: [
        { path: 'homepage', component: () => import('@/views/backend/Homepage.vue') },
        { path: 'announcement', component: () => import('@/views/backend/Announcement.vue') },
        { path: 'admin', component: () => import('@/views/backend/Admin.vue') },
        { path: 'personal-center', component: () => import('@/views/backend/PersonalCenter.vue') }
      ]
    },
    {
      path: '/frontend', component: () => import('@/views/Frontend.vue'),
      children: [
        { path: 'homepage', component: () => import('@/views/frontend/Homepage.vue') },
        { path: 'personal-center', component: () => import('@/views/frontend/PersonalCenter.vue') }
      ]
    },
    { path: '/login', component: () => import('@/views/Login.vue') },
    { path: '/404', component: () => import('@/views/404.vue') },
    { path: '/:pathMatch(.*)', redirect: '/404' }
  ]
})

export default router