<template>
  <ul>
    <li><router-link to="/flights">Flights</router-link></li>
    <li><router-link to="/seats">Seats</router-link></li>
    <li><router-link to="/tickets">Tickets</router-link></li>
    <li><router-link to="/passengers">Passengers</router-link></li>
    <li><router-link to="/airports">Airports</router-link></li>
    <li><router-link to="/employees">Employees</router-link></li>
    <li>
      <router-link to="/">Over-ride Trip Cancellation</router-link>
    </li>
  </ul>
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
  components: {},
  setup() {
    return {};
  },
});
</script>

<style scoped></style>
