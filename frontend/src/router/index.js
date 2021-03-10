import { createRouter, createWebHistory } from "vue-router";
import Index from "../views/Index.vue";
import NotFound from "../views/NotFound.vue";
import ReadEmployees from "../views/employee/ReadEmployees.vue";
import CreateEmployee from "../views/employee/CreateEmployee.vue";
import UpdateEmployee from "../views/employee/UpdateEmployee.vue";

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
