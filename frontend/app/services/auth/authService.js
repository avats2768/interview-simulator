import api from '~/services/axios'

export default {

  async login(payload) {
    const { data } = await api.post('/auth/login', payload)
    return data
  },

  async register(payload) {
    const { data } = await api.post('/auth/register', payload)
    return data
  },

  async verifyEmail(token) {
    const { data } = await api.get('/auth/verify-email', {
      params: { token }
    })

    return data
  },

  async resendVerificationEmail(email) {
    const { data } = await api.post('/auth/resend-verification-email', { email })
    return data
  }

}
