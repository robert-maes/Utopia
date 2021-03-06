<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial">Oops! Unable to load employees.</div>
    <div v-else>
      <h3 class="mt-1">Employees 👔</h3>
      <router-link to="/employee/add" tag="button" class="btn btn-primary"
        >Create a new Employee</router-link
      >
      <table class="table table-striped table-bordered table-sm mt-3">
        <thead>
          <tr class="table-primary">
            <th scope="col">ID</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Username</th>
            <th scope="col">Email</th>
            <th scope="col">Phone Number</th>
            <th scope="col"></th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="employee in employees" :key="employee.id">
            <th scope="row">{{ employee.id }}</th>
            <td>{{ employee.givenName }}</td>
            <td>{{ employee.familyName }}</td>
            <td>{{ employee.username }}</td>
            <td>{{ employee.email }}</td>
            <td>{{ employee.phoneNumber }}</td>
            <td>
              <router-link
                :to="`/employee/${employee.id}/update`"
                tag="button"
                class="btn btn-warning btn-sm"
                >Update</router-link
              >
            </td>
            <td>
              <a
                href="#"
                @click="destroyEmployee(employee.id)"
                class="btn btn-danger btn-sm"
                >Delete</a
              >
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <router-link to="/" tag="button" class="btn btn-secondary">Back</router-link>
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
        if (confirm("Are you sure you want to delete this employee?")) {
          await destroy(`employee/${employeeId}`);
          employees.value = employees.value.filter(
            (employee) => employee.id !== employeeId
          );
        }
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
