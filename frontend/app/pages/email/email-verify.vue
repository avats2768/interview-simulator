<script setup>
import authService from '~/services/auth/authService'

definePageMeta({ title: 'Verify email', layout: 'auth' })

const route = useRoute()

const status = ref('loading')
const errorMessage = ref('')

async function runVerification() {
  const token = route.query.token

  if (!token) {
    status.value = 'error'
    errorMessage.value = 'Invalid verification link.'
    return
  }

  status.value = 'loading'

  try {
    const response = await authService.verifyEmail(token)

    if (response.success) {
      status.value = 'success'
    } else {
      status.value = 'error'
      errorMessage.value = response.message || 'Invalid verification link.'
    }
  } catch (error) {
    status.value = 'error'
    errorMessage.value
      = error.response?.data?.message
        || 'Invalid verification link.'
  }
}

onMounted(() => {
  runVerification()
})
</script>

<template>
  <div class="flex flex-col items-center gap-6 text-center">
    <template v-if="status === 'loading'">
      <UIcon
        name="i-lucide-loader-circle"
        class="size-14 animate-spin text-primary"
      />

      <div>
        <h1 class="text-lg font-semibold text-highlighted">
          Verifying your email…
        </h1>
        <p class="mt-1 text-sm text-dimmed">
          Please wait while we confirm your account.
        </p>
      </div>
    </template>

    <template v-else-if="status === 'success'">
      <UIcon
        name="i-lucide-circle-check"
        class="size-14 text-success"
      />

      <div>
        <h1 class="text-lg font-semibold text-highlighted">
          Email Verified
        </h1>
        <p class="mt-1 text-sm text-dimmed">
          Account verified successfully.
        </p>
      </div>

      <UButton
        label="Go to Login"
        block
        class="w-full"
        to="/auth/login"
      />
    </template>

    <template v-else>
      <UIcon
        name="i-lucide-circle-x"
        class="size-14 text-error"
      />

      <div>
        <h1 class="text-lg font-semibold text-highlighted">
          Verification Failed
        </h1>
        <p class="mt-1 text-sm text-dimmed">
          {{ errorMessage }}
        </p>
      </div>

      <div class="flex w-full flex-col gap-2">
        <UButton
          label="Try Again"
          block
          :loading="status === 'loading'"
          @click="runVerification"
        />
        <UButton
          label="Go to Login"
          variant="ghost"
          color="neutral"
          block
          to="/auth/login"
        />
      </div>
    </template>
  </div>
</template>
