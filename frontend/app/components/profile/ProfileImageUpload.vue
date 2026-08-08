<script setup>
import { ref, computed } from 'vue'
import { useProfileStore } from '~/stores/profileStore'

const profileStore = useProfileStore()

const fileInput = ref(null)

const imagePreview = ref(null)

/**
 * Current image shown by the component.
 */
const src = computed(() => {

  if (imagePreview.value) {
    return imagePreview.value
  }

  return profileStore.profile?.profileImage || null
})

/**
 * Open hidden file input.
 */
function openFilePicker() {

  if (profileStore.uploading) {
    return
  }

  fileInput.value?.click()
}

/**
 * Handle selected image.
 */
async function onFileChange(event) {

  const file =
    event.target.files?.[0]

  if (!file) {
    return
  }

  /*
   * Validate file type on frontend.
   *
   * Backend also validates it.
   */
  const allowedTypes = [
    'image/jpeg',
    'image/png',
    'image/webp'
  ]

  if (!allowedTypes.includes(file.type)) {

    profileStore.error =
      'Only JPG, PNG, and WebP images are allowed.'

    event.target.value = ''

    return
  }

  /*
   * Validate maximum size.
   */
  const maxSize =
    5 * 1024 * 1024

  if (file.size > maxSize) {

    profileStore.error =
      'Profile image must not exceed 5 MB.'

    event.target.value = ''

    return
  }

  /*
   * Create local preview.
   */
  imagePreview.value =
    URL.createObjectURL(file)

  try {

    await profileStore.uploadProfileImage(
      file
    )

    /*
     * Backend response now contains the
     * permanent Cloudinary URL.
     */
    imagePreview.value = null

  } catch (error) {

    /*
     * Upload failed.
     *
     * Remove temporary preview.
     */
    imagePreview.value = null

  } finally {

    /*
     * Allow selecting the same file again.
     */
    event.target.value = ''
  }
}
</script>

<template>

  <div class="relative">

    <!-- Profile image -->
    <div
      class="relative h-24 w-24 overflow-hidden rounded-full"
    >

      <img
        v-if="src"
        :src="src"
        alt="Profile photo"
        class="h-full w-full object-cover"
      />

      <div
        v-else
        class="flex h-full w-full items-center justify-center bg-neutral-800 text-2xl font-semibold"
      >
        {{
          profileStore.fullName
            ?.split(' ')
            .map(name => name.charAt(0))
            .join('')
            .slice(0, 2)
            .toUpperCase()
        }}
      </div>

      <!-- Upload progress -->
      <div
        v-if="profileStore.uploading"
        class="absolute inset-0 flex items-center justify-center rounded-full bg-black/50"
      >
        <span
          class="text-xs font-semibold text-white"
        >
          {{ profileStore.uploadProgress }}%
        </span>
      </div>

    </div>

    <!-- Hidden input -->
    <input
      ref="fileInput"
      type="file"
      accept="image/jpeg,image/png,image/webp"
      class="hidden"
      aria-label="Upload profile photo"
      @change="onFileChange"
    />

    <!-- Upload button -->
    <UButton
      :label="
        src
          ? 'Change Photo'
          : 'Upload Photo'
      "
      icon="i-lucide-camera"
      color="neutral"
      variant="outline"
      size="sm"
      :loading="profileStore.uploading"
      :disabled="profileStore.uploading"
      @click="openFilePicker"
    />

  </div>

</template>