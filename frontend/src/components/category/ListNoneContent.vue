<template>
  <div v-if="fetchError">Error</div>
  <div v-else style="overflow: auto;">
      <div v-for="(content, index) in this.data" v-bind:key="index">
        <div :contenteditable="editable" :id="content.textId + '-body'">{{content.body}}</div>
        <button @click="deleteContent(content.textId)">Delete</button>
        <div v-if="editable">
          <span>Tag : </span>
          <div contenteditable="true" :id="content.textId + '-tag'">{{content.tag}}</div>
          <button @click="reWriteContent(content)">Re-Write</button>
        </div>
      </div>
  </div>
</template>

<script>

import {fetchApi, deleteApi, reWriteApi} from '../../utils/Apis.js'

export default {
  
  props: {
    selectedCategory: String
  },
  data() {
    return {
      fetchError: false,
      data: []
    }
  },
  computed: {
    path(){
      return '/' + this.selectedCategory;
    },
    editable() {
      return this.selectedCategory === 'opinion';
    },
  },
  watch: {
    selectedCategory: function(){
      this.loadContent();
    }
  },
  created() {
    this.loadContent();
  },
  methods: {
    loadContent(){
      fetchApi(this.path).then( (res) => {
        console.log(res.data);
        this.fetchError = res.error !== null;
        this.data = res.data;
      })
    },
    deleteContent(textId){
      deleteApi(this.path ,textId).then(() => this.loadContent());
    },
    reWriteContent(content){
      const writtenContent = { 
        textId: content.textId, 
        body: document.getElementById(`${content.textId}-body`).innerText,
        tag: document.getElementById(`${content.textId}-tag`).innerText
      }
      reWriteApi(this.path, writtenContent).then( () => this.loadContent());
    }
  },
}
</script>

<style>

</style>