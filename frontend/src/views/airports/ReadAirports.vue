<template>
  <div v-if="loading">Loading ...</div>
  <div v-else>
    <div v-if="error">Oops! An error has occured.<br />{{ error.message }}</div>
    <div v-else>
      <h3>Airports</h3>
      <router-link to="/airports/add">Create a new Airport</router-link>
      <table>
        <tr>
          <th>IATA ID</th>
          <th>City</th>
          <th></th>
          <th></th>
        </tr>
        <tr v-for="airport in airports" :key="airport.iataId">
          <td>
            <b>{{ airport.iataId }}</b>
          </td>
          <td>{{ airport.city }}</td>
          <td>
            <router-link :to="`/airports/${airport.iataId}/update`"
              >Update</router-link
            >
          </td>
          <td>
            <a
              v-if="!airport.arrivals && !airport.departures"
              href="#"
              @click="destroyAirport(airport.iataId)"
              >Delete</a
            >
          </td>
        </tr>
      </table>
    </div>
  </div>
  <router-link to="/">Quit to previous</router-link>
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import { get, destroy } from "../../utils";

export default defineComponent({
  components: {},
  setup() {
    const loading = ref(true);
    const error = ref(null);
    const airports = ref(null);

    const getAirports = async () => {
      try {
        airports.value = await get("airport");
      } catch (e) {
        error.value = e;
        console.error(e.message);
      }
    };

    const destroyAirport = async (iataId) => {
      try {
        await destroy(`airport/${iataId}`);
        console.log(airports.value);
        airports.value = airports.value.filter(
          (airport) => airport.iataId !== iataId
        );
      } catch (e) {
        console.error(e);
      }
    };

    onMounted(async () => {
      loading.value = true;
      await getAirports();
      loading.value = false;
    });

    return {
      loading,
      error,
      airports,
      destroyAirport,
    };
  },
});
</script>

<style scoped></style>
