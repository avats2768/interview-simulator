<script setup>
const { breadcrumbs } = useNavigation()

const links = computed(() =>
  breadcrumbs.value.map(item => ({ label: item.label, to: item.to }))
)
</script>

<template>
  <UBreadcrumb :items="links" />
</template>
