<template>
  <main class="content-frame">
    <category-toolbar :category="category"></category-toolbar>
    <section class="content-container">
      <list-content-frame v-if="category === 'poem'" :category="'poem'"></list-content-frame>
      <list-content-frame v-else-if="category === 'review'" :category="'review'"></list-content-frame>
      <list-content-frame v-else-if="category === 'theory'" :category="'theory'"></list-content-frame>
      <list-content-frame v-else-if="category === 'opinion'" :category="'opinion'"></list-content-frame>
      <line-frame v-else></line-frame>
    </section>
  </main>
</template>

<script>

import CategoryToolbar from './frame/CategoryToolbar.vue';
import LineFrame from './frame/LineFrame.vue';
import ListContentFrame from './frame/ListContentFrame.vue';

export default {
  components: {
    CategoryToolbar,
    LineFrame,
    ListContentFrame
  },
  props: {
    category: String
  }
}
</script>

<style>

.content-frame {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.content-container {
  width: inherit;
  height: calc(100vh - 65px);
}

</style>