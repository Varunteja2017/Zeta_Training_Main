<template>
  <h2>Add User</h2>

  <input v-model="name" placeholder="Name" />
  <input v-model="email" placeholder="Email" />
  <div>
    <label>
      <input type="radio" value="Male" v-model="gender" />
      Male
    </label>

    <label>
      <input type="radio" value="Female" v-model="gender" />
      Female
    </label>
  </div>

  <button @click="saveUser">Save</button>
</template>

<script>
import api from '../services/api'

export default {
  data() {
    return {
      name: '',
      email: '',
      gender: ''
    }
  },
  methods: {
    saveUser() {
      api.addUser({
        name: this.name,
        email: this.email,
        gender: this.gender
      }).then(() => {
        this.$router.push('/users')
      })
    }
  }
}
</script>