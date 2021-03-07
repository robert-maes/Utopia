import { createRouter, createWebHistory } from "vue-router";
import Index from "../views/Index.vue";
import AdminWrapper from "../views/admin/AdminWrapper.vue";
import AdminMain from "../views/admin/AdminMain.vue";
import EmployeeMain from "../views/employee/EmployeeMain.vue";
import TravelerMain from "../views/traveler/TravelerMain.vue";
import TravelerWrapper from "../views/traveler/TravelerWrapper.vue";
import EmployeeWrapper from "../views/employee/EmployeeWrapper.vue";
import EmployeeFlights from "../views/employee/EmployeeFlights.vue";
import EmployeeFlight from "../views/employee/EmployeeFlight.vue";
import EmployeeUpdateFlight from "../views/employee/EmployeeUpdateFlight.vue";
import AdminFlights from "../views/admin/AdminFlights.vue";
import AdminAddFlight from "../views/admin/AdminAddFlight.vue";
import EmployeeFlightAddSeats from "../views/employee/EmployeeFlightAddSeats.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Index,
  },
  {
    path: "/admn",
    name: "Admin",
    component: AdminWrapper,
    children: [
      {
        path: "main",
        name: "Admin Main",
        component: AdminMain,
      },
      {
        path: "main/flights",
        name: "Admin Flights",
        component: AdminFlights,
      },
      {
        path: "main/flights/add",
        name: "Admin Add Flight",
        component: AdminAddFlight,
      },
    ],
  },
  {
    path: "/emp",
    name: "Employee",
    component: EmployeeWrapper,
    children: [
      {
        path: "main",
        name: "Employee Main",
        component: EmployeeMain,
      },
      {
        path: "main/flights",
        name: "Employee Flights",
        component: EmployeeFlights,
      },
      {
        path: "main/flight/:flightId",
        name: "Employee Flight",
        component: EmployeeFlight,
        props: true,
      },
      {
        path: "main/flight/:flightId/update",
        name: "Employee Update Flight",
        component: EmployeeUpdateFlight,
        props: true,
      },
      {
        path: "main/flight/:flightId/seats",
        name: "Employe Add Seats to Flight",
        component: EmployeeFlightAddSeats,
        props: true,
      },
    ],
  },
  {
    path: "/trav",
    name: "Traveler",
    component: TravelerWrapper,
    children: [
      {
        path: "main",
        name: "Traveler Main",
        component: TravelerMain,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  linkActiveClass: "active-link",
});

export default router;
