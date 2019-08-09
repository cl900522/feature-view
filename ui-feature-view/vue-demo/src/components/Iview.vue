<template>
  <div>
    <i-button @click="show" type="primary">Click me!</i-button>

    <i-row>
        <i-col span="12">col-12</i-col>
        <i-col span="12">col-12</i-col>
    </i-row>

    <Layout>
        <Sider hide-trigger>Sider</Sider>
        <Layout>
            <Header>Header</Header>
            <Content>Content</Content>
            <Footer>Footer</Footer>
        </Layout>
    </Layout>

    <Slider v-model="size"></Slider>
    <Slider v-model="size_r" range></Slider>

    <Page :current="1" :total="1000"></Page>

    <Switch></Switch>

    <Table border :columns="columns1" :data="data1"></Table>
  </div>

</template>

<script>
export default {
  name: 'IVue',
  data () {
    return {
      size: 20,
      size_r: [60, 100],
      columns1: [{
        title: 'Name',
        key: 'name'
      },
      {
        title: 'Age',
        key: 'age'
      },
      {
        title: 'Address',
        key: 'address'
      }],
      data1: [{
        name: 'John Brown',
        age: 18,
        address: 'New York No. 1 Lake Park',
        date: '2016-10-03'
      },
      {
        name: 'Jim Green',
        age: 24,
        address: 'London No. 1 Lake Park',
        date: '2016-10-01'
      },
      {
        name: 'Joe Black',
        age: 30,
        address: 'Sydney No. 1 Lake Park',
        date: '2016-10-02'
      },
      {
        name: 'Jon Snow',
        age: 26,
        address: 'Ottawa No. 2 Lake Park',
        date: '2016-10-04'
      }]
    }
  },
  methods: {
    show () {
      this.$Message.info('This is a info tip')
    },
    visible () {
      return true
    }
  }
}
</script>
