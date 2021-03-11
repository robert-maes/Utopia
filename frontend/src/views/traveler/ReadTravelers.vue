<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial">Oops! Unable to load travelers.</div>
    <div v-else>
      <h3 class="mt-1">Travelers 🧳</h3>
      <router-link to="/traveler/add" tag="button" class="btn btn-primary"
        >Create a new Traveler</router-link
      >
      <table class="table table-striped table-bordered table-sm mt-3">
        <thead>
          <tr class="table-primary">
            <th scope="col">ID</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Birthday</th>
            <th scope="col">Gender</th>
            <th scope="col">Address</th>
            <th scope="col"></th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="traveler in travelers" :key="traveler.id">
            <th scope="row">{{ traveler.id }}</th>
            <td>{{ traveler.givenName }}</td>
            <td>{{ traveler.familyName }}</td>
            <td>{{ traveler.dateOfBirth }}</td>
            <td>{{ traveler.gender }}</td>
            <td>{{ traveler.address }}</td>
            <td>
              <router-link
                :to="`/traveler/${traveler.id}/update`"
                tag="button"
                class="btn btn-warning btn-sm"
                >Update</router-link
              >
            </td>
            <td>
              <a
                href="#"
                @click="destroyTraveler(traveler.id)"
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
    const travelers = ref(null);

    const getTravelers = async () => {
      try {
        travelers.value = await get("traveler");
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    const destroyTraveler = async (travelerId) => {
      try {
        if (confirm("Are you sure you want to delete this traveler?")) {
          await destroy(`traveler/${travelerId}`);
          travelers.value = travelers.value.filter(
            (traveler) => traveler.id !== travelerId
          );
        }
      } catch (e) {
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getTravelers();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      travelers,
      destroyTraveler,
    };
  },
});
</script>

<style scoped></style>
