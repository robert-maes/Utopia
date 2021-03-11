<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div class="alert alert-warning" v-if="errorInitial">
      Oops! Unable to load seats/travelers.
    </div>
    <div v-else>
      <h3 class="mt-1">Create Ticket</h3>
      <div v-if="errors" class="alert alert-danger">
        <p v-if="errors.message">{{ errors.message }}</p>
        <ul v-else>
          <li v-for="error in errors" :key="error.fieldName">
            {{ error.message }}
          </li>
        </ul>
      </div>
      <form v-on:submit.prevent="createTicket">
        <table>
          <tr>
            <td>
              <label for="travelerId" class="form-label">Traveler:</label>
            </td>
            <td>
              <select
                id="travelerId"
                required
                v-model="travelerId"
                class="form-select"
              >
                <option
                  v-for="traveler in travelers"
                  :key="traveler.id"
                  :value="traveler.id"
                >
                  [{{ traveler.id }}]
                  {{ traveler.givenName + " " + traveler.familyName }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              <label for="seatId" class="form-label">Seat:</label>
            </td>
            <td>
              <select id="seatId" required v-model="seatId" class="form-select">
                <option v-for="seat in seats" :key="seat.id" :value="seat.id">
                  [{{ seat.id }}] - {{ seat.seatClass }} -
                  {{ seat.flight.originAirport.iataId }} &rarr;
                  {{ seat.flight.destinationAirport.iataId }}
                </option>
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
  <router-link to="/ticket" tag="button" class="btn btn-secondary mt-1"
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
    const seats = ref(null);
    const travelers = ref(null);
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();
    const travelerId = ref(null);
    const seatId = ref(null);

    const createTicket = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await post("ticket", {
          travelerId: travelerId.value,
          seatId: seatId.value,
        });
        await router.push({
          path: "/ticket",
        });
      } catch (e) {
        errors.value = JSON.parse(e.message);
        console.error(e);
      } finally {
        loading.value = false;
      }
    };

    const getSeats = async () => {
      try {
        seats.value = await get("seat");
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    const getTravelers = async () => {
      try {
        travelers.value = await get("traveler");
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getSeats();
      await getTravelers();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      seats,
      travelers,
      loading,
      errors,
      travelerId,
      seatId,
      createTicket,
    };
  },
});
</script>

<style scoped></style>
