<template>
  <div id="app">
    <router-link to="/foo">Go to Foo</router-link>
    <br />
    <router-link :to="{ name: 'barRouter' }">Go to Bar</router-link>
    <br />
    <router-link to="/user/11">Go to User 11</router-link>
    <br />
    <router-link :to="{ name: 'userRouter', params: { userId: 22 } }"
      >Go to User 32</router-link
    >
    <br />
    <br />
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
