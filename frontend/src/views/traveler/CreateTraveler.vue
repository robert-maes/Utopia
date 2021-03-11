<template>
  <div>
    <h3 class="mt-1">Create a new Traveler</h3>
    <div v-if="errors" class="alert alert-danger">
      <p v-if="errors.message">{{ errors.message }}</p>
      <ul v-else>
        <li v-for="error in errors" :key="error.fieldName">
          {{ error.message }}
        </li>
      </ul>
    </div>
    <form v-on:submit.prevent="createTraveler">
      <table>
        <tr>
          <td><label for="givenName" class="form-label">First Name:</label></td>
          <td>
            <input
              type="text"
              required
              maxlength="255"
              id="givenName"
              v-model="givenName"
              class="form-control"
            />
          </td>
        </tr>
        <tr>
          <td><label for="familyName" class="form-label">Last Name:</label></td>
          <td>
            <input
              type="text"
              required
              maxlength="255"
              id="familyName"
              v-model="familyName"
              class="form-control"
            />
          </td>
        </tr>
        <tr>
          <td><label for="dateOfBirth" class="form-label">Birthday:</label></td>
          <td>
            <input
              type="date"
              required
              id="dateOfBirth"
              v-model="dateOfBirth"
              class="form-control"
            />
          </td>
        </tr>
        <tr>
          <td><label for="gender" class="form-label">Gender:</label></td>
          <td>
            <input
              type="text"
              required
              maxlength="255"
              id="gender"
              v-model="gender"
              class="form-control"
            />
          </td>
        </tr>
        <tr>
          <td><label for="address" class="form-label">Address:</label></td>
          <td>
            <input
              type="text"
              required
              maxlength="255"
              id="address"
              v-model="address"
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
    <router-link to="/traveler" tag="button" class="btn btn-secondary mt-1"
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
    const givenName = ref("");
    const familyName = ref("");
    const dateOfBirth = ref("");
    const gender = ref("");
    const address = ref("");

    const createTraveler = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await post("traveler", {
          givenName: givenName.value,
          familyName: familyName.value,
          dateOfBirth: dateOfBirth.value,
          gender: gender.value,
          address: address.value,
        });
        await router.push({
          path: "/traveler",
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
      givenName,
      familyName,
      dateOfBirth,
      gender,
      address,
      createTraveler,
    };
  },
});
</script>

<style scoped></style>
