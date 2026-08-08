<script setup>
defineProps({
  collapsed: {
    type: Boolean,
    default: false
  }
})
</script>

<template>
  <NuxtLink
    to="/"
    class="flex items-center gap-2.5 overflow-hidden focus-visible:outline-2 focus-visible:outline-primary rounded-md"
  >
    <span
      class="flex size-8 shrink-0 items-center justify-center rounded-lg bg-primary text-inverted font-bold text-sm"
    >
      P
    </span>
    <span
      v-if="!collapsed"
      class="text-base font-semibold text-highlighted whitespace-nowrap"
    >
      Praxis
    </span>
  </NuxtLink>
</template>
