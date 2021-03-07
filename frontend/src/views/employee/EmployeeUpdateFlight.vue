<template>
  <u>Update Flight</u>
  <div v-if="loading">Loading flight...</div>
  <div v-else>
    <div v-if="error">
      Oops! Unable to load your flight. Please make sure it is valid and still
      exists or try again later.
    </div>
    <div v-else>
      <form v-on:submit.prevent="onSubmit">
        <table>
          <tr>
            <td>
              <label for="originAirport">Origin Airport</label>
            </td>
            <td>
              <label for="originCity">Origin City</label>
            </td>
          </tr>
          <tr>
            <td>
              <input
                type="text"
                id="originAirport"
                v-model="originAirport"
                minlength="3"
                maxlength="3"
              />
            </td>
            <td><input type="text" id="originCity" v-model="originCity" /></td>
          </tr>
          <tr>
            <td>
              <label for="destinationAirport">Destination Airport</label>
            </td>
            <td>
              <label for="destinationCity">Destination City</label>
            </td>
          </tr>
          <tr>
            <td>
              <input
                type="text"
                id="destinationAirport"
                v-model="destinationAirport"
                minlength="3"
                maxlength="3"
              />
            </td>
            <td>
              <input
                type="text"
                id="destinationCity"
                v-model="destinationCity"
              />
            </td>
          </tr>
          <tr>
            <td>
              <label for="departureDate">Departure Date (YYYY-MM-DD)</label>
            </td>
            <td>
              <label for="departureTime">Depature Time (HH:MM)</label>
            </td>
          </tr>
          <tr>
            <td>
              <input type="date" id="departureDate" v-model="departureDate" />
            </td>
            <td>
              <input type="time" id="departureTime" v-model="departureTime" />
            </td>
          </tr>
          <tr>
            <td>
              <label for="arrivalDate">Arrival Date (YYYY-MM-DD)</label>
            </td>
            <td>
              <label for="arrivalTime">Arrrival Time (HH:MM)</label>
            </td>
          </tr>
          <tr>
            <td>
              <input
                disabled
                type="date"
                id="arrivalDate"
                v-model="arrivalDate"
              />
            </td>
            <td>
              <input
                disabled
                type="time"
                id="arrivalTime"
                v-model="arrivalTime"
              />
            </td>
          </tr>
          <tr>
            <td></td>
            <td>
              <input
                type="submit"
                value="Update Flight"
                @click="updateFlight"
                :disabled="!canSubmit"
              />
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
  <router-link :to="`/emp/main/flight/${flightId}`"
    >Quit to previous</router-link
  >
</template>

<script>
import {
  computed,
  defineComponent,
  defineProps,
  onMounted,
  reactive,
  ref,
} from "vue";
import { BASE_URL } from "../../constants";

export default defineComponent({
  props: {
    flightId: Number,
  },
  setup(props) {
    const flight = ref(null);
    const loading = ref(true);
    const error = ref(null);
    const showDetails = ref(false);
    const canSubmit = ref(true);

    const originAirport = ref("");
    const originCity = ref("");
    const destinationAirport = ref("");
    const destinationCity = ref("");
    const departureDate = ref("");
    const departureTime = ref("");
    const arrivalDate = ref("");
    const arrivalTime = ref("");

    //TODO: fix time
    const departureDateTime = computed(() => {
      if (flight.value && flight.value.departureTime) {
        return new Date(flight.value.departureTime * 1000);
      }
    });

    const updateFlight = async () => {
      canSubmit.value = false;
      try {
        const reconsDate = new Date();
        const dateSplit = departureDate.value.split("-");
        reconsDate.setFullYear(dateSplit[0]);
        reconsDate.setMonth(dateSplit[1] - 1);
        reconsDate.setDate(dateSplit[2]);
        const timeSplit = departureTime.value.split(":");
        reconsDate.setHours(timeSplit[0]);
        reconsDate.setMinutes(timeSplit[1]);
        const unixTime = Math.floor(reconsDate.getTime() / 1000);
        console.log(unixTime);
        const result = await fetch(`${BASE_URL}/flight/${props.flightId}`, {
          method: "put",
          headers: {
            "content-type": "application/json",
          },
          body: JSON.stringify({
            originAirport: originAirport.value,
            originCity: originCity.value,
            destinationAirport: destinationAirport.value,
            destinationCity: destinationCity.value,
            departureTimestamp: unixTime,
          }),
        });
      } catch (err) {
        console.log(err);
      } finally {
        canSubmit.value = true;
      }
    };

    const fetchFlight = async (flightId) => {
      console.log("fetch", flightId);
      loading.value = true;
      try {
        const result = await fetch(`${BASE_URL}/flight/${flightId}`, {
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
        flight.value = await result.json();
        originAirport.value = flight.value.route.origin.iataId;
        originCity.value = flight.value.route.origin.city;
        destinationAirport.value = flight.value.route.destination.iataId;
        destinationCity.value = flight.value.route.destination.city;
        console.log(flight.value.departureTime);
        const departureDateObject = new Date(flight.value.departureTime * 1000);
        console.log(departureDateObject.toString());
        departureDate.value = `${departureDateObject.getFullYear()}-${(
          "0" +
          (departureDateObject.getMonth() + 1)
        ).slice(-2)}-${("0" + departureDateObject.getDate()).slice(-2)}`;
        departureTime.value = `${departureDateObject.getHours()}:${departureDateObject.getMinutes()}`;
      } catch (err) {
        error.value = err;
      } finally {
        loading.value = false;
      }
    };

    onMounted(async () => {
      await fetchFlight(props.flightId);
    });

    return {
      flight,
      loading,
      error,
      showDetails,
      departureDateTime,
      originAirport,
      originCity,
      destinationAirport,
      destinationCity,
      departureDate,
      departureTime,
      arrivalDate,
      arrivalTime,
      canSubmit,
      updateFlight,
    };
  },
});
</script>

<style scoped></style>
