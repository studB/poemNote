<template>
  <div style="display: flex; justify-content: center; height: 100%">
      <main style="width: 100%; display: flex; flex-direction: row;">
        <section style="flex: 1; display: flex; align-items: center">
          <div style="position: relative;">
            <div style="position: absolute; left: 30px; top: -150px; background-color: black; color: white; width: 300px; height: 300px">Logo</div>
          </div>
        </section>
        <nav style="width: 700px;">
          <ul style="list-style-type: none; text-align: right; padding-top: 100px;">
            <li v-for="(item, index) in category" v-bind:key="index" style="padding-top:50px;">
              <div style="padding-right: 100px;">
                <span @click="enterContent(item)" style="padding-right: 50px;">{{item}}</span>
                <button @click="write(item)">write</button>
              </div>
            </li>
          </ul>
        </nav>
      </main>
      <write-modal v-bind:selectedCategory="this.selectedCategory"></write-modal>
  </div>
</template>

<script>

import WriteModal from '../components/content/WriteModal.vue';

export default {
  name: 'category',
  components: {
    WriteModal
  },
  methods: {
    enterContent(item){
      this.$router.push('/content/' + item);
    },
    write(item) {
      this.selectedCategory = item;
      this.$root.$emit('bv::show::modal', 'write-modal', '#btnShow');
    }
  },
  data(){
    return {
      category: ['poem', 'review', 'line', 'opinion', 'theory'],
      selectedCategory : ''
    }
  }
}
</script>

<style>

</style>