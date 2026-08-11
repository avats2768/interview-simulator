<script setup>
import { onMounted, reactive, ref, computed } from 'vue'
import skillService from '~/services/skillsService'

const props = defineProps({
  profile: {
    type: Object,
    required: true
  },

  loading: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['submit'])

const BIO_MAX_LENGTH = 500

/*
|--------------------------------------------------------------------------
| Skills
|--------------------------------------------------------------------------
*/

const skills = ref([])

const skillsLoading = ref(false)

const skillsError = ref(null)

/*
 * Selected skill IDs.
 *
 * Backend stores these as:
 *
 * PostgreSQL bigint[]
 *
 * Example:
 *
 * [1, 3, 5]
 */
const selectedSkillIds = ref(
  Array.isArray(props.profile.skillIds)
    ? [...props.profile.skillIds]
    : []
)

/*
 * Options passed to USelectMenu.
 */
const skillOptions = computed(() => {
  return skills.value.map(skill => ({
    id: skill.id,
    name: skill.skillName,
    category: skill.category
  }))
})

/*
|--------------------------------------------------------------------------
| Form state
|--------------------------------------------------------------------------
*/

const state = reactive({

  firstName:
    props.profile.firstName ?? '',

  lastName:
    props.profile.lastName ?? '',

  phone:
    props.profile.phone ?? '',

  city:
    props.profile.city ?? '',

  country:
    props.profile.country ?? '',

  headline:
    props.profile.headline ?? '',

  yearsOfExperience:
    props.profile.yearsOfExperience ?? null,

  currentCompany:
    props.profile.currentCompany ?? '',

  currentPosition:
    props.profile.currentPosition ?? '',

  preferredRole:
    props.profile.preferredRole ?? '',

  noticePeriod:
    props.profile.noticePeriod ?? '',

  currentCTC:
    props.profile.currentCTC ?? null,

  expectedCTC:
    props.profile.expectedCTC ?? null,

  linkedinUrl:
    props.profile.linkedinUrl ?? '',

  githubUrl:
    props.profile.githubUrl ?? '',

  portfolioUrl:
    props.profile.portfolioUrl ?? '',

  bio:
    props.profile.bio ?? '',

  /*
   * Skills are part of the update payload.
   */
  skillIds: selectedSkillIds.value
})

/*
|--------------------------------------------------------------------------
| Load skills
|--------------------------------------------------------------------------
*/

async function fetchSkills() {

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

/*
|--------------------------------------------------------------------------
| Lifecycle
|--------------------------------------------------------------------------
*/

onMounted(() => {

  fetchSkills()

})

/*
|--------------------------------------------------------------------------
| Validation
|--------------------------------------------------------------------------
*/

function validate(formState) {

  const errors = []

  if (!formState.firstName?.trim()) {

    errors.push({
      name: 'firstName',
      message: 'First name is required.'
    })
  }

  if (!formState.lastName?.trim()) {

    errors.push({
      name: 'lastName',
      message: 'Last name is required.'
    })
  }

  if (
    formState.phone
    && !/^[+]?[\d\s-()]{7,20}$/.test(
      formState.phone
    )
  ) {

    errors.push({
      name: 'phone',
      message: 'Enter a valid phone number.'
    })
  }

  if (
    formState.yearsOfExperience !== null
    && formState.yearsOfExperience !== ''
  ) {

    const years =
      Number(
        formState.yearsOfExperience
      )

    if (
      Number.isNaN(years)
      || years < 0
      || years > 60
    ) {

      errors.push({
        name: 'yearsOfExperience',
        message: 'Must be between 0 and 60.'
      })
    }
  }

  if (
    formState.currentCTC !== null
    && formState.currentCTC !== ''
    && Number(formState.currentCTC) < 0
  ) {

    errors.push({
      name: 'currentCTC',
      message: 'Current CTC cannot be negative.'
    })
  }

  if (
    formState.expectedCTC !== null
    && formState.expectedCTC !== ''
    && Number(formState.expectedCTC) < 0
  ) {

    errors.push({
      name: 'expectedCTC',
      message: 'Expected CTC cannot be negative.'
    })
  }

  if (
    formState.linkedinUrl
    && !/^https?:\/\/([a-z]{2,3}\.)?linkedin\.com\/.+/i.test(
      formState.linkedinUrl
    )
  ) {

    errors.push({
      name: 'linkedinUrl',
      message: 'Enter a valid LinkedIn URL.'
    })
  }

  if (
    formState.githubUrl
    && !/^https?:\/\/(www\.)?github\.com\/.+/i.test(
      formState.githubUrl
    )
  ) {

    errors.push({
      name: 'githubUrl',
      message: 'Enter a valid GitHub URL.'
    })
  }

  if (
    formState.portfolioUrl
    && !/^https?:\/\/.+\..+/i.test(
      formState.portfolioUrl
    )
  ) {

    errors.push({
      name: 'portfolioUrl',
      message: 'Enter a valid URL.'
    })
  }

  if (
    formState.bio
    && formState.bio.length > BIO_MAX_LENGTH
  ) {

    errors.push({
      name: 'bio',
      message:
        `Bio must be ${BIO_MAX_LENGTH} characters or fewer.`
    })
  }

  /*
   * Maximum 20 skills.
   */
  if (
    Array.isArray(formState.skillIds)
    && formState.skillIds.length > 20
  ) {

    errors.push({
      name: 'skillIds',
      message: 'You can select a maximum of 20 skills.'
    })
  }

  return errors
}

/*
|--------------------------------------------------------------------------
| Submit
|--------------------------------------------------------------------------
*/

function onSubmit(event) {

  const payload = {

    ...event.data,

    yearsOfExperience:
      event.data.yearsOfExperience === ''
        ? null
        : Number(
            event.data.yearsOfExperience
          ),

    currentCTC:
      event.data.currentCTC === ''
        ? null
        : Number(
            event.data.currentCTC
          ),

    expectedCTC:
      event.data.expectedCTC === ''
        ? null
        : Number(
            event.data.expectedCTC
          ),

    /*
     * Make sure IDs are numbers.
     *
     * This protects against string values coming
     * from a UI component.
     */
    skillIds:
      Array.isArray(event.data.skillIds)
        ? event.data.skillIds.map(
            id => Number(id)
          )
        : []
  }

  emit(
    'submit',
    payload
  )
}
</script>

<template>

  <UForm
    :state="state"
    :validate="validate"
    class="flex flex-col gap-6"
    @submit="onSubmit"
  >

    <!-- ========================================================= -->
    <!-- Personal Information -->
    <!-- ========================================================= -->

    <UCard>

      <template #header>

        <h3
          class="text-sm font-semibold text-highlighted"
        >
          Personal Information
        </h3>

      </template>

      <div
        class="grid grid-cols-1 gap-4 sm:grid-cols-2"
      >

        <UFormField
          label="First Name"
          name="firstName"
          required
        >
          <UInput
            v-model="state.firstName"
            placeholder="Ethan"
            class="w-full"
          />
        </UFormField>

        <UFormField
          label="Last Name"
          name="lastName"
          required
        >
          <UInput
            v-model="state.lastName"
            placeholder="Miller"
            class="w-full"
          />
        </UFormField>

        <UFormField
          label="Phone"
          name="phone"
        >
          <UInput
            v-model="state.phone"
            type="tel"
            placeholder="+1 555 123 4567"
            icon="i-lucide-phone"
            class="w-full"
          />
        </UFormField>

        <UFormField
          label="City"
          name="city"
        >
          <UInput
            v-model="state.city"
            placeholder="Hisar"
            class="w-full"
          />
        </UFormField>

        <UFormField
          label="Country"
          name="country"
        >
          <UInput
            v-model="state.country"
            placeholder="India"
            class="w-full"
          />
        </UFormField>

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
        class="grid grid-cols-1 gap-4 sm:grid-cols-2"
      >

        <UFormField
          label="Headline"
          name="headline"
          class="sm:col-span-2"
        >

          <UInput
            v-model="state.headline"
            placeholder="Full Stack Developer"
            class="w-full"
          />

        </UFormField>


        <UFormField
          label="Years of Experience"
          name="yearsOfExperience"
        >

          <UInput
            v-model="state.yearsOfExperience"
            type="number"
            min="0"
            max="60"
            placeholder="3"
            class="w-full"
          />

        </UFormField>


        <UFormField
          label="Current Company"
          name="currentCompany"
        >

          <UInput
            v-model="state.currentCompany"
            placeholder="Acme Corp"
            class="w-full"
          />

        </UFormField>


        <UFormField
          label="Current Position"
          name="currentPosition"
        >

          <UInput
            v-model="state.currentPosition"
            placeholder="Frontend Engineer"
            class="w-full"
          />

        </UFormField>


        <UFormField
          label="Preferred Role"
          name="preferredRole"
        >

          <UInput
            v-model="state.preferredRole"
            placeholder="Full Stack Developer"
            class="w-full"
          />

        </UFormField>


        <UFormField
          label="Notice Period"
          name="noticePeriod"
        >

          <UInput
            v-model="state.noticePeriod"
            placeholder="30 Days"
            class="w-full"
          />

        </UFormField>


        <!-- ===================================================== -->
        <!-- Skills -->
        <!-- ===================================================== -->

        <UFormField
          label="Skills"
          name="skillIds"
          description="Select up to 20 skills"
          class="sm:col-span-2"
        >

          <USelectMenu
            v-model="state.skillIds"
            :items="skillOptions"
            value-key="id"
            label-key="name"
            multiple
            searchable
            placeholder="Search and select your skills..."
            search-placeholder="Search skills..."
            :loading="skillsLoading"
            :disabled="skillsLoading"
            class="w-full"
          >

            <template #empty>
              <div
                class="px-3 py-6 text-center text-sm text-dimmed"
              >
                No skills found.
              </div>
            </template>

          </USelectMenu>

          <!-- Skill loading error -->

          <p
            v-if="skillsError"
            class="mt-1 text-sm text-error"
          >
            {{ skillsError }}
          </p>

          <!-- Selected skill count -->

          <p
            v-else
            class="mt-1 text-xs text-dimmed"
          >
            {{ state.skillIds.length }} / 20 skills selected
          </p>

        </UFormField>

      </div>

    </UCard>


    <!-- ========================================================= -->
    <!-- Compensation -->
    <!-- ========================================================= -->

    <UCard>

      <template #header>

        <h3
          class="text-sm font-semibold text-highlighted"
        >
          Compensation
        </h3>

      </template>

      <div
        class="grid grid-cols-1 gap-4 sm:grid-cols-2"
      >

        <UFormField
          label="Current CTC (LPA)"
          name="currentCTC"
        >

          <UInput
            v-model="state.currentCTC"
            type="number"
            min="0"
            step="0.1"
            placeholder="3.5"
            class="w-full"
          />

        </UFormField>


        <UFormField
          label="Expected CTC (LPA)"
          name="expectedCTC"
        >

          <UInput
            v-model="state.expectedCTC"
            type="number"
            min="0"
            step="0.1"
            placeholder="6"
            class="w-full"
          />

        </UFormField>

      </div>

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
        class="grid grid-cols-1 gap-4 sm:grid-cols-2"
      >

        <UFormField
          label="LinkedIn"
          name="linkedinUrl"
          class="sm:col-span-2"
        >

          <UInput
            v-model="state.linkedinUrl"
            type="url"
            placeholder="https://linkedin.com/in/username"
            icon="i-lucide-linkedin"
            class="w-full"
          />

        </UFormField>


        <UFormField
          label="GitHub"
          name="githubUrl"
        >

          <UInput
            v-model="state.githubUrl"
            type="url"
            placeholder="https://github.com/username"
            icon="i-lucide-github"
            class="w-full"
          />

        </UFormField>


        <UFormField
          label="Portfolio"
          name="portfolioUrl"
        >

          <UInput
            v-model="state.portfolioUrl"
            type="url"
            placeholder="https://yourdomain.com"
            icon="i-lucide-globe"
            class="w-full"
          />

        </UFormField>

      </div>

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

      <UFormField
        label="Bio"
        name="bio"
        :description="`Max ${BIO_MAX_LENGTH} characters`"
      >

        <UTextarea
          v-model="state.bio"
          :maxlength="BIO_MAX_LENGTH"
          :rows="5"
          placeholder="Tell us about yourself..."
          class="w-full"
        />

      </UFormField>

    </UCard>


    <!-- ========================================================= -->
    <!-- Actions -->
    <!-- ========================================================= -->

    <div
      class="flex flex-col-reverse gap-3 sm:flex-row sm:justify-end"
    >

      <UButton
        label="Cancel"
        color="neutral"
        variant="outline"
        to="/profile"
        :disabled="loading"
      />

      <UButton
        type="submit"
        label="Save Changes"
        icon="i-lucide-check"
        :loading="loading"
      />

    </div>

  </UForm>

</template>