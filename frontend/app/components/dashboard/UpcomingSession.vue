<script setup>
defineProps({
  role: {
    type: String,
    required: true
  },
  date: {
    type: String,
    required: true
  },
  time: {
    type: String,
    required: true
  },
  duration: {
    type: String,
    required: true
  }
})
</script>

<template>
  <UCard>
    <template #header>
      <h3 class="text-sm font-semibold text-highlighted">Upcoming Session</h3>
    </template>

    <div class="flex flex-col gap-4">
      <div class="flex items-center gap-3">
        <div class="flex size-11 shrink-0 items-center justify-center rounded-lg bg-primary/10 text-primary">
          <UIcon name="i-lucide-mic" class="size-5" />
        </div>
        <div class="flex flex-col">
          <span class="text-sm font-medium text-highlighted">{{ role }}</span>
          <span class="text-xs text-dimmed">Mock Interview</span>
        </div>
      </div>

      <div class="flex flex-col gap-2 text-sm">
        <div class="flex items-center gap-2 text-muted">
          <UIcon name="i-lucide-calendar" class="size-4" />
          <span>{{ date }}</span>
        </div>
        <div class="flex items-center gap-2 text-muted">
          <UIcon name="i-lucide-clock" class="size-4" />
          <span>{{ time }} · {{ duration }}</span>
        </div>
      </div>

      <UButton label="Start Interview" icon="i-lucide-play" block />
    </div>
  </UCard>
</template>