<template>
  <div v-if="fetchError">Error</div>
  <main v-else style="display: flex; flex-direction: row; height: 100%">
      <aside style="width: 200px; height: 500px;">
          <ul style="list-style-type: none;">
              <li v-for="(content, index) in this.data" :key="index">
                  <div @click="openContent(content)">
                      <span>{{content.title}}</span>
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
              </div>
          </div>
          <div style="height: 50px; display: flex; flex-direction: row; width: 400px; justify-content: center;">
              <button @click="reWriteContent(openedContent)">Re-Write</button>
              <button @click="deleteContent(openedContent.textId)">delete</button>
          </div>
      </section>
  </main>
</template>

<script>

import {fetchApi, deleteApi, reWriteApi} from '../../utils/Apis.js';

export default {
    props: {
        selectedCategory: String
    },
    data(){
        return {
            fetchError: false,
            data: [],
            openedContent: ''
        }
    },
    computed: {
        path(){
            return '/' + this.selectedCategory;
        }
    },
    created() {
        this.loadContent();
    },
    methods: {
        loadContent(){
            fetchApi(this.path).then( (res) => {
                this.fetchError = res.error !== null;
                this.data = res.data;
            })
        },
        openContent(content){
            this.openedContent = content;
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
                tag: document.getElementById(`${content.textId}-tag`).innerText
            }
            reWriteApi(this.path, writtenContent).then(() => {
                this.loadContent();
            });
        }
    },
}
</script>

<style>

</style>