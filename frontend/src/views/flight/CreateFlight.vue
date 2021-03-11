<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div class="alert alert-warning" v-if="errorInitial">
      Oops! Unable to load airports.
    </div>
    <div v-else>
      <h3 class="mt-1">Create Flight</h3>
      <div v-if="errors" class="alert alert-danger">
        <p v-if="errors.message">{{ errors.message }}</p>
        <ul v-else>
          <li v-for="error in errors" :key="error.fieldName">
            {{ error.message }}
          </li>
        </ul>
      </div>
      <form v-on:submit.prevent="createFlight">
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
                required
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
                required
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
              <label for="departureDateTime" class="form-label"
                >Departure Date & Time:</label
              >
            </td>
            <td>
              <input
                type="datetime-local"
                required
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
                required
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
                required
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
                required
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
                value="Submit"
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
import { get, post, datetimeLocalValueToInstant } from "../../utils";
import { useRouter } from "vue-router";

export default defineComponent({
  components: {},
  setup() {
    const loadingInitial = ref(true);
    const errorInitial = ref(null);
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

    const createFlight = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await post("flight", {
          originAirportId: originAirport.value,
          destinationAirportId: destinationAirport.value,
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
      await getAirports();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      airports,
      loading,
      errors,
      createFlight,
      originAirport,
      destinationAirport,
      departureDateTime,
      arrivalDateTime,
      seatPrice,
      totalSeats,
    };
  },
});
</script>

<style scoped></style>
