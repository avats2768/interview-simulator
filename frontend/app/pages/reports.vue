<script setup>
import PageHeader from '~/components/common/PageHeader.vue';

definePageMeta({ title: 'Reports' })
</script>

<template>
  <div class="flex flex-col gap-6">
    <PageHeader
      title="Reports"
      description="Review performance reports from past sessions."
    />

    <UCard :ui="{ body: 'flex flex-col items-center justify-center gap-3 py-16 text-center' }">
      <UIcon
        name="i-lucide-bar-chart-3"
        class="size-8 text-dimmed"
      />
      <p class="text-sm text-dimmed max-w-sm">
        This page will be built in the next phase. The layout, navigation and theming are
        already wired up and ready to hold this feature's content.
      </p>
    </UCard>
  </div>
</template>
