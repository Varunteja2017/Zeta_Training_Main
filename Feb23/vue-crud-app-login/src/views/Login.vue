<template>
  <h2>Login</h2>

  <input v-model="email" placeholder="Email" />
  <input v-model="password" type="password" placeholder="Password" />
  <button @click="login">Login</button>

  <p v-if="error">{{ error }}</p>
</template>

<script>
import authService from '../services/authService'

export default {
  data() {
    return {
      email: '',
      password: '',
      error: ''
    }
  },
  methods: {
    async login() {
      try {
        await authService.login(this.email, this.password)
        this.$router.push('/dashboard')
      } catch (err) {
        this.error = err.message
      }
    }
  }
}
</script>
