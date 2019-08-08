<template>
  <div id="app">
    <router-view/>
    <router-link :to="{name: 'Foot'}">Jump</router-link>
    <a href="/user.html">To Foot</a>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
