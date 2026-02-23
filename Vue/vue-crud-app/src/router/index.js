import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Users from '../views/Users.vue'
import AddUser from '../views/AddUser.vue'
import EditUser from '../views/EditUser.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/users', component: Users },
  { path: '/add', component: AddUser },
  { path: '/edit/:id', component: EditUser }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
