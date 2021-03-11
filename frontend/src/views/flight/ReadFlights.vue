<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial">Oops! Unable to load flights.</div>
    <div v-else>
      <h3 class="mt-1">Flights 🛫</h3>
      <router-link to="/flight/add" tag="button" class="btn btn-primary"
        >Create a new Flight</router-link
      >
      <table class="table table-striped table-bordered table-sm mt-3">
        <thead>
          <tr class="table-primary">
            <th scope="col">ID</th>
            <th scope="col">Origin Airport</th>
            <th scope="col">Destination Airport</th>
            <th scope="col">Departure Time</th>
            <th scope="col">Arrival Time</th>
            <th scope="col">Flight Length (hours)</th>
            <th scope="col">Total Seats</th>
            <th scope="col">Reserved Seats</th>
            <th scope="col">Seat Price</th>
            <th scope="col"></th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="flight in flights" :key="flight.id">
            <th scope="row">{{ flight.id }}</th>
            <td>{{ flight.originAirport.iataId }}</td>
            <td>{{ flight.destinationAirport.iataId }}</td>
            <td>
              {{ instantToLocalDateTimeString(flight.departureTime) }}
            </td>
            <td>
              {{ instantToLocalDateTimeString(flight.arrivalTime) }}
            </td>
            <td>{{ roundToTwoDecimals(flight.flightLengthInHours) }}</td>
            <td>{{ flight.totalSeats }}</td>
            <td>{{ flight.reservedSeats }}</td>
            <td>
              {{ formatMoney(flight.seatPrice) }}
            </td>
            <td>
              <router-link
                :to="`/flight/${flight.id}/update`"
                tag="button"
                class="btn btn-warning btn-sm"
                >Update</router-link
              >
            </td>
            <td>
              <a
                href="#"
                @click="destroyFlight(flight.id)"
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
import {
  get,
  destroy,
  instantToLocalDateTimeString,
  roundToTwoDecimals,
  formatMoney,
} from "../../utils";

export default defineComponent({
  components: {},
  setup() {
    const loadingInitial = ref(true);
    const errorInitial = ref(null);
    const flights = ref(null);

    const getFlights = async () => {
      try {
        flights.value = await get("flight");
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    const destroyFlight = async (flightId) => {
      try {
        if (
          confirm(
            "Are you sure you want to delete this flight? THIS WILL DELETE ALL ASSOCIATED SEATS AND TICKETS."
          )
        ) {
          await destroy(`flight/${flightId}`);
          flights.value = flights.value.filter(
            (flight) => flight.id !== flightId
          );
        }
      } catch (e) {
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getFlights();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      flights,
      destroyFlight,
      instantToLocalDateTimeString,
      formatMoney,
      roundToTwoDecimals,
    };
  },
});
</script>

<style scoped></style>
