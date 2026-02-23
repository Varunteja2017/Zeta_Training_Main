<template>
  <h2>Add User</h2>

  <input v-model="name" placeholder="Name" />
  <input v-model="email" placeholder="Email" />
  <button @click="saveUser">Save</button>
</template>

<script>
import api from '../services/api'

export default {
  data() {
    return {
      name: '',
      email: ''
    }
  },
  methods: {
    saveUser() {
      api.addUser({
        name: this.name,
        email: this.email
      }).then(() => {
        this.$router.push('/users')
      })
    }
  }
}
</script>
