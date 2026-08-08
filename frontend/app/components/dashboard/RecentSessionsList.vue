<script setup>
defineProps({
  sessions: {
    type: Array,
    required: true
    // shape: [{ id, role, date, duration, score, status }]
  }
})

const statusColor = {
  'completed': 'success',
  'in-progress': 'warning',
  'missed': 'error'
}

const statusLabel = {
  'completed': 'Completed',
  'in-progress': 'In Progress',
  'missed': 'Missed'
}
</script>

<template>
  <UCard :ui="{ body: 'p-0 sm:p-0' }">
    <template #header>
      <div class="flex items-center justify-between">
        <h3 class="text-sm font-semibold text-highlighted">
          Recent Sessions
        </h3>
        <UButton
          label="View all"
          color="neutral"
          variant="link"
          trailing-icon="i-lucide-arrow-right"
          to="/history"
          size="xs"
        />
      </div>
    </template>

    <div class="divide-y divide-default">
      <div
        v-for="session in sessions"
        :key="session.id"
        class="flex items-center gap-4 px-5 py-4"
      >
        <div class="flex size-9 shrink-0 items-center justify-center rounded-lg bg-elevated text-muted">
          <UIcon
            name="i-lucide-mic"
            class="size-4"
          />
        </div>

        <div class="flex flex-1 flex-col min-w-0">
          <span class="text-sm font-medium text-highlighted truncate">{{ session.role }}</span>
          <span class="text-xs text-dimmed">{{ session.date }} · {{ session.duration }}</span>
        </div>

        <span
          v-if="session.score !== null"
          class="text-sm font-medium text-highlighted"
        >
          {{ session.score }}%
        </span>

        <UBadge
          :color="statusColor[session.status]"
          variant="subtle"
          size="sm"
        >
          {{ statusLabel[session.status] }}
        </UBadge>
      </div>
    </div>
  </UCard>
</template>
