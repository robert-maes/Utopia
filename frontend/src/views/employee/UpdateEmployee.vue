<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial" class="alert alert-warning">
      Oops! Unable to load employee with ID {{ employeeId }}
    </div>
    <div v-else>
      <h3 class="mt-1">Update Employee</h3>
      <div v-if="errors" class="alert alert-danger">
        <p v-if="errors.message">{{ errors.message }}</p>
        <ul v-else>
          <li v-for="error in errors" :key="error.fieldName">
            {{ error.message }}
          </li>
        </ul>
      </div>
      <form v-on:submit.prevent="updateEmployee">
        <table>
          <tr>
            <td>
              <label for="givenName" class="form-label">First Name:</label>
            </td>
            <td>
              <input
                type="text"
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
                maxlength="255"
                id="familyName"
                v-model="familyName"
                class="form-control"
              />
            </td>
          </tr>
          <tr>
            <td><label for="email" class="form-label">Email:</label></td>
            <td>
              <input
                type="email"
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
  <router-link to="/employee" tag="button" class="btn btn-secondary mt-1"
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
    employeeId: String,
  },
  setup(props) {
    const loadingInitial = ref(true);
    const errorInitial = ref(null);
    const employee = ref(null);
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();
    const givenName = ref("");
    const familyName = ref("");
    const email = ref("");
    const phoneNumber = ref("");

    const updateEmployee = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await put(`employee/${props.employeeId}`, {
          givenName: givenName.value,
          familyName: familyName.value,
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

    const getEmployee = async () => {
      try {
        employee.value = await get(`employee/${props.employeeId}`);
        givenName.value = employee.value.givenName;
        familyName.value = employee.value.familyName;
        email.value = employee.value.email;
        phoneNumber.value = employee.value.phoneNumber;
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getEmployee();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      employee,
      loading,
      errors,
      givenName,
      familyName,
      email,
      phoneNumber,
      updateEmployee,
    };
  },
});
</script>

<style scoped></style>
