<script setup lang="ts">
import Sidebar from './components/layout/Sidebar.vue'
import ReservationList from './components/ReservationList.vue'
</script>

<template>
  <div class="app-layout">
    <Sidebar />

    <main class="main-content">
      <header class="top-bar">
        <h2>Tableau de bord - Locations</h2>
      </header>
      
      <section class="page-container">
        <ReservationList />
      </section>
    </main>
  </div>
</template>
