<template>
  <div>
    <h3>Create a new Employee</h3>
    <div v-if="errors" style="color: red">
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
          <td><label for="givenName">First Name:</label></td>
          <td>
            <input
              type="text"
              required
              maxlength="255"
              id="givenName"
              v-model="givenName"
            />
          </td>
        </tr>
        <tr>
          <td><label for="familyName">Last Name:</label></td>
          <td>
            <input
              type="text"
              required
              maxlength="255"
              id="familyName"
              v-model="familyName"
            />
          </td>
        </tr>
        <tr>
          <td><label for="username">Username:</label></td>
          <td>
            <input
              type="text"
              required
              pattern="^[a-zA-Z0-9_]*$"
              minlength="8"
              maxlength="255"
              id="username"
              v-model="username"
            />
          </td>
        </tr>
        <tr>
          <td><label for="email">Email:</label></td>
          <td>
            <input
              type="email"
              required
              maxlength="255"
              id="email"
              v-model="email"
            />
          </td>
        </tr>
        <tr>
          <td><label for="phoneNumber">Phone Number:</label></td>
          <td>
            <input
              type="tel"
              pattern="^(\+\d{1,2}\s)?\(?\d{3}\)?[\s.-]\d{3}[\s.-]\d{4}$"
              required
              id="phoneNumber"
              v-model="phoneNumber"
            />
          </td>
        </tr>
        <tr>
          <td></td>
          <td><input type="submit" value="Submit" :disabled="loading" /></td>
        </tr>
      </table>
    </form>
    <router-link to="/employee">Quit to Previous</router-link>
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
        router.push({
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
