<template>
  <div id="app">
    <MySlot> </MySlot>
    <MySlot>
      <p>Nice to meet you: slot</p>
    </MySlot>
    <NoneSlot>
      <p>Nice to meet you: slot</p>
    </NoneSlot>
    <NamedSlot>
      <template v-slot:header> Here might be a page title </template>
      great
      <template v-slot:footer> page end part </template>
    </NamedSlot>
  </div>
</template>

<script>
import MySlot from "./MySlot.vue";
import NoneSlot from "./NoneSlot.vue";
import NamedSlot from "./NamedSlot.vue";
export default {
  name: "app",
  components: {
    MySlot,
    NoneSlot,
    NamedSlot,
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
