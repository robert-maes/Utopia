<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial">Oops! Unable to load seats.</div>
    <div v-else>
      <h3 class="mt-1">Seats 💺</h3>
      <router-link to="/seat/add" tag="button" class="btn btn-primary"
        >Create a new Seat</router-link
      >
      <table class="table table-striped table-bordered table-sm mt-3">
        <thead>
          <tr class="table-primary">
            <th scope="col">ID</th>
            <th scope="col">Flight ID</th>
            <th scope="col">Ticket ID</th>
            <th scope="col">Seat Class</th>
            <th scope="col"></th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="seat in seats" :key="seat.id">
            <th scope="row">{{ seat.id }}</th>
            <td>{{ seat.flight.id }}</td>
            <td>{{ seat.ticket ? seat.ticket.id : "" }}</td>
            <td>{{ seat.seatClass }}</td>
            <td>
              <router-link
                :to="`/seat/${seat.id}/update`"
                tag="button"
                class="btn btn-warning btn-sm"
                >Update</router-link
              >
            </td>
            <td>
              <a
                href="#"
                @click="destroySeat(seat.id)"
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
    const seats = ref(null);

    const getSeats = async () => {
      try {
        seats.value = await get("seat");
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    const destroySeat = async (seatId) => {
      try {
        if (
          confirm(
            "Are you sure you want to delete this seat? THIS WILL DELETE ANY ASSOCIATED TICKETS."
          )
        ) {
          await destroy(`seat/${seatId}`);
          seats.value = seats.value.filter((seat) => seat.id !== seatId);
        }
      } catch (e) {
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getSeats();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      seats,
      destroySeat,
    };
  },
});
</script>

<style scoped></style>
