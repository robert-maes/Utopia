<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial" class="alert alert-warning">
      Oops! Unable to load flights.
    </div>
    <div v-else>
      <h3 class="mt-1">Create Seat</h3>
      <div v-if="errors" class="alert alert-danger">
        <p v-if="errors.message">{{ errors.message }}</p>
        <ul v-else>
          <li v-for="error in errors" :key="error.fieldName">
            {{ error.message }}
          </li>
        </ul>
      </div>
      <form v-on:submit.prevent="createSeat">
        <table>
          <tr>
            <td><label for="flightId" class="form-label">Flight:</label></td>
            <td>
              <select
                id="flightId"
                required
                v-model="flightId"
                class="form-select"
              >
                <option
                  v-for="flight in flights"
                  :key="flight.id"
                  :value="flight.id"
                >
                  [{{ flight.id }}] {{ flight.originAirport.iataId }} &rarr;
                  {{ flight.destinationAirport.iataId }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              <label for="seatClass" class="form-label">Seat Class:</label>
            </td>
            <td>
              <select
                id="seatClass"
                required
                v-model="seatClass"
                class="form-select"
              >
                <option value="FIRST">FIRST</option>
                <option value="BUSINESS">BUSINESS</option>
                <option value="ECONOMY">ECONOMY</option>
              </select>
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
  <router-link to="/seat" tag="button" class="btn btn-secondary mt-1"
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
    const flights = ref(null);
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();
    const flightId = ref(null);
    const seatClass = ref(null);

    const createSeat = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await post("seat", {
          flightId: flightId.value,
          seatClass: seatClass.value,
        });
        await router.push({
          path: "/seat",
        });
      } catch (e) {
        errors.value = JSON.parse(e.message);
        console.error(e);
      } finally {
        loading.value = false;
      }
    };

    const getFlights = async () => {
      try {
        flights.value = await get("flight");
      } catch (e) {
        errorInitial.value = e;
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
      loading,
      errors,
      flightId,
      seatClass,
      createSeat,
    };
  },
});
</script>

<style scoped></style>
