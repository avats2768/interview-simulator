<script setup>
import PageHeader from '~/components/common/PageHeader.vue'

definePageMeta({ title: 'Resume', middleware: 'auth' })
</script>

<template>
  <div class="flex flex-col gap-6">
    <PageHeader
      title="Resume"
      description="Upload and get AI feedback on your resume."
    />

    <UCard :ui="{ body: 'flex flex-col items-center justify-center gap-3 py-16 text-center' }">
      <UIcon
        name="i-lucide-file-text"
        class="size-8 text-dimmed"
      />
      <p class="text-sm text-dimmed max-w-sm">
        This page will be built in the next phase. The layout, navigation and theming are
        already wired up and ready to hold this feature's content.
      </p>
    </UCard>
  </div>
</template>
