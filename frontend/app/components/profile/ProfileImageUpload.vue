<script setup>
import { useProfileStore } from '~/stores/profileStore'
import { getInitials } from '~/utils/string'

defineProps({
  src: {
    type: String,
    default: null
  },
  name: {
    type: String,
    default: ''
  }
})

const profileStore = useProfileStore()
const toast = useToast()

const fileInput = ref(null)
const previewUrl = ref(null)

const MAX_FILE_SIZE = 5 * 1024 * 1024 // 5MB
const ALLOWED_TYPES = ['image/jpeg', 'image/png', 'image/webp']

function openFilePicker() {
  fileInput.value?.click()
}

async function onFileChange(event) {
  const file = event.target.files?.[0]
  event.target.value = ''

  if (!file) return

  if (!ALLOWED_TYPES.includes(file.type)) {
    toast.add({
      title: 'Unsupported file type',
      description: 'Please choose a JPG, PNG or WEBP image.',
      color: 'error'
    })
    return
  }

  if (file.size > MAX_FILE_SIZE) {
    toast.add({
      title: 'File too large',
      description: 'Please choose an image under 5MB.',
      color: 'error'
    })
    return
  }

  previewUrl.value = URL.createObjectURL(file)

  try {
    await profileStore.uploadProfileImage(file)

    toast.add({
      title: 'Profile image updated successfully.',
      color: 'success'
    })
  } catch (error) {
    toast.add({
      title: 'Upload failed',
      description: error.response?.data?.message || 'Something went wrong while uploading your photo.',
      color: 'error'
    })
  } finally {
    if (previewUrl.value) {
      URL.revokeObjectURL(previewUrl.value)
      previewUrl.value = null
    }
  }
}
</script>

<template>
  <div class="flex flex-col items-center gap-3">
    <div class="relative">
      <UAvatar
        :src="previewUrl || src"
        :alt="name || 'Profile photo'"
        :text="getInitials(name || '')"
        size="3xl"
      />

      <div
        v-if="profileStore.uploading"
        class="absolute inset-0 flex items-center justify-center rounded-full bg-black/50"
      >
        <span class="text-xs font-semibold text-white">{{ profileStore.uploadProgress }}%</span>
      </div>
    </div>

    <input
      ref="fileInput"
      type="file"
      accept="image/jpeg,image/png,image/webp"
      class="hidden"
      aria-label="Upload profile photo"
      @change="onFileChange"
    >

    <UButton
      :label="src ? 'Change Photo' : 'Upload Photo'"
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