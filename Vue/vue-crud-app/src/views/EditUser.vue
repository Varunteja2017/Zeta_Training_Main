<template>
  <h2>Edit User</h2>

  <input v-model="name" />
  <input v-model="email" />
  <button @click="updateUser">Update</button>
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
  mounted() {
    const id = this.$route.params.id
    api.getUser(id).then(res => {
      this.name = res.data.name
      this.email = res.data.email
    })
  },
  methods: {
    updateUser() {
      const id = this.$route.params.id
      api.updateUser(id, {
        name: this.name,
        email: this.email
      }).then(() => {
        this.$router.push('/users')
      })
    }
  }
}
</script>
