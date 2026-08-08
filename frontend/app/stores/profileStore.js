import profileService from '~/services/profileService'

export const useProfileStore = defineStore('profile', () => {
  const profile = ref(null)
  const loading = ref(false)
  const updating = ref(false)
  const uploading = ref(false)
  const uploadProgress = ref(0)
  const error = ref(null)

  const fullName = computed(() => {
    if (!profile.value) return ''
    return [profile.value.firstName, profile.value.lastName].filter(Boolean).join(' ')
  })

  const completionPercentage = computed(() => profile.value?.completionPercentage ?? 0)
  const isProfileCompleted = computed(() => profile.value?.profileCompleted ?? false)

  async function fetchProfile() {
    loading.value = true
    error.value = null

    try {
      const response = await profileService.getMyProfile()
      profile.value = response.data
      return response
    } catch (err) {
      error.value = err.response?.data?.message || 'Unable to load your profile right now.'
      throw err
    } finally {
      loading.value = false
    }
  }

  async function updateProfile(payload) {
    updating.value = true
    error.value = null

    try {
      const response = await profileService.updateProfile(payload)
      profile.value = response.data
      return response
    } catch (err) {
      error.value = err.response?.data?.message || 'Unable to update your profile right now.'
      throw err
    } finally {
      updating.value = false
    }
  }

  async function uploadProfileImage(file) {
    uploading.value = true
    uploadProgress.value = 0
    error.value = null

    try {
      const response = await profileService.uploadProfileImage(file, (event) => {
        if (event.total) {
          uploadProgress.value = Math.round((event.loaded / event.total) * 100)
        }
      })

      if (profile.value) {
        profile.value.profileImage = response.data?.profileImage ?? profile.value.profileImage
      }

      return response
    } catch (err) {
      error.value = err.response?.data?.message || 'Unable to upload your photo right now.'
      throw err
    } finally {
      uploading.value = false
      uploadProgress.value = 0
    }
  }

  return {
    profile,
    loading,
    updating,
    uploading,
    uploadProgress,
    error,
    fullName,
    completionPercentage,
    isProfileCompleted,
    fetchProfile,
    updateProfile,
    uploadProfileImage
  }
})