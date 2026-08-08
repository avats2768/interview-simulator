<script setup>
// Placeholder content for the layout phase -- real data wiring comes with the dashboard feature.
const notifications = [
  { id: '1', title: 'Your mock interview report is ready', time: '2h ago' },
  { id: '2', title: 'New coding round unlocked', time: '1d ago' },
  { id: '3', title: 'Resume feedback received', time: '2d ago' }
]
</script>

<template>
  <UPopover :content="{ align: 'end', side: 'bottom', sideOffset: 8 }">
    <div class="relative inline-flex">
      <UButton
        icon="i-lucide-bell"
        color="neutral"
        variant="ghost"
        aria-label="Notifications"
      />
      <span
        v-if="notifications.length"
        class="pointer-events-none absolute right-1.5 top-1.5 size-1.5 rounded-full bg-primary"
      />
    </div>

    <template #content>
      <div class="w-72 p-2">
        <p class="px-2 py-1.5 text-xs font-medium text-dimmed uppercase tracking-wide">
          Notifications
        </p>
        <div class="flex flex-col">
          <div
            v-for="item in notifications"
            :key="item.id"
            class="flex flex-col gap-0.5 rounded-md px-2 py-2 hover:bg-elevated transition-colors duration-150"
          >
            <span class="text-sm text-highlighted">{{ item.title }}</span>
            <span class="text-xs text-dimmed">{{ item.time }}</span>
          </div>
        </div>
      </div>
    </template>
  </UPopover>
</template>
