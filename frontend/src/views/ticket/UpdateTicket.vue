<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial" class="alert alert-warning">
      Oops! Unable to load ticket with ID {{ ticketId }}
    </div>
    <div v-else>
      <h3 class="mt-1">Update Ticket</h3>
      <div v-if="errors" class="alert alert-danger">
        <p v-if="errors.message">{{ errors.message }}</p>
        <ul v-else>
          <li v-for="error in errors" :key="error.fieldName">
            {{ error.message }}
          </li>
        </ul>
      </div>
      <form v-on:submit.prevent="updateTicket">
        <table>
          <tr>
            <td><label for="isActive" class="form-label">Active:</label></td>
            <td>
              <div class="form-check form-switch">
                <input
                  class="form-check-input"
                  type="checkbox"
                  id="isActive"
                  v-model="isActive"
                />
              </div>
            </td>
          </tr>
          <tr>
            <td></td>
            <td class="text-end">
              <input
                type="submit"
                value="Update"
                :disabled="loading"
                class="btn btn-primary"
              />
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
  <router-link to="/ticket" tag="button" class="btn btn-secondary mt-1"
    >Cancel</router-link
  >
</template>

<script>
import { defineComponent, onMounted, ref } from "vue";
import { get, put } from "../../utils";
import { useRouter } from "vue-router";

export default defineComponent({
  components: {},
  props: {
    ticketId: String,
  },
  setup(props) {
    const loadingInitial = ref(true);
    const errorInitial = ref(null);
    const ticket = ref(null);
    const loading = ref(false);
    const errors = ref(null);
    const router = useRouter();
    const isActive = ref(null);

    const updateTicket = async () => {
      loading.value = true;
      errors.value = null;
      try {
        await put(`ticket/${props.ticketId}`, {
          isActive: isActive.value ? "true" : "false",
        });
        await router.push({
          path: "/ticket",
        });
      } catch (e) {
        errors.value = JSON.parse(e.message);
        console.error(e);
      } finally {
        loading.value = false;
      }
    };

    const getTicket = async () => {
      try {
        ticket.value = await get(`ticket/${props.ticketId}`);
        isActive.value = ticket.value.active;
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getTicket();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      ticket,
      loading,
      errors,
      isActive,
      updateTicket,
    };
  },
});
</script>

<style scoped></style>
