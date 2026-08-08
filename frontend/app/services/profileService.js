import api from '~/services/axios'

// Kept in one place so the upload endpoint can be swapped/updated later
// without touching the store or components.
const PROFILE_IMAGE_ENDPOINT = '/candidate/profile/image'

export default {
  async getMyProfile() {
    const response = await api.get('/candidate/profile/me')
    return response.data
  },

  async updateProfile(payload) {
    const response = await api.put('/candidate/profile/me', payload)
    return response.data
  },

  async uploadProfileImage(file, onUploadProgress) {
    const formData = new FormData()
    formData.append('file', file)

    const response = await api.post(PROFILE_IMAGE_ENDPOINT, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      onUploadProgress
    })

    return response.data
  }
}