<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';

interface Reservation {
  id: number;
  client: string;
  dateHeure: string;
  idLieu: string;
  nbPassager: number;
  lieuCode: string;
}

const reservations = ref<Reservation[]>([]);
const isSuccess = ref(false);
const filterDate = ref('');
const loading = ref(false);

const API_BASE_URL = 'http://localhost:9000/api/reservations/list';

const fetchReservations = async () => {
  loading.value = true;
  try {
    const { data } = await axios.get(API_BASE_URL, {
      params: { filterDate: filterDate.value || null }
    });
    reservations.value = data.reservations;
    isSuccess.value = data.success;
  } catch (error) {
    console.error("Erreur API:", error);
    isSuccess.value = false;
  } finally {
    loading.value = false;
  }
};

const formatDate = (dateStr: string) => {
  return new Date(dateStr).toLocaleString('fr-FR', {
    dateStyle: 'short',
    timeStyle: 'short'
  });
};

onMounted(fetchReservations);
</script>

<template>
  <div class="reservation-container">
    <header class="content-header">
      <div class="text-group">
        <h1>Réservations</h1>
        <p>Flux opérationnel des véhicules</p>
      </div>

      <div class="filter-box">
        <label for="date-input">Filtrer par date</label>
        <input 
          id="date-input"
          type="date" 
          v-model="filterDate" 
          @change="fetchReservations" 
        />
      </div>
    </header>

    <div v-if="!loading" :class="['status-bar', isSuccess ? 'status-ok' : 'status-ko']">
      {{ isSuccess ? 'Données à jour' : 'Déconnexion du serveur distant' }}
    </div>

    <div v-if="loading" class="loader">
      <div class="spinner"></div>
    </div>
    
    <div v-else class="table-wrapper">
      <table v-if="reservations.length > 0">
        <thead>
          <tr>
            <th>Référence</th>
            <th>Client</th>
            <th>Date / Heure</th>
            <th>Destination</th>
            <th>Passagers</th>
            <th>Lieu</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="res in reservations" :key="res.id">
            <td class="id-tag">#{{ res.id }}</td>
            <td class="client-name">{{ res.client }}</td>
            <td>{{ formatDate(res.dateHeure) }}</td>
            <td><span class="location-badge">{{ res.idLieu }}</span></td>
            <td><span class="pax-count">{{ res.nbPassager }}</span> pers.</td>
            <td class="lieu-code">{{ res.lieuCode }}</td>
          </tr>
        </tbody>
      </table>
      
      <div v-else class="empty-state">
        <p>Aucune réservation trouvée pour cette sélection.</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Utilisation des variables CSS définies dans main.css */
.reservation-container {
  max-width: 1000px;
  margin: 0 auto;
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

h1 {
  font-size: 1.75rem;
  margin: 0;
  color: var(--text-main);
}

.text-group p {
  color: var(--text-muted);
  margin: 4px 0 0;
}

/* Input Styles */
.filter-box {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.filter-box label {
  font-size: 0.75rem;
  font-weight: 700;
  text-transform: uppercase;
  color: var(--text-muted);
}

input[type="date"] {
  background: var(--bg-sidebar);
  border: 1px solid var(--border-color);
  color: var(--text-main);
  padding: 8px 12px;
  border-radius: 8px;
  outline: none;
}

/* Table Design */
.table-wrapper {
  background: var(--bg-sidebar);
  border: 1px solid var(--border-color);
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0,0,0,0.05);
}

table {
  width: 100%;
  border-collapse: collapse;
}

th {
  text-align: left;
  padding: 16px;
  background: rgba(0,0,0,0.02);
  color: var(--text-muted);
  font-size: 0.85rem;
  font-weight: 600;
  border-bottom: 1px solid var(--border-color);
}

td {
  padding: 16px;
  border-bottom: 1px solid var(--border-color);
  color: var(--text-main);
  font-size: 0.95rem;
}

tr:last-child td {
  border-bottom: none;
}

tr:hover {
  background: rgba(var(--accent-color), 0.05);
}

/* Custom Cells */
.id-tag {
  font-family: 'Courier New', Courier, monospace;
  color: var(--text-muted);
}

.client-name {
  font-weight: 600;
}
.lieu-code {
  font-weight: 600;
}
.location-badge {
  background: var(--bg-app);
  padding: 4px 10px;
  border-radius: 6px;
  font-size: 0.85rem;
  border: 1px solid var(--border-color);
}

.pax-count {
  font-weight: bold;
  color: var(--accent-color);
}

/* Status Banner */
.status-bar {
  display: inline-block;
  padding: 6px 16px;
  border-radius: 20px;
  font-size: 0.85rem;
  margin-bottom: 1.5rem;
  font-weight: 500;
}

.status-ok { background: #dcfce7; color: #16a34a; }
.status-ko { background: #fee2e2; color: #dc2626; }

/* Loader */
.loader { padding: 3rem; text-align: center; }
.spinner {
  width: 30px; height: 30px;
  border: 3px solid var(--border-color);
  border-top-color: var(--accent-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  display: inline-block;
}
@keyframes spin { to { transform: rotate(360deg); } }

.empty-state {
  padding: 3rem;
  text-align: center;
  color: var(--text-muted);
}
</style>