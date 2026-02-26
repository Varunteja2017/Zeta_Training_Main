<template>
  <h2>Dashboard</h2>
  <p>Welcome {{ user.email }}</p>
  <button @click="logout">Logout</button>
</template>

<script>
import authService from '../services/authService'

export default {
  data() {
    return {
      user: authService.getCurrentUser()
    }
  },
  methods: {
    logout() {
      authService.logout()
      this.$router.push('/login')
    }
  }
}
</script>
