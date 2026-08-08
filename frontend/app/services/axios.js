import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL
})

// Attach the JWT from the auth store to every outgoing request.
api.interceptors.request.use((config) => {
  const authStore = useAuthStore()

  if (authStore.token) {
    config.headers.Authorization = `Bearer ${authStore.token}`
  }

  return config
})

// Centralized 401 handling -- clears the session and sends the user back to /login
// instead of letting every page/store implement its own auth-expiry logic.
api.interceptors.response.use(
  response => response,
  (error) => {
    if (error.response?.status === 401) {
      const authStore = useAuthStore()
      authStore.logout()
      navigateTo('/login')
    }

    return Promise.reject(error)
  }
)

export default api
