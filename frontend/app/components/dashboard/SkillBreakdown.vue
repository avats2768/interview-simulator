<script setup>
defineProps({
  skills: {
    type: Array,
    required: true
    // shape: [{ label: 'Technical Skills', score: 85 }]
  }
})
</script>

<template>
  <UCard>
    <template #header>
      <h3 class="text-sm font-semibold text-highlighted">
        Skill Breakdown
      </h3>
    </template>

    <div class="flex flex-col gap-4">
      <div
        v-for="skill in skills"
        :key="skill.label"
        class="flex flex-col gap-1.5"
      >
        <div class="flex items-center justify-between text-sm">
          <span class="text-muted">{{ skill.label }}</span>
          <span class="font-medium text-highlighted">{{ skill.score }}%</span>
        </div>
        <UProgress
          :model-value="skill.score"
          size="sm"
        />
      </div>
    </div>
  </UCard>
</template>
