<script setup>
defineProps({
  completionPercentage: {
    type: Number,
    required: true
  }
})
</script>

<template>
  <UCard>
    <div class="mb-2 flex items-center justify-between">
      <h3 class="text-sm font-semibold text-highlighted">
        Profile Completion
      </h3>
      <span class="text-sm font-medium text-highlighted">{{ completionPercentage }}%</span>
    </div>
    <UProgress
      :model-value="completionPercentage"
      size="sm"
    />
  </UCard>
</template>
