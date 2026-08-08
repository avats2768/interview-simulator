/**
 * Single source of truth for the app's navigation structure.
 * Both AppSidebar and AppTopbar (page title / breadcrumb) read from here
 * so routes and labels never drift out of sync.
 */
const primaryItems = [
  { label: 'Dashboard', to: '/', icon: 'i-lucide-layout-dashboard' },
  { label: 'Interview', to: '/interview', icon: 'i-lucide-mic' },
  { label: 'Resume', to: '/resume', icon: 'i-lucide-file-text' },
  { label: 'Coding Round', to: '/coding-round', icon: 'i-lucide-code-2' },
  { label: 'Reports', to: '/reports', icon: 'i-lucide-bar-chart-3' },
  { label: 'History', to: '/history', icon: 'i-lucide-history' }
]

const footerItems = [
  { label: 'Profile', to: '/profile', icon: 'i-lucide-user-round' },
  { label: 'Settings', to: '/settings', icon: 'i-lucide-settings' }
]

export const useNavigation = () => {
  const route = useRoute()

  const allItems = computed(() => [...primaryItems, ...footerItems])

  const currentItem = computed(() =>
    allItems.value.find(item => item.to === route.path)
  )

  const pageTitle = computed(() => currentItem.value?.label ?? 'Overview')

  const breadcrumbs = computed(() => {
    if (route.path === '/') return [{ label: 'Dashboard' }]
    return [{ label: 'Dashboard', to: '/' }, { label: pageTitle.value }]
  })

  return {
    primaryItems,
    footerItems,
    pageTitle,
    breadcrumbs
  }
}
