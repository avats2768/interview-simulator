<script setup>
import PageHeader from '~/components/common/PageHeader.vue'
import StatCard from '~/components/dashboard/StatCard.vue'
import ScoreTrendChart from '~/components/dashboard/ScoreTrendChart.vue'
import SkillBreakdown from '~/components/dashboard/SkillBreakdown.vue'
import UpcomingSession from '~/components/dashboard/UpcomingSession.vue'
import RecentSessionsList from '~/components/dashboard/RecentSessionsList.vue'

definePageMeta({ title: 'Dashboard' })

// Static placeholder data for this phase -- will be replaced by real API/state.
const stats = [
  {
    label: 'Interviews Completed',
    value: '12',
    icon: 'i-lucide-mic',
    trend: { value: '+3', positive: true }
  },
  {
    label: 'Average Score',
    value: '82%',
    icon: 'i-lucide-target',
    trend: { value: '+5%', positive: true }
  },
  {
    label: 'Coding Rounds Passed',
    value: '8 / 10',
    icon: 'i-lucide-code-2',
    trend: { value: '+2', positive: true }
  },
  {
    label: 'Resume Score',
    value: '91 / 100',
    icon: 'i-lucide-file-text',
    trend: { value: '-1', positive: false }
  }
]

const scoreTrend = [
  { label: 'Mon', score: 62 },
  { label: 'Tue', score: 68 },
  { label: 'Wed', score: 74 },
  { label: 'Thu', score: 70 },
  { label: 'Fri', score: 85 },
  { label: 'Sat', score: 82 },
  { label: 'Sun', score: 90 }
]

const skills = [
  { label: 'Technical Skills', score: 88 },
  { label: 'Communication', score: 76 },
  { label: 'Problem Solving', score: 82 },
  { label: 'System Design', score: 65 }
]

const recentSessions = [
  {
    id: '1',
    role: 'Frontend Engineer · Round 2',
    date: 'Aug 4, 2026',
    duration: '45 min',
    score: 88,
    status: 'completed'
  },
  {
    id: '2',
    role: 'Coding Round · Arrays & Strings',
    date: 'Aug 2, 2026',
    duration: '60 min',
    score: 74,
    status: 'completed'
  },
  {
    id: '3',
    role: 'System Design · Round 1',
    date: 'Jul 30, 2026',
    duration: '50 min',
    score: null,
    status: 'in-progress'
  },
  {
    id: '4',
    role: 'Behavioral Interview',
    date: 'Jul 28, 2026',
    duration: '30 min',
    score: 91,
    status: 'completed'
  },
  {
    id: '5',
    role: 'Frontend Engineer · Round 1',
    date: 'Jul 24, 2026',
    duration: '45 min',
    score: null,
    status: 'missed'
  }
]
</script>

<template>
  <div class="flex flex-col gap-6">
    <PageHeader
      title="Dashboard"
      description="Welcome back, Ethan. Here's an overview of your interview prep."
    >
      <template #actions>
        <UButton label="Start Mock Interview" icon="i-lucide-mic" to="/interview" />
      </template>
    </PageHeader>

    <div class="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4">
      <StatCard
        v-for="stat in stats"
        :key="stat.label"
        :label="stat.label"
        :value="stat.value"
        :icon="stat.icon"
        :trend="stat.trend"
      />
    </div>

    <div class="grid grid-cols-1 gap-4 lg:grid-cols-3">
      <ScoreTrendChart :sessions="scoreTrend" class="lg:col-span-2" />
      <UpcomingSession
        role="Frontend Engineer"
        date="Aug 8, 2026"
        time="10:00 AM"
        duration="45 min"
      />
    </div>

    <div class="grid grid-cols-1 gap-4 lg:grid-cols-3">
      <RecentSessionsList :sessions="recentSessions" class="lg:col-span-2" />
      <SkillBreakdown :skills="skills" />
    </div>
  </div>
</template>