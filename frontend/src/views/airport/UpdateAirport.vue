<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial" class="alert alert-warning">
      Oops! Unable to load airport with ID {{ airportId }}
    </div>
    <div v-else>
      <h3 class="mt-1">Update Airport</h3>
      <div v-if="errors" class="alert alert-danger">
        <p v-if="errors.message">{{ errors.message }}</p>
        <ul v-else>
          <li v-for="error in errors" :key="error.fieldName">
            {{ error.message }}
          </li>
        </ul>
      </div>
      <form v-on:submit.prevent="updateAirport">
        <table>
          <tr>
            <td><label for="iataId" class="form-label">IATA ID:</label></td>
            <td>
              <input
                type="text"
                min="3"
                maxlength="3"
                id="iataId"
                pattern="[A-Z]{3}"
                v-model="iataId"
                class="form-control"
              />
            </td>
          </tr>
          <tr>
            <td><label for="city" class="form-label">City:</label></td>
            <td>
              <input
                type="text"
                maxlength="255"
                id="city"
                v-model="city"
                class="form-control"
              />
            </td>
          </tr>
          <tr>
            <td></td>
            <td class="text-end">
              <input
                type="submit"
                value="Update"
                :disabled="loading"
                class="btn btn-primary"
              />
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
  <router-link to="/airport" tag="button" class="btn btn-secondary mt-1"
    >Cancel</router-link
  >
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import { get, put } from "../../utils";
import { useRouter } from "vue-router";

export default defineComponent({
  components: {},
  props: {
    airportId: String,
  },
  setup(props) {
    const loadingInitial = ref(true);
    const errorInitial = ref(null);
    const airport = ref(null);
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();
    const iataId = ref("");
    const city = ref("");

    const updateAirport = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await put(`airport/${props.airportId}`, {
          id: iataId.value,
          city: city.value,
        });
        await router.push({
          path: "/airport",
        });
      } catch (e) {
        errors.value = JSON.parse(e.message);
        console.error(e);
      } finally {
        loading.value = false;
      }
    };

    const getAirport = async () => {
      try {
        airport.value = await get(`airport/${props.airportId}`);
        iataId.value = airport.value.iataId;
        city.value = airport.value.city;
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getAirport();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      airport,
      loading,
      errors,
      iataId,
      city,
      updateAirport,
    };
  },
});
</script>

<style scoped></style>
