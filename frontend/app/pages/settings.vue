<script setup>
import PageHeader from '~/components/common/PageHeader.vue';

definePageMeta({ title: 'Settings' })
</script>

<template>
  <div class="flex flex-col gap-6">
    <PageHeader
      title="Settings"
      description="Configure your account and preferences."
    />

    <UCard :ui="{ body: 'flex flex-col items-center justify-center gap-3 py-16 text-center' }">
      <UIcon
        name="i-lucide-settings"
        class="size-8 text-dimmed"
      />
      <p class="text-sm text-dimmed max-w-sm">
        This page will be built in the next phase. The layout, navigation and theming are
        already wired up and ready to hold this feature's content.
      </p>
    </UCard>
  </div>
</template>
