<template>
  <ul>
    <li><router-link to="/admn/main/flights">Manage Flights</router-link></li>
    <li>Manage Seats</li>
    <li>Manage Tickets and Passengers</li>
    <li>Manage Airports</li>
    <li>Manage Travelers</li>
    <li>Manage Employees</li>
    <li>Over-ride Trip Cancellation for a ticket</li>
    <li><router-link to="/">Quit to previous</router-link></li>
  </ul>
</template>

<script>
import { defineComponent, defineProps, reactive } from "vue";

export default defineComponent({
  setup() {
    return {};
  },
});
</script>

<style scoped></style>
