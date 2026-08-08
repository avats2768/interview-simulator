<script setup>
import PageHeader from '~/components/common/PageHeader.vue';

definePageMeta({ title: 'Dashboard' })
</script>

<template>
  <div class="flex flex-col gap-6">
    <PageHeader
      title="Dashboard"
      description="Welcome back, Ethan. Here's an overview of your interview prep."
    />

    <UCard :ui="{ body: 'flex flex-col items-center justify-center gap-3 py-16 text-center' }">
      <UIcon
        name="i-lucide-layout-dashboard"
        class="size-8 text-dimmed"
      />
      <p class="text-sm text-dimmed max-w-sm">
        The application layout is ready. Dashboard widgets (fulfillment charts, stats, recent
        sessions) will be built here in the next phase.
      </p>
    </UCard>
  </div>
</template>
