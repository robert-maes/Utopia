<template>
  <div>
    <h3 class="mt-1">Create a new Airport</h3>
    <div v-if="errors" class="alert alert-danger">
      <p v-if="errors.message">{{ errors.message }}</p>
      <ul v-else>
        <li v-for="error in errors" :key="error.fieldName">
          {{ error.message }}
        </li>
      </ul>
    </div>
    <form v-on:submit.prevent="createAirport">
      <table>
        <tr>
          <td><label for="iataId" class="form-label">IATA ID:</label></td>
          <td>
            <input
              type="text"
              required
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
              required
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
              value="Submit"
              :disabled="loading"
              class="btn btn-primary"
            />
          </td>
        </tr>
      </table>
    </form>
    <router-link to="/airport" tag="button" class="btn btn-secondary mt-1"
      >Cancel</router-link
    >
  </div>
</template>

<script>
import { defineComponent, ref } from "vue";
import { useRouter } from "vue-router";
import { post } from "../../utils";

export default defineComponent({
  components: {},
  setup() {
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();
    const iataId = ref("");
    const city = ref("");

    const createAirport = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await post("airport", {
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
    return {
      loading,
      errors,
      iataId,
      city,
      createAirport,
    };
  },
});
</script>

<style scoped></style>
