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
</style>
