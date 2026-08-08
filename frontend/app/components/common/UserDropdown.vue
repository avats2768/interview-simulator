<script setup>
// Placeholder session info for the layout phase.
const user = {
  name: 'Ethan Miller',
  role: 'Candidate',
  avatar: undefined
}

const toast = useToast()

async function logout() {
  // Placeholder for the layout phase -- clear real session/auth state here once wired up.
  toast.add({
    title: 'Signed out',
    description: 'You have been logged out.',
    color: 'neutral',
    icon: 'i-lucide-log-out'
  })
  await navigateTo('/auth/login')
}

const items = [
  [
    { label: 'Profile', icon: 'i-lucide-user-round', to: '/profile' },
    { label: 'Settings', icon: 'i-lucide-settings', to: '/settings' }
  ],
  [
    { label: 'Log out', icon: 'i-lucide-log-out', color: 'error', onSelect: logout }
  ]
]
</script>

<template>
  <UDropdownMenu
    :items="items"
    :content="{ align: 'end' }"
  >
    <button
      type="button"
      class="flex items-center gap-2.5 rounded-lg px-1.5 py-1 hover:bg-elevated transition-colors duration-150 focus-visible:outline-2 focus-visible:outline-primary"
    >
      <UAvatar
        :src="user.avatar"
        :alt="user.name"
        :text="getInitials(user.name)"
        size="sm"
      />
      <span class="hidden md:flex flex-col items-start leading-tight">
        <span class="text-sm font-medium text-highlighted">{{ user.name }}</span>
        <span class="text-xs text-dimmed">{{ user.role }}</span>
      </span>
      <UIcon
        name="i-lucide-chevron-down"
        class="hidden md:block size-4 text-dimmed"
      />
    </button>
  </UDropdownMenu>
</template>