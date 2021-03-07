<template>
  <p>I am a(n)...</p>
  <ul>
    <li><router-link to="/emp/main">Employee</router-link></li>
    <li><router-link to="/admn/main">Administrator</router-link></li>
    <li><router-link to="/trav/main">Traveler</router-link></li>
  </ul>
</template>

<script>
import { defineComponent, defineProps, reactive } from "vue";
import HelloWorld from "../components/HelloWorld.vue";

export default defineComponent({
  components: { HelloWorld },
  setup() {
    return {};
  },
});
</script>

<style scoped></style>
