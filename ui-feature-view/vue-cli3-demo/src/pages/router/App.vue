<template>
  <div id="app">
    <router-link to="/foo">Go to Foo</router-link>
    <router-link to="/bar">Go to Bar</router-link>
    <router-link to="/user/11">Go to User 11</router-link>
    <router-link to="/user/32">Go to User 32</router-link>

    <router-view></router-view>
  </div>
</template>

<script>
import User from "./User.vue";
export default {
  name: "app",
  components: {
    User,
  },
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
