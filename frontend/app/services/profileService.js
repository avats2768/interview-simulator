import api from '~/services/axios'

const PROFILE_ENDPOINT = '/candidate/profile'
const PROFILE_IMAGE_ENDPOINT = '/candidate/profile/me/image'

export default {

  /**
   * Get logged-in candidate profile.
   */
  async getMyProfile() {
    const response = await api.get(
      `${PROFILE_ENDPOINT}/me`
    )

    return response.data
  },

  /**
   * Update logged-in candidate profile.
   */
  async updateProfile(payload) {
    const response = await api.put(
      `${PROFILE_ENDPOINT}/me`,
      payload
    )

    return response.data
  },

  /**
   * Upload / replace profile image.
   *
   * Backend expects:
   * multipart field name = image
   */
  async uploadProfileImage(
    file,
    onUploadProgress
  ) {

    if (!file) {
      throw new Error(
        'Please select an image.'
      )
    }

    const formData = new FormData()

    formData.append(
      'image',
      file
    )

    const response = await api.post(
      PROFILE_IMAGE_ENDPOINT,
      formData,
      {
        onUploadProgress
      }
    )

    return response.data
  },

  /**
   * Delete profile image.
   */
  async deleteProfileImage() {

    const response = await api.delete(
      PROFILE_IMAGE_ENDPOINT
    )

    return response.data
  }
}