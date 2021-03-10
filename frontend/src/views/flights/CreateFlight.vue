<template>
  <h3>Create a new Flight</h3>
  <div v-if="loading">Loading ...</div>
  <div v-else>
    <div v-if="loadingError">
      Oops! An error has occured. Please try again later.
    </div>
    <div v-else>
      <div style="color: red" v-if="errors">
        <p v-for="field in errors" :key="field">{{ field[0] }}</p>
      </div>
      <form v-on:submit.prevent="onSubmit">
        <table>
          <tr>
            <td><label for="originAirport">From:</label></td>
            <td>
              <select id="originAirport" v-model="originAirport">
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
            <td><label for="destinationAirport">To:</label></td>
            <td>
              <select id="destinationAirport" v-model="destinationAirport">
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
            <td><label for="airplane">Airplane:</label></td>
            <td>
              <select id="airplane" v-model="airplane">
                <option
                  v-for="airplane in airplanes"
                  :key="airplane.id"
                  :value="airplane.id"
                >
                  {{ airplane.id }}
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td><label for="departureDate">Departure Date:</label></td>
            <td>
              <input type="date" id="departureDate" v-model="departureDate" />
            </td>
          </tr>
          <tr>
            <td><label for="departureTime">Departure Time:</label></td>
            <td>
              <input
                type="time"
                id="departureTime"
                :value="departureTime"
                @input="departureTime = $event.target.value"
              />
            </td>
          </tr>
          <tr>
            <td><label for="arrivalDate">Arrival Date:</label></td>
            <td>
              <input type="date" id="arrivalDate" v-model="arrivalDate" />
            </td>
          </tr>
          <tr>
            <td><label for="arrivalTime">Arrival Time:</label></td>
            <td>
              <input
                type="time"
                id="arrivalTime"
                :value="arrivalTime"
                @input="arrivalTime = $event.target.value"
              />
            </td>
          </tr>
          <tr>
            <td><label for="reservedSeats">Reserved Seats:</label></td>
            <td>
              <input type="number" id="reservedSeats" v-model="reservedSeats" />
            </td>
          </tr>
          <tr>
            <td><label for="seatPrice">Seat Price:</label></td>
            <td>
              <input
                type="number"
                step="0.01"
                id="seatPrice"
                v-model="seatPrice"
              />
            </td>
          </tr>
          <tr>
            <td></td>
            <td><input type="submit" value="Submit" :disabled="loading" /></td>
          </tr>
        </table>
      </form>
    </div>
  </div>
  <router-link to="/flights">Quit to Previous</router-link>
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import { get, post, dateTimeToUnixTimestamp } from "../../utils";
import { useRouter } from "vue-router";

export default defineComponent({
  components: {},
  setup() {
    const iataId = ref(null);
    const city = ref(null);
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();
    const originAirport = ref(null);
    const destinationAirport = ref(null);
    const departureDate = ref(null);
    const departureTime = ref(null);
    const arrivalDate = ref(null);
    const arrivalTime = ref(null);
    const reservedSeats = ref(0);
    const seatPrice = ref(0.0);
    const airports = ref(null);
    const loadingError = ref(false);
    const airplane = ref(null);
    const airplanes = ref(null);

    const fetchAirports = async () => {
      try {
        airports.value = await get("airport");
      } catch (e) {
        console.log(e);
        loadingError.value = true;
      }
    };

    const fetchAirplanes = async () => {
      try {
        airplanes.value = await get("airplane");
      } catch (e) {
        console.log(e);
        loadingError.value = true;
      }
    };

    const onSubmit = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await post("flight", {
          originAirport: originAirport.value,
          destinationAirport: destinationAirport.value,
          airplane: airplane.value,
          departureTime: dateTimeToUnixTimestamp(
            departureDate.value,
            departureTime.value
          ),
          arrivalTime: dateTimeToUnixTimestamp(
            arrivalDate.value,
            arrivalTime.value
          ),
          reservedSeats: reservedSeats.value,
          seatPrice: seatPrice.value,
        });
        router.push({
          path: "/flights",
        });
      } catch (e) {
        console.log(e);
        errors.value = JSON.parse(e.message);
        console.error(e.message);
      } finally {
        loading.value = false;
      }
    };

    onMounted(async () => {
      loading.value = true;
      await fetchAirports();
      await fetchAirplanes();
      loading.value = false;
    });

    return {
      iataId,
      city,
      loading,
      errors,
      onSubmit,
      departureDate,
      departureTime,
      arrivalDate,
      arrivalTime,
      reservedSeats,
      seatPrice,
      loadingError,
      airports,
      originAirport,
      destinationAirport,
      airplanes,
      airplane,
    };
  },
});
</script>

<style scoped></style>
