export const useSidebarStore = defineStore('sidebar', () => {
  /** Desktop: rail collapsed to icons only */
  const collapsed = ref(false)
  /** Mobile: slide-over open state */
  const mobileOpen = ref(false)

  function toggleCollapsed() {
    collapsed.value = !collapsed.value
  }

  function openMobile() {
    mobileOpen.value = true
  }

  function closeMobile() {
    mobileOpen.value = false
  }

  return {
    collapsed,
    mobileOpen,
    toggleCollapsed,
    openMobile,
    closeMobile
  }
})
