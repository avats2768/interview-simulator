<script setup>
import authService from "~/services/auth/authService";
definePageMeta({ title: 'Create account', layout: 'auth' })

const toast = useToast()

const state = reactive({
  name: '',
  email: '',
  password: '',
  confirmPassword: '',
  agree: false
})

const loading = ref(false)

async function onSubmit() {

  if (!state.name || !state.email || !state.password || !state.confirmPassword) {

    toast.add({
      title: "Missing information",
      description: "Fill in every field.",
      color: "error"
    })

    return
  }

  if (state.password !== state.confirmPassword) {

    toast.add({
      title: "Passwords don't match",
      color: "error"
    })

    return
  }

  loading.value = true

  try {

    const response = await authService.register({

      username: state.name,
      email: state.email,
      password: state.password

    })

    toast.add({
      title: "Registration successful",
      description: "Please check your email to verify your account.",
      color: "success"
    })

    navigateTo({
      path: "/email/email-verification-sent",
      query: { email: state.email }
    })

  } catch (error) {

    toast.add({
      title: "Registration Failed",
      description:
        error.response?.data?.message ||
        "Something went wrong.",
      color: "error"
    })

  } finally {

    loading.value = false

  }

}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="text-center">
      <h1 class="text-lg font-semibold text-highlighted">Create your account</h1>
      <p class="mt-1 text-sm text-dimmed">Start practicing for your next interview</p>
    </div>

    <UForm :state="state" class="flex flex-col gap-4" @submit="onSubmit">
      <UFormField label="Full name" name="name">
        <UInput
          v-model="state.name"
          placeholder="Ethan Miller"
          icon="i-lucide-user-round"
          class="w-full"
        />
      </UFormField>

      <UFormField label="Email" name="email">
        <UInput
          v-model="state.email"
          type="email"
          placeholder="you@example.com"
          icon="i-lucide-mail"
          class="w-full"
        />
      </UFormField>

      <UFormField label="Password" name="password">
        <UInput
          v-model="state.password"
          type="password"
          placeholder="••••••••"
          icon="i-lucide-lock"
          class="w-full"
        />
      </UFormField>

      <UFormField label="Confirm password" name="confirmPassword">
        <UInput
          v-model="state.confirmPassword"
          type="password"
          placeholder="••••••••"
          icon="i-lucide-lock"
          class="w-full"
        />
      </UFormField>

      <UCheckbox v-model="state.agree" label="I agree to the Terms of Service and Privacy Policy" />

      <UButton type="submit" label="Create account" block :loading="loading" />
    </UForm>

    <p class="text-center text-sm text-dimmed">
      Already have an account?
      <NuxtLink to="/auth/login" class="font-medium text-primary hover:underline">
        Sign in
      </NuxtLink>
    </p>
  </div>
</template>