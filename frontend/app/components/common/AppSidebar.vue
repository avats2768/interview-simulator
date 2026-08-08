<script setup>
import AppLogo from './AppLogo.vue'
import SidebarItem from './SidebarItem.vue'

const sidebar = useSidebarStore()
const { primaryItems, footerItems } = useNavigation()
</script>

<template>
  <!-- Desktop rail -->
  <aside
    class="hidden md:flex md:flex-col fixed inset-y-0 left-0 z-30 border-r border-default bg-default transition-[width] duration-300 ease-in-out"
    :class="sidebar.collapsed ? 'md:w-[76px]' : 'md:w-64'"
  >
    <div
      class="flex h-16 shrink-0 items-center border-b border-default px-4"
      :class="sidebar.collapsed ? 'justify-center' : 'justify-between'"
    >
      <AppLogo :collapsed="sidebar.collapsed" />

      <UButton
        v-if="!sidebar.collapsed"
        icon="i-lucide-panel-left-close"
        color="neutral"
        variant="ghost"
        aria-label="Collapse sidebar"
        @click="sidebar.toggleCollapsed()"
      />
    </div>

    <div
      v-if="sidebar.collapsed"
      class="flex justify-center border-b border-default py-2"
    >
      <UButton
        icon="i-lucide-panel-left-open"
        color="neutral"
        variant="ghost"
        aria-label="Expand sidebar"
        @click="sidebar.toggleCollapsed()"
      />
    </div>

    <nav class="flex-1 overflow-y-auto px-3 py-4 space-y-1">
      <SidebarItem
        v-for="item in primaryItems"
        :key="item.to"
        :label="item.label"
        :to="item.to"
        :icon="item.icon"
        :collapsed="sidebar.collapsed"
      />
    </nav>

    <div class="border-t border-default px-3 py-3 space-y-1">
      <SidebarItem
        v-for="item in footerItems"
        :key="item.to"
        :label="item.label"
        :to="item.to"
        :icon="item.icon"
        :collapsed="sidebar.collapsed"
      />
    </div>
  </aside>

  <!-- Mobile slide-over -->
  <USlideover
    v-model:open="sidebar.mobileOpen"
    side="left"
    :ui="{ content: 'w-72 max-w-[80vw]' }"
  >
    <template #content>
      <div class="flex h-16 shrink-0 items-center justify-between px-4 border-b border-default">
        <AppLogo />
        <UButton
          icon="i-lucide-x"
          color="neutral"
          variant="ghost"
          aria-label="Close menu"
          @click="sidebar.closeMobile()"
        />
      </div>

      <nav
        class="flex-1 overflow-y-auto px-3 py-4 space-y-1"
        @click="sidebar.closeMobile()"
      >
        <SidebarItem
          v-for="item in primaryItems"
          :key="item.to"
          :label="item.label"
          :to="item.to"
          :icon="item.icon"
        />
      </nav>

      <div
        class="border-t border-default px-3 py-3 space-y-1"
        @click="sidebar.closeMobile()"
      >
        <SidebarItem
          v-for="item in footerItems"
          :key="item.to"
          :label="item.label"
          :to="item.to"
          :icon="item.icon"
        />
      </div>
    </template>
  </USlideover>
</template>
