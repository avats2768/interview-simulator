<script setup>
import AppSidebar from '~/components/common/AppSidebar.vue';
import AppTopbar from '~/components/common/AppTopbar.vue';

const sidebar = useSidebarStore()
</script>

<template>
  <div class="min-h-screen bg-muted">
    <AppSidebar />
    <AppTopbar />

    <main
      class="pt-16 min-h-screen transition-[padding] duration-300 ease-in-out"
      :class="sidebar.collapsed ? 'md:pl-[76px]' : 'md:pl-64'"
    >
      <div class="p-4 sm:p-6 lg:p-8 max-w-[1600px] mx-auto">
        <slot />
      </div>
    </main>
  </div>
</template>
