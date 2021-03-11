<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial" class="alert alert-warning">
      Oops! Unable to load flight with ID {{ flightId }}
    </div>
    <div v-else>
      <h3 class="mt-1">Update Flight</h3>
      <div v-if="errors" class="alert alert-danger">
        <p v-if="errors.message">{{ errors.message }}</p>
        <ul v-else>
          <li v-for="error in errors" :key="error.fieldName">
            {{ error.message }}
          </li>
        </ul>
      </div>
      <form v-on:submit.prevent="updateFlight">
        <table>
          <tr>
            <td>
              <label for="originAirport" class="form-label"
                >Origin Airport:</label
              >
            </td>
            <td>
              <select
                id="originAirport"
                v-model="originAirport"
                class="form-select"
              >
                <option
                  v-for="airport in airports"
                  :key="airport.iataId"
                  :value="airport.iataId"
                >
                  {{ airport.iataId }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              <label for="destinationAirport" class="form-label"
                >Destination Airport:</label
              >
            </td>
            <td>
              <select
                id="destinationAirport"
                v-model="destinationAirport"
                class="form-select"
              >
                <option
                  v-for="airport in airports"
                  :key="airport.iataId"
                  :value="airport.iataId"
                >
                  {{ airport.iataId }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              {{ departureDateTime }}
              <label for="departureDateTime" class="form-label"
                >Departure Date & Time:</label
              >
            </td>
            <td>
              <input
                type="datetime-local"
                id="departureDateTime"
                :value="departureDateTime"
                @input="departureDateTime = $event.target.value"
                class="form-control"
              />
            </td>
          </tr>
          <tr>
            <td>
              <label for="arrivalDateTime" class="form-label"
                >Arrival Date & Time:</label
              >
            </td>
            <td>
              <input
                type="datetime-local"
                id="arrivalDateTime"
                :value="arrivalDateTime"
                @input="arrivalDateTime = $event.target.value"
                class="form-control"
              />
            </td>
          </tr>
          <tr>
            <td>
              <label for="seatPrice" class="form-label">Seat Price:</label>
            </td>
            <td>
              <input
                type="number"
                step="0.01"
                min="0"
                id="seatPrice"
                v-model="seatPrice"
                class="form-control"
              />
            </td>
          </tr>
          <tr>
            <td>
              <label for="totalSeats" class="form-label">Total Seats:</label>
            </td>
            <td>
              <input
                type="number"
                min="0"
                id="totalSeats"
                v-model="totalSeats"
                class="form-control"
              />
            </td>
          </tr>
          <tr>
            <td></td>
            <td class="text-end">
              <input
                type="submit"
                value="Update"
                :disabled="loading"
                class="btn btn-primary"
              />
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
  <router-link to="/flight" tag="button" class="btn btn-secondary mt-1"
    >Cancel</router-link
  >
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import {
  get,
  put,
  instantToDatetimeLocalValue,
  datetimeLocalValueToInstant,
} from "../../utils";
import { useRouter } from "vue-router";

export default defineComponent({
  components: {},
  props: {
    flightId: String,
  },
  setup(props) {
    const loadingInitial = ref(true);
    const errorInitial = ref(null);
    const flight = ref(null);
    const airports = ref(null);
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();
    const originAirport = ref(null);
    const destinationAirport = ref(null);
    const departureDateTime = ref(null);
    const arrivalDateTime = ref(null);
    const seatPrice = ref(null);
    const totalSeats = ref(null);

    const updateFlight = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await put(`flight/${props.flightId}`, {
          originAirport: originAirport.value,
          destinationAirport: destinationAirport.value,
          departureTime: datetimeLocalValueToInstant(departureDateTime.value),
          arrivalTime: datetimeLocalValueToInstant(arrivalDateTime.value),
          seatPrice: seatPrice.value,
          totalSeats: totalSeats.value,
        });
        await router.push({
          path: "/flight",
        });
      } catch (e) {
        errors.value = JSON.parse(e.message);
        console.error(e);
      } finally {
        loading.value = false;
      }
    };

    const getFlight = async () => {
      try {
        flight.value = await get(`flight/${props.flightId}`);
        originAirport.value = flight.value.originAirport.iataId;
        destinationAirport.value = flight.value.destinationAirport.iataId;
        departureDateTime.value = instantToDatetimeLocalValue(
          flight.value.departureTime
        );
        arrivalDateTime.value = instantToDatetimeLocalValue(
          flight.value.arrivalTime
        );
        seatPrice.value = flight.value.seatPrice;
        totalSeats.value = flight.value.totalSeats;
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    const getAirports = async () => {
      try {
        airports.value = await get("airport");
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getFlight();
      await getAirports();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      flight,
      airports,
      loading,
      errors,
      originAirport,
      destinationAirport,
      departureDateTime,
      arrivalDateTime,
      seatPrice,
      totalSeats,
      updateFlight,
    };
  },
});
</script>

<style scoped></style>
