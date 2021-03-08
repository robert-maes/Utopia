<template>
  <div>
    <h3>Create a new Airport</h3>
    <div style="color: red" v-if="errors">
      <p v-for="field in errors" :key="field">{{ field[0] }}</p>
    </div>
    <form v-on:submit.prevent="onSubmit">
      <table>
        <tr>
          <td><label for="iataId">IATA ID:</label></td>
          <td><input type="text" id="iataId" v-model="iataId" /></td>
        </tr>
        <tr>
          <td><label for="city">City:</label></td>
          <td><input type="text" id="city" v-model="city" /></td>
        </tr>
        <tr>
          <td></td>
          <td><input type="submit" value="Submit" :disabled="loading" /></td>
        </tr>
      </table>
    </form>
    <router-link to="/airports">Quit to Previous</router-link>
  </div>
</template>

<script>
import { defineComponent, ref } from "vue";
import { post } from "../../utils";
import { useRouter } from "vue-router";

export default defineComponent({
  components: {},
  setup() {
    const iataId = ref(null);
    const city = ref(null);
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();

    const onSubmit = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await post("airport", { iataId: iataId.value, city: city.value });
        router.push({
          path: "/airports",
        });
      } catch (e) {
        console.log(e);
        errors.value = JSON.parse(e.message);
        console.error(e.message);
      } finally {
        loading.value = false;
      }
    };

    return {
      iataId,
      city,
      loading,
      errors,
      onSubmit,
    };
  },
});
</script>

<style scoped></style>
