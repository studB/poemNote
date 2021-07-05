<template>
  <div style="height: 100%;">
      <main style="display: flex; flex-direction: row; height: 100%">
        <section style="flex:1;">
          <list-exist-content-theory v-if="this.selectedCategory === 'theory'" v-bind:selectedCategory="this.selectedCategory"></list-exist-content-theory>
          <list-exist-content v-else-if="this.titleExist" v-bind:selectedCategory="this.selectedCategory"></list-exist-content>
          <list-none-content v-else v-bind:selectedCategory="this.selectedCategory"></list-none-content>
        </section>
        <nav style="width: 200px;">
          <ul style="list-style-type: none; padding-top:100px;">
            <li v-for="(item, index) in category" v-bind:key="index" style="padding-top: 50px;">
              <div>
                <span @click="enterAnotherContent(item)">{{item}}</span>
              </div>
            </li>
          </ul>
        </nav>
      </main>
  </div>
</template>

<script>

import ListExistContent from '../components/category/ListExistContent.vue';
import ListNoneContent from '../components/category/ListNoneContent.vue';
import ListExistContentTheory from '../components/category/ListExistContentTheory.vue';

export default {
  components: {
    ListExistContent,
    ListNoneContent,
    ListExistContentTheory
  },
  props: {
    selectedCategory: String
  },
  computed: {
    titleExist(){
      return ['poem', 'theory', 'review'].includes(this.selectedCategory);
    },
  },
  methods: {
    enterAnotherContent(item) {
      if(item !== this.selectedCategory){
        this.$router.push('/content/' + item);  
      }
    }
  },
  data(){
    return {
      category: ['poem', 'review', 'line', 'opinion', 'theory']
    }
  }

}
</script>

<style>
</style>