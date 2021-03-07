<template>
  <u>Manage Flight</u>
  <div v-if="loading">Loading flight...</div>
  <div v-else>
    <div v-if="error">
      Oops! Unable to load your flight. Please make sure it is valid and still
      exists or try again later.
    </div>
    <div v-else>
      <ul>
        <li>
          <button @click="showDetails = !showDetails">
            {{ showDetails ? "Hide Details" : "Show Details" }}
          </button>
          <div v-if="showDetails">
            <p>
              You have chosen to view the Flight with Flight Id:
              {{ flight.id }} and Departure Airport:
              {{ flight.route.origin.iataId }} and Arrival Airport:
              {{ flight.route.destination.iataId }}
            </p>
            <p>
              Departure Airport: {{ flight.route.origin.iataId }} | Arrival
              Airport:
              {{ flight.route.destination.iataId }}
              <br />
              Departure Date:
              {{ departureDateTime.toLocaleDateString("en-US") }}
              | Departure Time:
              {{ departureDateTime.toLocaleTimeString("en-US") }}
              <br />
              Arrival Date: ??? | Arrival Time: ???
            </p>
            <p>
              Available Seats by Class:
              <br />
              1) First &rarr; X
              <br />
              2) Business &rarr; Y
              <br />
              3) Economy &rarr; Z
            </p>
          </div>
        </li>
        <li>
          <router-link :to="`/emp/main/flight/${flightId}/update`"
            >Upate the details of the Flight</router-link
          >
        </li>
        <li>Add seats to the Flight</li>
      </ul>
    </div>
  </div>
  <router-link to="/emp/main/flights">Quit to previous</router-link>
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

    //TODO: fix time
    const departureDateTime = computed(() => {
      if (flight.value && flight.value.departureTime) {
        const departureTime = flight.value.departureTime;
        return new Date(
          departureTime[0],
          departureTime[1],
          departureTime[2],
          departureTime[3],
          departureTime[4],
          departureTime[5],
          0
        );
      }
    });

    const fetchFlight = async (flightId) => {
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
    };
  },
});
</script>

<style scoped></style>
