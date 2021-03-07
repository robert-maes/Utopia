<template>
  <ul>
    <li>
      Enter your Membership number:<br /><input type="text" /><button>
        Submit
      </button>
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
