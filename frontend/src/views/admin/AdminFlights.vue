<template>
  <u>Flights</u>
  <div v-if="loading">Loading flights...</div>
  <div v-else>
    <div v-if="error">
      Oops! Unable to load flights at this time. Please try again later.
    </div>
    <div v-else>
      <ul>
        <li>
          <router-link to="/admn/main/flights/add">Add Flight</router-link>
        </li>
        <li v-for="flight in flights" :key="flight.id">
          {{ flight.route.origin.iataId }},
          {{ flight.route.origin.city }} &rarr;
          {{ flight.route.destination.iataId }},
          {{ flight.route.destination.city }}
          <router-link :to="`/admn/main/flight/${flight.id}`"
            >Manage</router-link
          >
          | <a href="#">Delete</a>
        </li>
      </ul>
    </div>
  </div>
  <router-link to="/admn/main">Quit to previous</router-link>
</template>

<script>
import { defineComponent, defineProps, onMounted, reactive, ref } from "vue";
import { BASE_URL } from "../../constants";

export default defineComponent({
  setup() {
    const flights = ref(null);
    const loading = ref(true);
    const error = ref(null);

    const fetchFlights = async () => {
      loading.value = true;
      try {
        const result = await fetch(`${BASE_URL}/flight`, {
          method: "get",
          headers: {
            "content-type": "application/json",
          },
        });
        if (!result.ok) {
          const error = new Error(result.statusText);
          error.json = result.json();
          throw error;
        }
        flights.value = await result.json();
      } catch (err) {
        error.value = err;
      } finally {
        loading.value = false;
      }
    };

    onMounted(async () => {
      await fetchFlights();
    });

    return {
      flights,
      loading,
      error,
    };
  },
});
</script>

<style scoped></style>
