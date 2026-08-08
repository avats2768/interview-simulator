<script setup>
const props = defineProps({
  sessions: {
    type: Array,
    required: true
    // shape: [{ label: 'Mon', score: 72 }]
  }
})

const average = Math.round(
  props.sessions.reduce((total, item) => total + item.score, 0) / props.sessions.length
)
</script>

<template>
  <UCard>
    <template #header>
      <div class="flex items-center justify-between">
        <div>
          <h3 class="text-sm font-semibold text-highlighted">Score Trend</h3>
          <p class="text-xs text-dimmed mt-0.5">Last {{ sessions.length }} mock interviews</p>
        </div>
        <UBadge color="primary" variant="subtle">Avg {{ average }}%</UBadge>
      </div>
    </template>

    <div class="flex items-end gap-3 h-44 px-1">
      <div
        v-for="item in sessions"
        :key="item.label"
        class="flex flex-1 flex-col items-center gap-2"
      >
        <UTooltip :text="`${item.score}%`">
          <div class="flex h-36 w-full items-end rounded-md bg-elevated">
            <div
              class="w-full rounded-md bg-primary transition-all duration-300"
              :style="{ height: item.score + '%' }"
            />
          </div>
        </UTooltip>
        <span class="text-xs text-dimmed">{{ item.label }}</span>
      </div>
    </div>
  </UCard>
</template>