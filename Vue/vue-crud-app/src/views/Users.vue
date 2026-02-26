<template>
  <h2>Users</h2>

  <ul>
    <li v-for="user in users" :key="user.id">
      {{ user.name }} - {{ user.email }}- {{ user.gender }}
      <button @click="editUser(user.id)">Edit</button>
      <button @click="removeUser(user.id)">Delete</button>
    </li>
  </ul>
</template>

<script>
import api from '../services/api'

export default {
  data() {
    return { users: [] }
  },
  mounted() {
    this.loadUsers()
  },
  methods: {
    loadUsers() {
      api.getUsers().then(res => {
        this.users = res.data
      })
    },
    editUser(id) {
      this.$router.push(`/edit/${id}`)
    },
    removeUser(id) {
      api.deleteUser(id).then(() => {
        this.loadUsers()
      })
    }
  }
}
</script>
