<script setup>
defineProps({
  label: {
    type: String,
    required: true
  },
  to: {
    type: String,
    required: true
  },
  icon: {
    type: String,
    required: true
  },
  collapsed: {
    type: Boolean,
    default: false
  }
})
</script>

<template>
  <UTooltip
    :text="label"
    :disabled="!collapsed"
    :content="{ side: 'right' }"
  >
    <NuxtLink
      :to="to"
      class="group relative flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium text-muted transition-colors duration-150 hover:bg-elevated hover:text-highlighted"
      active-class="!bg-primary !text-inverted hover:!bg-primary"
      :class="collapsed ? 'justify-center px-0' : ''"
    >
      <UIcon
        :name="icon"
        class="size-5 shrink-0"
      />
      <span
        v-if="!collapsed"
        class="truncate"
      >{{ label }}</span>
    </NuxtLink>
  </UTooltip>
</template>
