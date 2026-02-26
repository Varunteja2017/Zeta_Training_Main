<template>
  <h2>Register</h2>

  <input v-model="email" placeholder="Email" />
  <input v-model="password" type="password" placeholder="Password" />
  <button @click="register">Register</button>
</template>

<script>
import authService from '../services/authService'

export default {
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    async register() {
      await authService.register({
        email: this.email,
        password: this.password,
        role: 'user'
      })
      this.$router.push('/login')
    }
  }
}
</script>
