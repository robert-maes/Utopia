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
              Arrival Date: {{ arrivalDateTime.toLocaleDateString("en-US") }} |
              Arrival Time: {{ arrivalDateTime.toLocaleTimeString("en-US") }}
            </p>
            <p>
              Available Seats by Class:
              <ol>
                <li v-for="seatClass in seatsByClass" :key="seatClass[0]">
                  {{seatClass[0]}} &rarr; {{seatClass[1]}}
                </li>
              </ol>
            </p>
          </div>
        </li>
        <li>
          <router-link :to="`/emp/main/flight/${flightId}/update`"
            >Upate the details of the Flight</router-link
          >
        </li>
        <li><router-link :to="`/emp/main/flight/${flightId}/seats`"
            >Add Seats to the Flight</router-link
          ></li>
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
        return new Date(flight.value.departureTime * 1000);
      }
    });

    const arrivalDateTime = computed(() => {
      if (flight.value && flight.value.arrivalTime) {
        return new Date(flight.value.arrivalTime * 1000);
      }
    });

    const seatsByClass = computed(() => {
      if (flight.value && flight.value.seats) {
        const seats = flight.value.seats;
        console.log(seats);
        const classMap = new Map();
        seats.forEach(seat => {
          console.log(seat);
          const count = classMap.get(seat.type.classification);
          if (count) {
            classMap.set(seat.type.classification, count + 1);
          } else {
            classMap.set(seat.type.classification, 1);
          }
        })
        return classMap;
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
      arrivalDateTime,
      seatsByClass
    };
  },
});
</script>

<style scoped></style>
