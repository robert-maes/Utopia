<template>
  <h3>Update Airport</h3>
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
            <td><label for="iataId">IATA ID:</label></td>
            <td>
              <input type="text" id="iataId" v-model="updatedAirportIataId" />
            </td>
          </tr>
          <tr>
            <td><label for="city">City:</label></td>
            <td>
              <input type="text" id="city" v-model="updatedAirportCity" />
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
  <router-link to="/airports">Quit to Previous</router-link>
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import { put, get } from "../../utils";
import { useRouter } from "vue-router";

export default defineComponent({
  components: {},
  props: {
    airportId: Number,
  },
  setup(props) {
    const iataId = ref(null);
    const city = ref(null);
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();
    const loadingError = ref(null);
    const airport = ref(null);
    const updatedAirportIataId = ref(null);
    const updatedAirportCity = ref(null);

    const fetchAirport = async () => {
      try {
        airport.value = await get(`airport/${props.airportId}`);
        updatedAirportIataId.value = airport.value.iataId;
        updatedAirportCity.value = airport.value.city;
      } catch (e) {
        loadingError.value = true;
      }
    };

    const onSubmit = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await put(`airport/${airport.value.iataId}`, {
          iataId: updatedAirportIataId.value,
          city: updatedAirportCity.value,
        });
        router.push({
          path: "/airports",
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
      await fetchAirport();
      loading.value = false;
    });

    return {
      iataId,
      city,
      loading,
      errors,
      onSubmit,
      loadingError,
      airport,
      updatedAirportIataId,
      updatedAirportCity,
    };
  },
});
</script>

<style scoped></style>
