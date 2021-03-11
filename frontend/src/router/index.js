import { createRouter, createWebHistory } from "vue-router";
import Index from "../views/Index.vue";
import NotFound from "../views/NotFound.vue";
import ReadEmployees from "../views/employee/ReadEmployees.vue";
import CreateEmployee from "../views/employee/CreateEmployee.vue";
import UpdateEmployee from "../views/employee/UpdateEmployee.vue";
import ReadAirports from "../views/airport/ReadAirports.vue";
import CreateAirport from "../views/airport/CreateAirport.vue";
import UpdateAirport from "../views/airport/UpdateAirport.vue";
import ReadFlights from "../views/flight/ReadFlights.vue";
import CreateFlight from "../views/flight/CreateFlight.vue";
import UpdateFlight from "../views/flight/UpdateFlight.vue";
import ReadTravelers from "../views/traveler/ReadTravelers.vue";
import CreateTraveler from "../views/traveler/CreateTraveler.vue";
import UpdateTraveler from "../views/traveler/UpdateTraveler.vue";
import ReadSeats from "../views/seat/ReadSeats.vue";
import CreateSeat from "../views/seat/CreateSeat.vue";
import UpdateSeat from "../views/seat/UpdateSeat.vue";
import ReadTickets from "../views/ticket/ReadTickets.vue";
import CreateTicket from "../views/ticket/CreateTicket.vue";
import UpdateTicket from "../views/ticket/UpdateTicket.vue";

const routes = [
  // INDEX ROUTE
  {
    path: "/",
    component: Index,
  },
  // EMPLOYEE ROUTES
  {
    path: "/employee",
    component: ReadEmployees,
  },
  {
    path: "/employee/add",
    component: CreateEmployee,
  },
  {
    path: "/employee/:employeeId/update",
    component: UpdateEmployee,
    props: true,
  },
  //AIRPORT ROUTES
  {
    path: "/airport",
    component: ReadAirports,
  },
  {
    path: "/airport/add",
    component: CreateAirport,
  },
  {
    path: "/airport/:airportId/update",
    component: UpdateAirport,
    props: true,
  },
  //FLIGHT ROUTES
  {
    path: "/flight",
    component: ReadFlights,
  },
  {
    path: "/flight/add",
    component: CreateFlight,
  },
  {
    path: "/flight/:flightId/update",
    component: UpdateFlight,
    props: true,
  },
  //TRAVELER ROUTES
  {
    path: "/traveler",
    component: ReadTravelers,
  },
  {
    path: "/traveler/add",
    component: CreateTraveler,
  },
  {
    path: "/traveler/:travelerId/update",
    component: UpdateTraveler,
    props: true,
  },
  //SEAT ROUTES
  {
    path: "/seat",
    component: ReadSeats,
  },
  {
    path: "/seat/add",
    component: CreateSeat,
  },
  {
    path: "/seat/:seatId/update",
    component: UpdateSeat,
    props: true,
  },
  //TICKET ROUTES
  {
    path: "/ticket",
    component: ReadTickets,
  },
  {
    path: "/ticket/add",
    component: CreateTicket,
  },
  {
    path: "/ticket/:ticketId/update",
    component: UpdateTicket,
    props: true,
  },
  // 404 ROUTES
  {
    path: "/:pathMatch(.*)*",
    component: NotFound,
  },
  {
    path: "/:pathMatch(.*)",
    component: NotFound,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  linkActiveClass: "active-link",
});

export default router;
