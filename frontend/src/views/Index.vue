<template>
  <ul>
    <li><router-link to="/flights">Flights</router-link></li>
    <li>Seats</li>
    <li>Tickets</li>
    <li>Passengers</li>
    <li><router-link to="/airports">Airports</router-link></li>
    <li>Employees</li>
    <li>Over-ride trip cancellation</li>
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
