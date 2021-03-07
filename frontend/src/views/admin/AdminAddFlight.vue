<template>
  <u>Add a New Flight</u>
  <div v-if="loading">Loading...</div>
  <div v-else>
    <div v-if="error">Oops! An error ocurred. Please try again later.</div>
    <div v-else>
      <form>
        <table>
          <tr>
            <td><label for="originAirport">Origin Airport:</label></td>
            <td>
              <select id="originAirport">
                <option
                  v-for="airport in airports"
                  :key="airport.iataId"
                  :value="airport.iataId"
                >
                  {{ airport.iataId }}
                </option>
              </select>
            </td>
            <td>Add New Airport</td>
          </tr>
          <tr>
            <td>
              <label for="destinationAirport">Destination Airport:</label>
            </td>
            <td>
              <select id="destinationAirport">
                <option
                  v-for="airport in airports"
                  :key="airport.iataId"
                  :value="airport.iataId"
                >
                  {{ airport.iataId }}
                </option>
              </select>
            </td>
            <td></td>
          </tr>
          <tr>
            <td><label for="airplane">Airplane:</label></td>
            <td>
              <select id="airplane">
                <option
                  v-for="airplane in airplanes"
                  :key="airplane.id"
                  :value="airplane.id"
                >
                  Airplane {{ airplane.id }} ({{ airplane.type.maxCapacity }}
                  passengers)
                </option>
              </select>
            </td>
            <td>Add New Airplane</td>
          </tr>
          <tr>
            <td><label for="departureDatetime">Departure:</label></td>
            <td><input type="datetime-local" id="departureDatetime" /></td>
            <td></td>
          </tr>
          <tr>
            <td><label for="reservedSeats">Reserved Seats:</label></td>
            <td><input type="text" id="reservedSeats" /></td>
            <td></td>
          </tr>
          <tr>
            <td><label for="seatPrice">Seat Price:</label></td>
            <td><input type="text" id="seatPrice" /></td>
            <td></td>
          </tr>
          <tr>
            <td></td>
            <td><input type="Submit" value="Create Flight" /></td>
            <td></td>
          </tr>
        </table>
      </form>
    </div>
  </div>
  <router-link to="/admn/main/flights">Quit to previous</router-link>
</template>

<script>
import { defineComponent, defineProps, onMounted, reactive, ref } from "vue";
import { BASE_URL } from "../../constants";

export default defineComponent({
  setup() {
    const airports = ref(null);
    const airplanes = ref(null);
    const loading = ref(true);
    const error = ref(null);

    const fetchAirplanes = async () => {
      try {
        const result = await fetch(`${BASE_URL}/airplane`, {
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
        airplanes.value = await result.json();
      } catch (err) {
        error.value = err;
      }
    };

    const fetchAirports = async () => {
      try {
        const result = await fetch(`${BASE_URL}/airport`, {
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
        airports.value = await result.json();
      } catch (err) {
        error.value = err;
      }
    };

    onMounted(async () => {
      loading.value = true;
      await fetchAirplanes();
      await fetchAirports();
      loading.value = false;
    });

    return {
      airplanes,
      airports,
      loading,
      error,
    };
  },
});
</script>

<style scoped></style>
