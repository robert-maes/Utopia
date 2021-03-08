<template>
  <div v-if="loading">Loading ...</div>
  <div v-else>
    <div v-if="error">Oops! An error has occured.<br />{{ error.message }}</div>
    <div v-else>
      <h3>Flights</h3>
      <router-link to="/flights/add">Create a new Flight</router-link>
      <table>
        <tr>
          <th>ID</th>
          <th>From</th>
          <th>To</th>
          <th>Departure Time</th>
          <th>Arrival Time</th>
          <th>Reserved Seats</th>
          <th>Seat Price</th>
          <th></th>
          <th></th>
        </tr>
        <tr v-for="flight in flights" :key="flight.id">
          <td>
            <b>{{ flight.id }}</b>
          </td>
          <td>{{ flight.route.origin.iataId }}</td>
          <td>{{ flight.route.destination.iataId }}</td>
          <td>
            {{ new Date(flight.departureTime * 1000).toLocaleString("en-US") }}
          </td>
          <td>
            {{ new Date(flight.arrivalTime * 1000).toLocaleString("en-US") }}
          </td>
          <td>{{ flight.reservedSeats }}</td>
          <td>{{ "$" + flight.seatPrice.toFixed(2) }}</td>
          <td>
            <router-link :to="`/flights/${flight.id}/update`"
              >Update</router-link
            >
          </td>
          <td>
            <a href="#" @click="destroyFlight(flight.id)">Delete</a>
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
    const flights = ref(null);

    const getFlights = async () => {
      try {
        flights.value = await get("flight");
      } catch (e) {
        error.value = e;
        console.error(e.message);
      }
    };

    const destroyFlight = async (flightId) => {
      try {
        await destroy(`flight/${flightId}`);
        flights.value = flights.value.filter(
          (flight) => flight.id !== flightId
        );
      } catch (e) {
        console.error(e);
      }
    };

    onMounted(async () => {
      loading.value = true;
      await getFlights();
      loading.value = false;
    });

    return {
      loading,
      error,
      flights,
      destroyFlight,
    };
  },
});
</script>

<style scoped></style>
