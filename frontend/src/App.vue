<template>
  <Header />
  <main class="flex-shrink-0">
    <div class="container">
      <router-view></router-view>
    </div>
  </main>
  <Footer />
</template>

<script>
import { defineComponent } from "vue";
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
export default defineComponent({
  components: {
    Header,
    Footer,
  },
  setup() {
    return {};
  },
});
</script>

<style>
html {
  position: relative;
  min-height: 100%;
}
body {
  margin-bottom: 60px; /* Margin bottom by footer height */
}
.footer {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 60px; /* Set the fixed height of the footer here */
  line-height: 60px; /* Vertically center the text there */
  background-color: #f5f5f5;
}
main > .container {
  padding: 60px 15px 0;
}

form {
  padding: 1.5rem;
  border-top-left-radius: 0.25rem;
  border-top-right-radius: 0.25rem;
  border: solid #dee2e6 1px;
}
</style>
