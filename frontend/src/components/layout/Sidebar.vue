<script setup lang="ts">
import { ref } from 'vue'

const isDark = ref(false)
const toggleTheme = () => {
  isDark.value = !isDark.value
  document.documentElement.setAttribute('data-theme', isDark.value ? 'dark' : 'light')
}
</script>

<template>
  <aside class="sidebar">
    <div class="brand">
      <div class="logo-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
          <path d="M13 2L3 14h9l-1 8 10-12h-9l1-8z" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
      <span>Velocity<strong>Rent</strong></span>
    </div>

    <nav class="nav-links">
      <a href="#">
        <svg class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
          <line x1="16" y1="2" x2="16" y2="6"/>
          <line x1="8" y1="2" x2="8" y2="6"/>
          <line x1="3" y1="10" x2="21" y2="10"/>
        </svg>
        Reservations
      </a>
    </nav>

    <div class="theme-switcher" @click="toggleTheme">
      <div class="theme-info">
        <svg v-if="isDark" class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"/>
        </svg>
        <svg v-else class="icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
          <circle cx="12" cy="12" r="5"/><line x1="12" y1="1" x2="12" y2="3"/><line x1="12" y1="21" x2="12" y2="23"/><line x1="4.22" y1="4.22" x2="5.64" y2="5.64"/><line x1="18.36" y1="18.36" x2="19.78" y2="19.78"/><line x1="1" y1="12" x2="3" y2="12"/><line x1="21" y1="12" x2="23" y2="12"/><line x1="4.22" y1="19.78" x2="5.64" y2="18.36"/><line x1="18.36" y1="5.64" x2="19.78" y2="4.22"/>
        </svg>
        <span>{{ isDark ? 'Dark' : 'Light' }}</span>
      </div>
      <div class="switch-base">
        <div class="switch-dot" :class="{ 'is-active': isDark }"></div>
      </div>
    </div>
  </aside>
</template>

<style scoped>
.sidebar {
  width: 280px;
  /* Effet Glassmorphism */
  background: var(--bg-sidebar-glass); 
  backdrop-filter: blur(15px);
  -webkit-backdrop-filter: blur(15px);
  border-right: 1px solid var(--border-glass);
  
  display: flex;
  flex-direction: column;
  padding: 2rem 1.2rem;
  height: 100vh;
  position: sticky;
  top: 0;
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;
  font-size: 1.3rem;
  margin-bottom: 3.5rem;
  padding-left: 0.5rem;
  color: var(--text-main);
}

.logo-icon {
  background: linear-gradient(135deg, var(--accent-color), var(--accent-hover));
  color: white;
  width: 38px;
  height: 38px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 15px var(--accent-soft);
}

.logo-icon svg { width: 22px; height: 22px; }

.nav-links {
  display: flex;
  flex-direction: column;
  gap: 10px;
  flex: 1;
}

.nav-links a {
  text-decoration: none;
  color: var(--text-muted);
  padding: 12px 16px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  gap: 14px;
  font-weight: 500;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  border: 1px solid transparent;
}

.icon {
  width: 20px;
  height: 20px;
  stroke-width: 2;
}

.nav-links a:hover {
  background: var(--accent-soft);
  color: var(--accent-color);
  transform: translateX(4px);
}

.nav-links a.active {
  background: var(--accent-color);
  color: white;
  box-shadow: 0 8px 20px var(--accent-soft);
}

/* Theme Switcher Style */
.theme-switcher {
  margin-top: auto;
  padding: 12px 16px;
  background: rgba(var(--text-main-rgb), 0.05);
  border: 1px solid var(--border-glass);
  border-radius: 16px;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: background 0.3s;
}

.theme-info {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 0.9rem;
  font-weight: 600;
}

.switch-base {
  width: 36px;
  height: 20px;
  background: var(--text-muted);
  border-radius: 20px;
  position: relative;
  opacity: 0.4;
}

.switch-dot {
  width: 14px;
  height: 14px;
  background: white;
  border-radius: 50%;
  position: absolute;
  top: 3px;
  left: 3px;
  transition: transform 0.3s;
}

.switch-dot.is-active {
  transform: translateX(16px);
  background: var(--accent-color);
}
</style>