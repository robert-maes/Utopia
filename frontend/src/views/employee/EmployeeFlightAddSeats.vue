<template>
  <u>Add Seats</u>
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
            <td><label for="seatType">Seat Type</label></td>
            <td>
              <select
                id="seatType"
                @change="updateSeatCount"
                v-model="selectedSeatType"
              >
                <option
                  v-for="seatType in seatTypes"
                  :key="seatType.id"
                  :value="seatType.classification"
                >
                  {{ seatType.classification }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td><label for="seatCount">Number of Seats</label></td>
            <td>
              <input id="seatCount" v-model="numOfSeats" type="number" />
            </td>
          </tr>
          <tr>
            <td></td>
            <td><input type="submit" value="Add Seats" @click="addSeats" /></td>
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
    const seatTypes = ref(null);
    const flight = ref(null);
    const loading = ref(true);
    const error = ref(null);
    const showDetails = ref(false);
    const selectedSeatType = ref(null);
    const numOfSeats = ref(null);

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

    const updateSeatCount = () => {
      numOfSeats.value = existingSeatCount.value;
    };

    const existingSeatCount = computed(() => {
      return seatsByClass.value.get(selectedSeatType.value);
    });

    const seatsByClass = computed(() => {
      if (flight.value && flight.value.seats) {
        const seats = flight.value.seats;
        console.log(seats);
        const classMap = new Map();
        seats.forEach((seat) => {
          console.log(seat);
          const count = classMap.get(seat.type.classification);
          if (count) {
            classMap.set(seat.type.classification, count + 1);
          } else {
            classMap.set(seat.type.classification, 1);
          }
        });
        return classMap;
      }
    });

    const addSeats = async () => {
      const seatType = selectedSeatType.value;
      const seatNum = numOfSeats.value;
      const flightId = props.flightId;
      console.log(seatType);
      console.log(seatNum);
      try {
        const result = await fetch(`${BASE_URL}/flight/${flightId}/seats`, {
          method: "post",
          headers: {
            "content-type": "application/json",
          },
          body: JSON.stringify({ seatType, seatNum }),
        });
      } catch (err) {
        console.log(err);
      }
    };

    const fetchSeatTypes = async () => {
      try {
        const result = await fetch(`${BASE_URL}/seat_type`, {
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
        seatTypes.value = await result.json();
      } catch (err) {
        error.value = err;
      }
    };

    const fetchFlight = async (flightId) => {
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
      }
    };

    onMounted(async () => {
      loading.value = true;
      await fetchFlight(props.flightId);
      await fetchSeatTypes();
      loading.value = false;
    });

    return {
      flight,
      loading,
      error,
      showDetails,
      departureDateTime,
      arrivalDateTime,
      seatsByClass,
      seatTypes,
      selectedSeatType,
      existingSeatCount,
      numOfSeats,
      updateSeatCount,
      addSeats,
    };
  },
});
</script>

<style scoped></style>
