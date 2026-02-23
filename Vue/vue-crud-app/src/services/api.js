import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:3000'
})

export default {
  getUsers() {
    return api.get('/users')
  },

  getUser(id) {
    return api.get(`/users/${id}`)
  },

  addUser(user) {
    return api.post('/users', user)
  },

  updateUser(id, user) {
    return api.put(`/users/${id}`, user)
  },

  deleteUser(id) {
    return api.delete(`/users/${id}`)
  }
}
