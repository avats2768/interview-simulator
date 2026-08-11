<script setup>
import { computed, onMounted, ref } from 'vue'
import ProfileImageUpload from './ProfileImageUpload.vue'
import skillService from '~/services/skillsService.js'

const props = defineProps({
  profile: {
    type: Object,
    required: true
  }
})

/*
|--------------------------------------------------------------------------
| Location
|--------------------------------------------------------------------------
*/

const location = computed(() =>
  [
    props.profile.city,
    props.profile.country
  ]
    .filter(Boolean)
    .join(', ')
)

/*
|--------------------------------------------------------------------------
| Skills
|--------------------------------------------------------------------------
*/

const skills = ref([])

const skillsLoading = ref(false)

const skillsError = ref(null)

/**
 * Convert profile.skillIds into actual skill objects.
 *
 * Example:
 *
 * profile.skillIds = [1, 3, 7]
 *
 * becomes:
 *
 * [
 *   { id: 1, name: 'Java' },
 *   { id: 3, name: 'React' },
 *   { id: 7, name: 'Docker' }
 * ]
 */
const selectedSkills = computed(() => {

  if (
    !Array.isArray(props.profile.skillIds)
    || props.profile.skillIds.length === 0
  ) {
    return []
  }

  const selectedIds =
    new Set(
      props.profile.skillIds.map(
        id => Number(id)
      )
    )

  return skills.value.filter(
    skill =>
      selectedIds.has(
        Number(skill.id)
      )
  )
})

/**
 * Fetch all active skills.
 */
async function fetchSkills() {

  if (
    !Array.isArray(props.profile.skillIds)
    || props.profile.skillIds.length === 0
  ) {
    return
  }

  skillsLoading.value = true

  skillsError.value = null

  try {

    const response =
      await skillService.getAllActiveSkills()

    skills.value =
      Array.isArray(response.data)
        ? response.data
        : []

  } catch (error) {

    skillsError.value =
      error.response?.data?.message
      || 'Unable to load skills.'

  } finally {

    skillsLoading.value = false
  }
}

onMounted(() => {
  fetchSkills()
})

/*
|--------------------------------------------------------------------------
| Professional information
|--------------------------------------------------------------------------
*/

const infoRows = computed(() => [

  {
    label: 'Current Position',
    value: props.profile.currentPosition
  },

  {
    label: 'Current Company',
    value: props.profile.currentCompany
  },

  {
    label: 'Preferred Role',
    value: props.profile.preferredRole
  },

  {
    label: 'Years of Experience',

    value:
      props.profile.yearsOfExperience !== null
      && props.profile.yearsOfExperience !== undefined
        ? `${props.profile.yearsOfExperience} yrs`
        : null
  },

  {
    label: 'Notice Period',
    value: props.profile.noticePeriod
  },

  {
    label: 'Location',
    value: location.value
  }
])

/*
|--------------------------------------------------------------------------
| Social links
|--------------------------------------------------------------------------
*/

const hasSocialLinks = computed(() =>
  !!(
    props.profile.linkedinUrl
    || props.profile.githubUrl
    || props.profile.portfolioUrl
  )
)
</script>

<template>

  <div class="flex flex-col gap-4">

    <!-- ========================================================= -->
    <!-- Header -->
    <!-- ========================================================= -->

    <UCard>

      <div
        class="flex flex-col items-center gap-4 text-center sm:flex-row sm:items-start sm:text-left"
      >

        <ProfileImageUpload
          :src="profile.profileImage"
          :name="`${profile.firstName ?? ''} ${profile.lastName ?? ''}`"
        />

        <div
          class="flex flex-1 flex-col gap-1"
        >

          <h2
            class="text-xl font-semibold text-highlighted"
          >
            {{ profile.firstName }}
            {{ profile.lastName }}
          </h2>

          <p
            v-if="profile.headline"
            class="text-sm text-muted"
          >
            {{ profile.headline }}
          </p>

          <p
            v-if="location"
            class="text-sm text-dimmed"
          >
            {{ location }}
          </p>

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


    <!-- ========================================================= -->
    <!-- Professional Information -->
    <!-- ========================================================= -->

    <UCard>

      <template #header>

        <h3
          class="text-sm font-semibold text-highlighted"
        >
          Professional Information
        </h3>

      </template>

      <div
        class="grid grid-cols-1 gap-x-8 gap-y-4 sm:grid-cols-2"
      >

        <div
          v-for="row in infoRows"
          :key="row.label"
          class="flex flex-col gap-1"
        >

          <span
            class="text-xs text-dimmed"
          >
            {{ row.label }}
          </span>

          <span
            class="text-sm font-medium text-highlighted"
          >
            {{ row.value || 'Not specified' }}
          </span>

        </div>

      </div>

    </UCard>


    <!-- ========================================================= -->
    <!-- Skills -->
    <!-- ========================================================= -->

    <UCard>

      <template #header>

        <div
          class="flex items-center justify-between gap-3"
        >

          <h3
            class="text-sm font-semibold text-highlighted"
          >
            Skills
          </h3>

          <span
            v-if="selectedSkills.length"
            class="text-xs text-dimmed"
          >
            {{ selectedSkills.length }}
            {{ selectedSkills.length === 1 ? 'skill' : 'skills' }}
          </span>

        </div>

      </template>

      <!-- Loading -->

      <div
        v-if="skillsLoading"
        class="flex flex-wrap gap-2"
      >

        <USkeleton
          v-for="n in 5"
          :key="n"
          class="h-7 w-20 rounded-full"
        />

      </div>

      <!-- Error -->

      <div
        v-else-if="skillsError"
        class="flex items-center gap-2 text-sm text-dimmed"
      >

        <UIcon
          name="i-lucide-circle-alert"
          class="size-4 text-error"
        />

        <span>
          {{ skillsError }}
        </span>

      </div>

      <!-- Skills -->

      <div
        v-else-if="selectedSkills.length"
        class="flex flex-wrap gap-2"
      >

        <UBadge
          v-for="skill in selectedSkills"
          :key="skill.id"
          color="primary"
          variant="solid"
          size="md"
        >
          {{ skill.skillName }}
        </UBadge>

      </div>

      <!-- Empty -->

      <p
        v-else
        class="text-sm text-dimmed"
      >
        No skills added yet.
      </p>

    </UCard>


    <!-- ========================================================= -->
    <!-- About -->
    <!-- ========================================================= -->

    <UCard>

      <template #header>

        <h3
          class="text-sm font-semibold text-highlighted"
        >
          About
        </h3>

      </template>

      <p
        v-if="profile.bio"
        class="whitespace-pre-line text-sm text-muted"
      >
        {{ profile.bio }}
      </p>

      <p
        v-else
        class="text-sm text-dimmed"
      >
        No bio added yet.
      </p>

    </UCard>


    <!-- ========================================================= -->
    <!-- Social Profiles -->
    <!-- ========================================================= -->

    <UCard>

      <template #header>

        <h3
          class="text-sm font-semibold text-highlighted"
        >
          Social Profiles
        </h3>

      </template>

      <div
        v-if="hasSocialLinks"
        class="flex flex-wrap gap-2"
      >

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

      <p
        v-else
        class="text-sm text-dimmed"
      >
        No social profiles added yet.
      </p>

    </UCard>

  </div>

</template>