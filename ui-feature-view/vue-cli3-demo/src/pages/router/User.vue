<template>
  <div>
    <h1>User id:{{ user.id }}</h1>
    <h1>User Name:{{ user.name }}</h1>
    <h1>User Age:{{ user.age }}</h1>
  </div>
</template>

<script>
export default {
  props: [],
  data() {
    console.log(1);
    return {
      user: {
        id: 0,
        name: "",
        age: 0,
      },
    };
  },
  created() {
    console.log(2);
    this.user.id = this.$route.params.userId;
    this.user.name = "DLK";
    this.user.age = 23;
  },
  computed: {
    userComputed() {
      let userId = this.$route.params.userId;

      return {
        id: userId,
        name: "Hello",
        address: "Chengdu",
        age: 30,
      };
    },
  },
  /*beforeRouteUpdate(to, from, next) {
    // 也可以使用 watch->$route
    var userId = to.params.userId;
    this.user = {
      id: userId,
      name: "DL",
      age: "100",
    };

    //console.log(this.user);
    next();
  },*/
  watch: {
    $route(to, from, next) {
      // 也可以使用 beforeRouteUpdate
      var userId = to.params.userId;
      this.user = {
        id: userId,
        name: "DL",
        age: "100",
      };
    },
  },
};
</script>

<style>
</style>