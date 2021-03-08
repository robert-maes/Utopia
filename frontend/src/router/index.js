import { createRouter, createWebHistory } from "vue-router";
import CreateAirport from "../views/airports/CreateAirport.vue";
import ReadAirports from "../views/airports/ReadAirports.vue";
import UpdateAirport from "../views/airports/UpdateAirport.vue";
import CreateFlight from "../views/flights/CreateFlight.vue";
import ReadFlights from "../views/flights/ReadFlights.vue";
import Index from "../views/Index.vue";

const routes = [
  {
    path: "/",
    component: Index,
  },
  {
    path: "/airports",
    component: ReadAirports,
  },
  {
    path: "/airports/add",
    component: CreateAirport,
  },
  {
    path: "/airports/:airportId/update",
    component: UpdateAirport,
    props: true,
  },
  {
    path: "/flights",
    component: ReadFlights,
  },
  {
    path: "/flights/add",
    component: CreateFlight,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  linkActiveClass: "active-link",
});

export default router;
