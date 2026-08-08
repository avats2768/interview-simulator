export const useAuthStore = defineStore('auth', () => {
  // Cookies (not localStorage) so the session survives a refresh without storing
  // anything in browser storage that isn't already sent to the server anyway.
  const tokenCookie = useCookie('auth_token', { sameSite: 'strict' })
  const userCookie = useCookie('auth_user', { sameSite: 'strict' })

  const user = ref(userCookie.value ?? null)
  const token = ref(tokenCookie.value ?? null)

  const isAuthenticated = computed(() => !!token.value)

  function login(data) {
    user.value = {
      id: data.id,
      uuid: data.uuid,
      username: data.username,
      email: data.email,
      role: data.role,
      emailVerified: data.emailVerified
    }
    token.value = data?.token ?? null

    userCookie.value = user.value
    tokenCookie.value = token.value
  }

  function logout() {
    user.value = null
    token.value = null

    userCookie.value = null
    tokenCookie.value = null
  }

  return { user, token, isAuthenticated, login, logout }
})
