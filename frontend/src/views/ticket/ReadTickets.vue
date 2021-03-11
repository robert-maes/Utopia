<template>
  <div v-if="loadingInitial">Loading...</div>
  <div v-else>
    <div v-if="errorInitial">Oops! Unable to load tickets.</div>
    <div v-else>
      <h3 class="mt-1">Tickets 🎫</h3>
      <router-link to="/ticket/add" tag="button" class="btn btn-primary"
        >Create a new Ticket</router-link
      >
      <table class="table table-striped table-bordered table-sm mt-3">
        <thead>
          <tr class="table-primary">
            <th scope="col">ID</th>
            <th scope="col">Confirmation Code</th>
            <th scope="col">Active</th>
            <th scope="col">Traveler ID</th>
            <th scope="col">Traveler Name</th>
            <th scope="col">Flight</th>
            <th scope="col">Seat ID</th>
            <th scope="col">Seat Class</th>
            <th scope="col"></th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ticket in tickets" :key="ticket.id">
            <th scope="row">{{ ticket.id }}</th>
            <td>{{ ticket.confirmationCode }}</td>
            <td>{{ ticket.active ? "X" : "" }}</td>
            <td>{{ ticket.traveler.id }}</td>
            <td>
              {{ ticket.traveler.givenName + " " + ticket.traveler.familyName }}
            </td>
            <td>
              [{{ ticket.seat.flight.id }}]
              {{ ticket.seat.flight.originAirport.iataId }} &rarr;
              {{ ticket.seat.flight.destinationAirport.iataId }}
            </td>
            <td>{{ ticket.seat.id }}</td>
            <td>{{ ticket.seat.seatClass }}</td>
            <td>
              <router-link
                :to="`/ticket/${ticket.id}/update`"
                tag="button"
                class="btn btn-warning btn-sm"
                >Update</router-link
              >
            </td>
            <td>
              <a
                href="#"
                @click="destroyTicket(ticket.id)"
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
    const tickets = ref(null);

    const getTickets = async () => {
      try {
        tickets.value = await get("ticket");
      } catch (e) {
        errorInitial.value = e;
        console.error(e);
      }
    };

    const destroyTicket = async (ticketId) => {
      try {
        if (confirm("Are you sure you want to delete this ticket?")) {
          await destroy(`ticket/${ticketId}`);
          tickets.value = tickets.value.filter(
            (ticket) => ticket.id !== ticketId
          );
        }
      } catch (e) {
        console.error(e);
      }
    };

    onMounted(async () => {
      loadingInitial.value = true;
      await getTickets();
      loadingInitial.value = false;
    });

    return {
      loadingInitial,
      errorInitial,
      tickets,
      destroyTicket,
    };
  },
});
</script>

<style scoped></style>
