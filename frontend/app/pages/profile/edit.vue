<script setup>
import { useProfileStore } from '~/stores/profileStore'
import ProfileForm from '~/components/profile/ProfileForm.vue'

definePageMeta({ title: 'Edit Profile', middleware: 'auth' })

const profileStore = useProfileStore()
const toast = useToast()

if (!profileStore.profile) {
  await profileStore.fetchProfile().catch(() => {})
}

async function onSubmit(payload) {
  try {
    await profileStore.updateProfile(payload)

    toast.add({
      title: 'Profile updated successfully.',
      color: 'success'
    })

    navigateTo('/profile')
  } catch (error) {
    toast.add({
      title: 'Update failed',
      description: error.response?.data?.message || profileStore.error || 'Something went wrong.',
      color: 'error'
    })
  }
}

function retry() {
  profileStore.fetchProfile()
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <PageHeader title="Edit Profile" description="Keep your information up to date." />

    <!-- Loading state -->
    <div v-if="profileStore.loading && !profileStore.profile" class="flex flex-col gap-4">
      <UCard v-for="n in 3" :key="n" :ui="{ body: 'py-8' }">
        <div class="h-4 w-1/3 animate-pulse rounded bg-elevated" />
      </UCard>
    </div>

    <!-- Error state -->
    <UCard
      v-else-if="profileStore.error && !profileStore.profile"
      :ui="{ body: 'flex flex-col items-center justify-center gap-3 py-16 text-center' }"
    >
      <UIcon name="i-lucide-circle-x" class="size-8 text-error" />
      <p class="text-sm text-dimmed max-w-sm">{{ profileStore.error }}</p>
      <UButton label="Try Again" icon="i-lucide-refresh-cw" color="neutral" variant="outline" @click="retry" />
    </UCard>

    <ProfileForm
      v-else-if="profileStore.profile"
      :profile="profileStore.profile"
      :loading="profileStore.updating"
      @submit="onSubmit"
    />
  </div>
</template>