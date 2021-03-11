<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial">Oops! Unable to load airports.</div>
    <div v-else>
      <h3 class="mt-1">Airports 🏙️</h3>
      <router-link to="/airport/add" tag="button" class="btn btn-primary"
        >Create a new Airport</router-link
      >
      <table class="table table-striped table-bordered table-sm mt-3">
        <thead>
          <tr class="table-primary">
            <th scope="col">IATA ID</th>
            <th scope="col">City</th>
            <th scope="col"></th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="airport in airports" :key="airport.iataId">
            <th scope="row">{{ airport.iataId }}</th>
            <td>{{ airport.city }}</td>
            <td>
              <router-link
                :to="`/airport/${airport.iataId}/update`"
                tag="button"
                class="btn btn-warning btn-sm"
                >Update</router-link
              >
            </td>
            <td>
              <a
                href="#"
                @click="destroyAirport(airport.iataId)"
                class="btn btn-danger btn-sm"
                >Delete</a
              >
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <router-link to="/" tag="button" class="btn btn-secondary">Back</router-link>
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import { get, destroy } from "../../utils";

export default defineComponent({
  components: {},
  setup() {
    const loadingInitial = ref(true);
    const errorInitial = ref(null);
    const airports = ref(null);

    const getAirports = async () => {
      try {
        airports.value = await get("airport");
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    const destroyAirport = async (airportId) => {
      try {
        if (
          confirm(
            "Are you sure you want to delete this airport? THIS WILL DELETE ALL ASSOCIATED FLIGHTS, SEATS AND TICKETS."
          )
        ) {
          await destroy(`airport/${airportId}`);
          airports.value = airports.value.filter(
            (airport) => airport.iataId !== airportId
          );
        }
      } catch (e) {
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getAirports();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      airports,
      destroyAirport,
    };
  },
});
</script>

<style scoped></style>
