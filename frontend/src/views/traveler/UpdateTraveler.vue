<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial" class="alert alert-warning">
      Oops! Unable to load traveler with ID {{ travelerId }}
    </div>
    <div v-else>
      <h3 class="mt-1">Update Traveler</h3>
      <div v-if="errors" class="alert alert-danger">
        <p v-if="errors.message">{{ errors.message }}</p>
        <ul v-else>
          <li v-for="error in errors" :key="error.fieldName">
            {{ error.message }}
          </li>
        </ul>
      </div>
      <form v-on:submit.prevent="updateTraveler">
        <table>
          <tr>
            <td>
              <label for="givenName" class="form-label">First Name:</label>
            </td>
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
            <td>
              <label for="familyName" class="form-label">Last Name:</label>
            </td>
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
            <td>
              <label for="dateOfBirth" class="form-label">Birthday:</label>
            </td>
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
  <router-link to="/traveler" tag="button" class="btn btn-secondary mt-1"
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
    travelerId: String,
  },
  setup(props) {
    const loadingInitial = ref(true);
    const errorInitial = ref(null);
    const traveler = ref(null);
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();
    const givenName = ref("");
    const familyName = ref("");
    const dateOfBirth = ref("");
    const gender = ref("");
    const address = ref("");

    const updateTraveler = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await put(`traveler/${props.travelerId}`, {
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

    const getTraveler = async () => {
      try {
        traveler.value = await get(`traveler/${props.travelerId}`);
        givenName.value = traveler.value.givenName;
        familyName.value = traveler.value.familyName;
        dateOfBirth.value = traveler.value.dateOfBirth;
        gender.value = traveler.value.gender;
        address.value = traveler.value.address;
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getTraveler();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      traveler,
      loading,
      errors,
      givenName,
      familyName,
      dateOfBirth,
      gender,
      address,
      updateTraveler,
    };
  },
});
</script>

<style scoped></style>
