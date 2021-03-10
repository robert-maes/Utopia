<template>
  <ul>
    <li><router-link to="/flight">Flights</router-link></li>
    <li><router-link to="/seat">Seats</router-link></li>
    <li><router-link to="/ticket">Tickets</router-link></li>
    <li><router-link to="/traveler">Travelers</router-link></li>
    <li><router-link to="/airport">Airports</router-link></li>
    <li><router-link to="/employee">Employees</router-link></li>
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
