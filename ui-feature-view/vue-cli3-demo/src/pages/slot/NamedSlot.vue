<template>
  <div>
    <Header>
      <slot name="header"></slot>
    </Header>
    <main>
      <slot></slot>
    </main>
    <footer>
      <slot name="footer"></slot>
    </footer>
  </div>
</template>

<script>
import Header from "./Head.vue";
export default {
  components: {
    Header,
  },
};
</script>