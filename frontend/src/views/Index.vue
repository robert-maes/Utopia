<template>
  <h1 class="mt-5">Admin Panel</h1>
  <ul class="list-group">
    <li class="list-group-item">
      <router-link to="/flight" tag="button" class="btn btn-primary noColor"
        >🛫&nbsp;&nbsp;Flights</router-link
      >
    </li>
    <li class="list-group-item">
      <router-link to="/seat" tag="button" class="btn btn-primary noColor"
        >💺&nbsp;&nbsp;Seats</router-link
      >
    </li>
    <li class="list-group-item">
      <router-link to="/ticket" tag="button" class="btn btn-primary noColor"
        >🎫&nbsp;&nbsp;Tickets</router-link
      >
    </li>
    <li class="list-group-item">
      <router-link to="/traveler" tag="button" class="btn btn-primary noColor"
        >🧳&nbsp;&nbsp;Travelers</router-link
      >
    </li>
    <li class="list-group-item">
      <router-link to="/airport" tag="button" class="btn btn-primary noColor"
        >🏙️&nbsp;&nbsp;Airports</router-link
      >
    </li>
    <li class="list-group-item">
      <router-link to="/employee" tag="button" class="btn btn-primary noColor"
        >👔&nbsp;&nbsp;Employees</router-link
      >
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

<style>
.noColor {
  color: transparent;
  text-shadow: 0 0 0 white;
}
</style>
