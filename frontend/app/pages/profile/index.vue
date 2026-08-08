<script setup>
import { useProfileStore } from '~/stores/profileStore'
import ProfileCompletion from '~/components/profile/ProfileCompletion.vue'
import ProfileOverview from '~/components/profile/ProfileOverview.vue'

definePageMeta({ title: 'Profile', middleware: 'auth' })

const profileStore = useProfileStore()

if (!profileStore.profile) {
  await profileStore.fetchProfile().catch(() => {})
}

function retry() {
  profileStore.fetchProfile()
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <PageHeader
      title="Profile"
      description="Manage your personal information."
    />

    <!-- Loading state -->
    <div
      v-if="profileStore.loading && !profileStore.profile"
      class="flex flex-col gap-4"
    >
      <UCard
        v-for="n in 3"
        :key="n"
        :ui="{ body: 'py-8' }"
      >
        <div class="flex items-center gap-4">
          <div class="size-16 shrink-0 animate-pulse rounded-full bg-elevated" />
          <div class="flex flex-1 flex-col gap-2">
            <div class="h-4 w-1/3 animate-pulse rounded bg-elevated" />
            <div class="h-3 w-1/4 animate-pulse rounded bg-elevated" />
          </div>
        </div>
      </UCard>
    </div>

    <!-- Error state -->
    <UCard
      v-else-if="profileStore.error && !profileStore.profile"
      :ui="{ body: 'flex flex-col items-center justify-center gap-3 py-16 text-center' }"
    >
      <UIcon
        name="i-lucide-circle-x"
        class="size-8 text-error"
      />
      <p class="text-sm text-dimmed max-w-sm">
        {{ profileStore.error }}
      </p>
      <UButton
        label="Try Again"
        icon="i-lucide-refresh-cw"
        color="neutral"
        variant="outline"
        @click="retry"
      />
    </UCard>

    <!-- Empty state -->
    <UCard
      v-else-if="!profileStore.profile"
      :ui="{ body: 'flex flex-col items-center justify-center gap-3 py-16 text-center' }"
    >
      <UIcon
        name="i-lucide-user-round"
        class="size-8 text-dimmed"
      />
      <p class="text-sm text-dimmed max-w-sm">
        You haven't set up your profile yet. Add your details to help recruiters and interviewers get to know you.
      </p>
      <UButton
        label="Set Up Profile"
        icon="i-lucide-pencil"
        to="/profile/edit"
      />
    </UCard>

    <!-- Profile content -->
    <template v-else>
      <ProfileCompletion :completion-percentage="profileStore.completionPercentage" />
      <ProfileOverview :profile="profileStore.profile" />
    </template>
  </div>
</template>
