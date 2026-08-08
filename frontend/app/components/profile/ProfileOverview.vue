<script setup>
import ProfileImageUpload from './ProfileImageUpload.vue'

const props = defineProps({
  profile: {
    type: Object,
    required: true
  }
})

const location = computed(() =>
  [props.profile.city, props.profile.country].filter(Boolean).join(', ')
)

const infoRows = computed(() => [
  { label: 'Current Position', value: props.profile.currentPosition },
  { label: 'Current Company', value: props.profile.currentCompany },
  { label: 'Preferred Role', value: props.profile.preferredRole },
  {
    label: 'Years of Experience',
    value: props.profile.yearsOfExperience !== null && props.profile.yearsOfExperience !== undefined
      ? `${props.profile.yearsOfExperience} yrs`
      : null
  },
  { label: 'Notice Period', value: props.profile.noticePeriod },
  { label: 'Location', value: location.value }
])

const hasSocialLinks = computed(() =>
  !!(props.profile.linkedinUrl || props.profile.githubUrl || props.profile.portfolioUrl)
)
</script>

<template>
  <div class="flex flex-col gap-4">
    <!-- Header -->
    <UCard>
      <div class="flex flex-col items-center gap-4 text-center sm:flex-row sm:items-start sm:text-left">
        <ProfileImageUpload
          :src="profile.profileImage"
          :name="`${profile.firstName ?? ''} ${profile.lastName ?? ''}`"
        />

        <div class="flex flex-1 flex-col gap-1">
          <h2 class="text-xl font-semibold text-highlighted">
            {{ profile.firstName }} {{ profile.lastName }}
          </h2>
          <p v-if="profile.headline" class="text-sm text-muted">{{ profile.headline }}</p>
          <p v-if="location" class="text-sm text-dimmed">{{ location }}</p>
        </div>

        <UButton
          label="Edit Profile"
          icon="i-lucide-pencil"
          color="neutral"
          variant="outline"
          to="/profile/edit"
          class="shrink-0"
        />
      </div>
    </UCard>

    <!-- Professional Information -->
    <UCard>
      <template #header>
        <h3 class="text-sm font-semibold text-highlighted">Professional Information</h3>
      </template>

      <div class="grid grid-cols-1 gap-x-8 gap-y-4 sm:grid-cols-2">
        <div v-for="row in infoRows" :key="row.label" class="flex flex-col gap-1">
          <span class="text-xs text-dimmed">{{ row.label }}</span>
          <span class="text-sm font-medium text-highlighted">{{ row.value || 'Not specified' }}</span>
        </div>
      </div>
    </UCard>

    <!-- About -->
    <UCard>
      <template #header>
        <h3 class="text-sm font-semibold text-highlighted">About</h3>
      </template>

      <p v-if="profile.bio" class="whitespace-pre-line text-sm text-muted">{{ profile.bio }}</p>
      <p v-else class="text-sm text-dimmed">No bio added yet.</p>
    </UCard>

    <!-- Social Profiles -->
    <UCard>
      <template #header>
        <h3 class="text-sm font-semibold text-highlighted">Social Profiles</h3>
      </template>

      <div v-if="hasSocialLinks" class="flex flex-wrap gap-2">
        <UButton
          v-if="profile.linkedinUrl"
          label="LinkedIn"
          icon="i-lucide-linkedin"
          color="neutral"
          variant="soft"
          size="sm"
          :to="profile.linkedinUrl"
          target="_blank"
        />
        <UButton
          v-if="profile.githubUrl"
          label="GitHub"
          icon="i-lucide-github"
          color="neutral"
          variant="soft"
          size="sm"
          :to="profile.githubUrl"
          target="_blank"
        />
        <UButton
          v-if="profile.portfolioUrl"
          label="Portfolio"
          icon="i-lucide-globe"
          color="neutral"
          variant="soft"
          size="sm"
          :to="profile.portfolioUrl"
          target="_blank"
        />
      </div>
      <p v-else class="text-sm text-dimmed">No social profiles added yet.</p>
    </UCard>
  </div>
</template>