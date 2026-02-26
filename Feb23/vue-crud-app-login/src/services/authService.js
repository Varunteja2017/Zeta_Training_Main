import axios from 'axios'

// Create axios instance
const api = axios.create({
  baseURL: 'http://localhost:3000'
})

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token")

    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }

    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

export default {

  async login(email, password) {
    try {
      const response = await api.get('/users', {
        params: { email, password }
      })

      if (response.data.length > 0) {
        const user = response.data[0]
        const fakeToken = "fake-jwt-token"

        // Store user + token
        localStorage.setItem("user", JSON.stringify(user))
        localStorage.setItem("token", fakeToken)

        return user
      } else {
        throw new Error("Invalid credentials")
      }

    } catch (error) {
      throw new Error("Login failed")
    }
  },

  async register(user) {
    try {
      const response = await api.post('/users', user)
      return response.data
    } catch (error) {
      throw new Error("Registration failed")
    }
  },

  logout() {
    localStorage.removeItem("user")
    localStorage.removeItem("token")
  },

  getCurrentUser() {
    return JSON.parse(localStorage.getItem("user"))
  },

  isAuthenticated() {
    return !!localStorage.getItem("token")
  }
}