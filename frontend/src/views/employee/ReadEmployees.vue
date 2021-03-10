<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial">Oops! Unable to load employees.</div>
    <div v-else>
      <h3>Employees</h3>
      <router-link to="/employee/add">Create a new Employee</router-link>
      <table>
        <tr>
          <th>ID</th>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Username</th>
          <th>Email</th>
          <th>Phone Number</th>
          <th></th>
          <th></th>
        </tr>
        <tr v-for="employee in employees" :key="employee.id">
          <td>{{ employee.id }}</td>
          <td>{{ employee.givenName }}</td>
          <td>{{ employee.familyName }}</td>
          <td>{{ employee.username }}</td>
          <td>{{ employee.email }}</td>
          <td>{{ employee.phoneNumber }}</td>
          <td>
            <router-link :to="`/employee/${employee.id}/update`"
              >Update</router-link
            >
          </td>
          <td>
            <a href="#" @click="destroyEmployee(employee.id)">Delete</a>
          </td>
        </tr>
      </table>
    </div>
  </div>
  <router-link to="/">Quit to Previous</router-link>
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import { get, destroy } from "../../utils";

export default defineComponent({
  components: {},
  setup() {
    const loadingInitial = ref(true);
    const errorInitial = ref(null);
    const employees = ref(null);

    const getEmployees = async () => {
      try {
        employees.value = await get("employee");
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    const destroyEmployee = async (employeeId) => {
      try {
        await destroy(`employee/${employeeId}`);
        employees.value = employees.value.filter(
          (employee) => employee.id !== employeeId
        );
      } catch (e) {
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getEmployees();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      employees,
      destroyEmployee,
    };
  },
});
</script>

<style scoped></style>
