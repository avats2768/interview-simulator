<script setup>
defineProps({
  label: {
    type: String,
    required: true
  },
  value: {
    type: String,
    required: true
  },
  icon: {
    type: String,
    required: true
  },
  trend: {
    type: Object,
    default: null
    // shape: { value: '+3', positive: true }
  }
})
</script>

<template>
  <UCard>
    <div class="flex items-center justify-between">
      <div class="flex flex-col gap-1">
        <span class="text-sm text-dimmed">{{ label }}</span>
        <span class="text-2xl font-semibold text-highlighted">{{ value }}</span>
      </div>

      <div class="flex size-11 shrink-0 items-center justify-center rounded-lg bg-primary/10 text-primary">
        <UIcon
          :name="icon"
          class="size-5"
        />
      </div>
    </div>

    <div
      v-if="trend"
      class="mt-3 flex items-center gap-1 text-xs"
    >
      <UIcon
        :name="trend.positive ? 'i-lucide-trending-up' : 'i-lucide-trending-down'"
        class="size-3.5"
        :class="trend.positive ? 'text-success' : 'text-error'"
      />
      <span :class="trend.positive ? 'text-success' : 'text-error'">{{ trend.value }}</span>
      <span class="text-dimmed">vs last week</span>
    </div>
  </UCard>
</template>
