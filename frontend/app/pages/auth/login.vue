<script setup>
import authService from '~/services/auth/authService'
import { useAuthStore } from '~/stores/authStore'

definePageMeta({ title: 'Log in', layout: 'auth' })

const toast = useToast()
const authStore = useAuthStore()

const state = reactive({
  email: '',
  password: '',
  remember: false
})

const loading = ref(false)
const resendLoading = ref(false)
const showUnverifiedCard = ref(false)

async function onSubmit() {
  loading.value = true
  showUnverifiedCard.value = false

  try {
    const response = await authService.login({

      email: state.email,
      password: state.password

    })

    authStore.login(
      response.data
    )

    toast.add({

      title: response.message,
      color: 'success'

    })

    navigateTo('/')
  } catch (error) {
    const message = error.response?.data?.message

    if (message === 'Please verify your email before logging in.') {
      showUnverifiedCard.value = true
    } else {
      toast.add({

        title: 'Login Failed',

        description:
                    message
                    || 'Invalid credentials.',

        color: 'error'

      })
    }
  } finally {
    loading.value = false
  }
}

async function onResendVerification() {
  if (!state.email) {
    toast.add({
      title: 'Email required',
      description: 'Enter your email above first.',
      color: 'error'
    })

    return
  }

  resendLoading.value = true

  try {
    const response = await authService.resendVerificationEmail(state.email)

    toast.add({
      title: response.message,
      color: 'success'
    })
  } catch (error) {
    toast.add({
      title: 'Something went wrong',
      description:
                error.response?.data?.message
                || 'Could not resend verification email.',
      color: 'error'
    })
  } finally {
    resendLoading.value = false
  }
}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="text-center">
      <h1 class="text-lg font-semibold text-highlighted">
        Welcome back
      </h1>
      <p class="mt-1 text-sm text-dimmed">
        Sign in to continue your interview prep
      </p>
    </div>

    <UCard
      v-if="showUnverifiedCard"
      class="border border-warning/30 bg-warning/5"
    >
      <div class="flex flex-col items-center gap-3 text-center">
        <UIcon
          name="i-lucide-mail-warning"
          class="size-10 text-warning"
        />

        <div>
          <h2 class="text-base font-semibold text-highlighted">
            Email Not Verified
          </h2>
          <p class="mt-1 text-sm text-dimmed">
            Your account has not been verified yet.
          </p>
        </div>

        <div class="flex w-full flex-col gap-2">
          <UButton
            label="Resend Verification Email"
            block
            :loading="resendLoading"
            :disabled="resendLoading"
            @click="onResendVerification"
          />

          <UButton
            label="Back to Register"
            variant="ghost"
            color="neutral"
            block
            to="/auth/register"
          />
        </div>
      </div>
    </UCard>

    <UForm
      :state="state"
      class="flex flex-col gap-4"
      @submit="onSubmit"
    >
      <UFormField
        label="Email"
        name="email"
      >
        <UInput
          v-model="state.email"
          type="email"
          placeholder="you@example.com"
          icon="i-lucide-mail"
          class="w-full"
        />
      </UFormField>

      <UFormField
        label="Password"
        name="password"
      >
        <UInput
          v-model="state.password"
          type="password"
          placeholder="••••••••"
          icon="i-lucide-lock"
          class="w-full"
        />
      </UFormField>

      <UCheckbox
        v-model="state.remember"
        label="Remember me"
      />

      <UButton
        type="submit"
        label="Sign in"
        block
        :loading="loading"
      />
    </UForm>

    <p class="text-center text-sm text-dimmed">
      Don't have an account?
      <NuxtLink
        to="/auth/register"
        class="font-medium text-primary hover:underline"
      >
        Sign up
      </NuxtLink>
    </p>
  </div>
</template>
