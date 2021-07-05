<template>
  <div v-if="fetchError">Error</div>
  <main v-else style="display: flex; flex-direction: row; height: 100%;">
      
      <aside style="width: 200px; height: 500px;">
        <ul style="list-style-type: none;">
          <li v-for="(content, index) in this.data" :key="index">
            <div @click="openContent(content)">
              <span>{{content.title}}</span>
              <span>{{content.pcount}}</span>
            </div>
          </li>
        </ul>
      </aside>
      <section v-if="openedContent === ''"></section>
      <section v-else style="flex:1; display: flex; flex-direction: column; height: 100%">
        <div style="flex:1;">
          <div contenteditable="true" :id="openedContent.textId + '-title'">{{openedContent.title}}</div>
          <textarea style="resize: none;" v-model="openedContent.body" :id="openedContent.textId + '-body'"></textarea>
        </div>
        <div style="height: 150px; display: flex; flex-direction: column; justify-content: center; align-items: center;">
          <div style="height: 100px">
            <span>Tag : </span><div contenteditable="true" :id="openedContent.textId + '-tag'">{{openedContent.tag}}</div>
            <div>
              <span>Written Status : </span><br/>
              <select v-model="selectedStatus">
                <option disabled value="">Select WrittenStatus</option>
                <option>발행가능</option>
                <option>작품미달</option>
                <option>씨앗</option>
              </select>
            </div>
          </div>
          <div style="height: 50px; display: flex; flex-direction: row; width: 400px; justify-content: center;">
            <div style="flex:1; position: relative;">
              <button style="width: 100%;" @click="publishStart()">Publish</button>
              <div v-show="beforePublish" style="position: absolute; top: -100px; left: 50px; heigth: 100px;">
                <div v-if="completedStatusExist"> Select CompletedStatus</div>
                <div>Select ValueStatus</div>
                <button @click="publishContent(openedContent)">Go ! </button>
              </div>
            </div>
            <div style="flex:1;">
              <button @click="reWriteContent(openedContent)" style="width: 100%;">Re-Write</button>
              <button @click="deleteContent(openedContent.textId)">delete</button>
            </div>
          </div>
        </div>
      </section>
  </main>
</template>

<script>

import {fetchApi, deleteApi, reWriteApi, publishApi} from '../../utils/Apis.js';

export default {
  props: {
    selectedCategory: String
  },
  data(){
    return{
      fetchError: false,
      data: [],
      beforePublish: false,
      openedContent: '',
      selectedStatus: ''
    }
  },
  computed: {
    path(){
      return '/' + this.selectedCategory;
    },
    completedStatusExist(){
      return this.selectedCategory === 'poem';
    }
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
      fetchApi(this.path + '/nopublished').then( (res) => {
        this.fetchError = res.error !== null;
        this.data = res.data;
      })
    },
    openContent(content){
      this.openedContent = content;
    },
    publishStart(){
      this.beforePublish = !this.beforePublish;
    },
    deleteContent(textId){
      deleteApi(this.path, textId).then( () => {
        this.loadContent();
        this.openedContent = '';
      });
    },
    reWriteContent(content){
      const writtenContent = {
        textId: content.textId,
        title: document.getElementById(`${content.textId}-title`).innerText,
        body: document.getElementById(`${content.textId}-body`).value,
        tag: document.getElementById(`${content.textId}-tag`).innerText,
        writtenStatus: this.selectedStatus
      }
      console.log(writtenContent);
      reWriteApi(this.path, writtenContent).then( () => {
        this.loadContent();
      })
    },
    publishContent(content){
      const publishContent = {
        textId: content.textId,
        title: document.getElementById(`${content.textId}-title`).innerText,
        body: document.getElementById(`${content.textId}-body`).value,
        tag: document.getElementById(`${content.textId}-tag`).innerText,
      }
      publishApi(this.path, publishContent).then( () => {
        this.loadContent();
      })
    }
  },
}
</script>

<style>

</style>