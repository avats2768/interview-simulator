<script setup>
import AppLogo from '~/components/common/AppLogo.vue';
import ThemeToggle from '~/components/common/ThemeToggle.vue';

</script>

<template>
  <div class="flex min-h-screen flex-col bg-muted">
    <header class="flex h-16 shrink-0 items-center justify-between border-b border-default bg-default px-4 sm:px-6">
      <AppLogo />
      <ThemeToggle />
    </header>

    <div class="flex flex-1 items-center justify-center p-4">
      <div class="w-full max-w-md">
        <UCard>
          <slot />
        </UCard>
      </div>
    </div>
  </div>
</template>