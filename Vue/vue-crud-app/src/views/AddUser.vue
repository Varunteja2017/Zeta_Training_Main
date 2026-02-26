<template>
  <h2>Add User</h2>

  <input v-model="name" placeholder="Name" />
  <input v-model="email" placeholder="Email" />
  <input type="radio" value="Male" v-model="gender" />
  <label> Male </label>
  <input type="radio" value="Female" v-model="gender" />
  <label> Female </label>
  <input type="checkbox" v-model="skills" value="JavaScript" />
  <label> JavaScript </label>
  <input type="checkbox" v-model="skills" value="Python" />
  <label> Python </label>
  <input type="checkbox" v-model="skills" value="Java" />
  <label> Java </label>


  <button @click="saveUser">Save</button>
</template>

<script>
import api from '../services/api'

export default {
  data() {
    return {
      name: '',
      email: '',
      gender: '',
      skills: []
    }
  },
  methods: {
    saveUser() {
      api.addUser({
        name: this.name,
        email: this.email,
        gender: this.gender,
        skills: this.skills
      }).then(() => {
        this.$router.push('/users')
      })
    }
  }
}
</script>