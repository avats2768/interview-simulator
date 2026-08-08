import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import profileService from '~/services/profileService'

export const useProfileStore = defineStore(
  'profile',
  () => {

    const profile = ref(null)

    const loading = ref(false)

    const updating = ref(false)

    const uploading = ref(false)

    const deletingImage = ref(false)

    const uploadProgress = ref(0)

    const error = ref(null)

    /**
     * Full candidate name.
     */
    const fullName = computed(() => {

      if (!profile.value) {
        return ''
      }

      return [
        profile.value.firstName,
        profile.value.lastName
      ]
        .filter(Boolean)
        .join(' ')
    })

    /**
     * Profile completion percentage.
     */
    const completionPercentage = computed(() => {

      return profile.value?.completionPercentage ?? 0
    })

    /**
     * Whether profile is 100% completed.
     */
    const isProfileCompleted = computed(() => {

      return profile.value?.profileCompleted ?? false
    })

    /**
     * Fetch logged-in user's profile.
     */
    async function fetchProfile() {

      loading.value = true

      error.value = null

      try {

        const response =
                await profileService.getMyProfile()

        profile.value =
                response.data

        return response

      } catch (err) {

        error.value =
          err.response?.data?.message
          || 'Unable to load your profile right now.'

        throw err

      } finally {

        loading.value = false
      }
    }

    /**
     * Update candidate profile.
     */
    async function updateProfile(payload) {

      updating.value = true

      error.value = null

      try {

        const response =
                await profileService.updateProfile(
                  payload
                )

        profile.value =
                response.data

        return response

      } catch (err) {

        error.value =
          err.response?.data?.message
          || 'Unable to update your profile right now.'

        throw err

      } finally {

        updating.value = false
      }
    }

    /**
     * Upload / replace profile image.
     */
    async function uploadProfileImage(file) {

      uploading.value = true

      uploadProgress.value = 0

      error.value = null

      try {

        const response =
          await profileService.uploadProfileImage(
            file,
            (event) => {

              if (
                event.total &&
                event.total > 0
              ) {

                uploadProgress.value =
                  Math.round(
                    (event.loaded / event.total) * 100
                  )
              }
            }
          )

        /*
         * Backend returns the complete updated profile.
         *
         * Replace the entire profile so completion
         * percentage and profileCompleted are also updated.
         */
        if (response.data) {

          profile.value =
                  response.data
        }

        return response

      } catch (err) {

        error.value =
          err.response?.data?.message
          || 'Unable to upload your photo right now.'

        throw err

      } finally {

        uploading.value = false

        /*
         * Keep 100% visible briefly if required by UI,
         * otherwise reset after request finishes.
         */
        uploadProgress.value = 0
      }
    }

    /**
     * Delete profile image.
     */
    async function deleteProfileImage() {

      deletingImage.value = true

      error.value = null

      try {

        const response =
          await profileService.deleteProfileImage()

        /*
         * Backend returns updated profile.
         */
        if (response.data) {

          profile.value =
                  response.data
        }

        return response

      } catch (err) {

        error.value =
          err.response?.data?.message
          || 'Unable to delete your photo right now.'

        throw err

      } finally {

        deletingImage.value = false
      }
    }

    return {

      profile,

      loading,

      updating,

      uploading,

      deletingImage,

      uploadProgress,

      error,

      fullName,

      completionPercentage,

      isProfileCompleted,

      fetchProfile,

      updateProfile,

      uploadProfileImage,

      deleteProfileImage
    }
  }
)