<template>
  <div>
    <h3 class="mt-1">Create a new Employee</h3>
    <div v-if="errors" class="alert alert-danger">
      <p v-if="errors.message">{{ errors.message }}</p>
      <ul v-else>
        <li v-for="error in errors" :key="error.fieldName">
          {{ error.message }}
        </li>
      </ul>
    </div>
    <form v-on:submit.prevent="createEmployee">
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
          <td><label for="username" class="form-label">Username:</label></td>
          <td>
            <input
              type="text"
              required
              pattern="^[a-zA-Z0-9_]*$"
              minlength="8"
              maxlength="255"
              id="username"
              v-model="username"
              class="form-control"
            />
          </td>
        </tr>
        <tr>
          <td><label for="email" class="form-label">Email:</label></td>
          <td>
            <input
              type="email"
              required
              maxlength="255"
              id="email"
              v-model="email"
              class="form-control"
            />
          </td>
        </tr>
        <tr>
          <td>
            <label for="phoneNumber" class="form-label">Phone Number:</label>
          </td>
          <td>
            <input
              type="tel"
              pattern="^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}$"
              required
              id="phoneNumber"
              v-model="phoneNumber"
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
    <router-link to="/employee" tag="button" class="btn btn-secondary mt-1"
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
    const username = ref("");
    const email = ref("");
    const phoneNumber = ref("");

    const createEmployee = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await post("employee", {
          givenName: givenName.value,
          familyName: familyName.value,
          username: username.value,
          email: email.value,
          phoneNumber: phoneNumber.value,
        });
        await router.push({
          path: "/employee",
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
      username,
      email,
      phoneNumber,
      createEmployee,
    };
  },
});
</script>

<style scoped></style>
