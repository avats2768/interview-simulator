<script setup>
import AppBreadcrumb from './AppBreadcrumb.vue'
import NotificationButton from './NotificationButton.vue'
import ThemeToggle from './ThemeToggle.vue'
import UserDropdown from './UserDropdown.vue'

const sidebar = useSidebarStore()
const { pageTitle } = useNavigation()

const search = ref('')
</script>

<template>
  <header
    class="fixed inset-x-0 top-0 z-20 h-16 border-b border-default bg-default/80 backdrop-blur transition-[padding] duration-300 ease-in-out"
    :class="sidebar.collapsed ? 'md:pl-[76px]' : 'md:pl-64'"
  >
    <div class="flex h-full items-center gap-3 px-4 sm:px-6">
      <UButton
        icon="i-lucide-menu"
        color="neutral"
        variant="ghost"
        class="md:hidden"
        aria-label="Open menu"
        @click="sidebar.openMobile()"
      />

      <div class="flex flex-col min-w-0">
        <AppBreadcrumb class="hidden sm:flex" />
        <h2 class="text-sm font-semibold text-highlighted truncate sm:hidden">
          {{ pageTitle }}
        </h2>
      </div>

      <div class="flex-1" />

      <UInput
        v-model="search"
        icon="i-lucide-search"
        placeholder="Search..."
        variant="subtle"
        class="hidden md:block w-64"
      />

      <UButton
        icon="i-lucide-search"
        color="neutral"
        variant="ghost"
        class="md:hidden"
        aria-label="Search"
      />

      <NotificationButton />
      <ThemeToggle />

      <USeparator
        orientation="vertical"
        class="h-6 hidden sm:block"
      />

      <UserDropdown />
    </div>
  </header>
</template>
