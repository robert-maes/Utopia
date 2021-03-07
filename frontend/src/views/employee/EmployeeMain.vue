<template>
  <ul>
    <li>
      <router-link to="/emp/main/flights">Enter Flights You Manage</router-link>
    </li>
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
