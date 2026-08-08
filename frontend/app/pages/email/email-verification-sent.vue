<script setup>
import authService from "~/services/auth/authService";

definePageMeta({ title: 'Verification email sent', layout: 'auth' })

const toast = useToast()
const route = useRoute()

const email = ref(route.query.email || '')
const resendLoading = ref(false)

async function onResend() {

    if (!email.value) {

        toast.add({
            title: "Email required",
            description: "Enter your email to resend the verification link.",
            color: "error"
        });

        return;

    }

    resendLoading.value = true;

    try {

        const response = await authService.resendVerificationEmail(email.value);

        toast.add({
            title: response.message,
            color: "success"
        });

    } catch (error) {

        toast.add({
            title: "Something went wrong",
            description:
                error.response?.data?.message ||
                "Could not resend verification email.",
            color: "error"
        });

    } finally {

        resendLoading.value = false;

    }

}
</script>

<template>
  <div class="flex flex-col gap-6">
    <div class="text-center">
      <UIcon name="i-lucide-mail-check" class="mx-auto size-14 text-primary" />

      <h1 class="mt-4 text-lg font-semibold text-highlighted">Verification Email Sent</h1>
      <p class="mt-1 text-sm text-dimmed">
        We have sent a verification link to your email address.
        Please check your inbox and spam folder.
      </p>
    </div>

    <UFormField v-if="!route.query.email" label="Email" name="email">
      <UInput
        v-model="email"
        type="email"
        placeholder="you@example.com"
        icon="i-lucide-mail"
        class="w-full"
      />
    </UFormField>

    <div class="flex flex-col gap-2">
      <UButton label="Go to Login" block to="/auth/login" />

      <UButton
        label="Resend Email"
        variant="soft"
        block
        :loading="resendLoading"
        :disabled="resendLoading"
        @click="onResend"
      />
    </div>
  </div>
</template>