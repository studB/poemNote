<template>
  <main class="publish-frame">
      <header class="publish-category">
          <button @click="enterContentPage(category)"></button>
          <button v-if="category === 'poem'">시</button>
          <button v-else>비평</button>
      </header>
      <section class="publish-content-frame">
          <div class="publish-list-container">
              <span v-for="item in contents" :key="item.id" class="publish-index-container">
                    <input :id="'p' + item.publishIndex" type="radio" v-model="selectedIndex" :value="item.publishIndex" >
                    <label :for="'p' + item.publishIndex">{{item.publishIndex}}</label>
              </span>
          </div>
          <section class="publish-content-container">
              <div class="publish-body-container">
                  <p id="publish-body-box"></p>
              </div>
              <div class="publish-sub-container">
                  <div class="publish-content-title-container">
                      <span>제목</span>
                      <p id="publish-content-title-box"></p>
                  </div>
                  <div class="publish-content-tag-container">
                      <span>#</span>
                      <textarea @input="autoHeight('.publish-content-tag-container>textarea', 74)"></textarea>
                  </div>
                  <div class="completed-status-container" v-if="category === 'poem'">
                      <completed-status-box ref="csBox"></completed-status-box>
                  </div>
                  <div class="value-status-container">
                      <value-status-box ref="vsBox"></value-status-box>
                  </div>
                  <div class="update-publish-container">
                      <button id="update" @click="updateDo()">수정</button>
                  </div>
              </div>
          </section>
      </section>
  </main>
</template>

<script>

import { fetchApi, reWriteApi } from '../utils/Apis';
import { autoHeight } from '../effect/boxing-effect';
import { enterContentPage } from '../utils/enter';

import CompletedStatusBox from './component/CompletedStatusBox.vue';
import ValueStatusBox from './component/ValueStatusBox.vue';

export default {
    components: {
        CompletedStatusBox,
        ValueStatusBox
    },
    props: {
        category: String,
        textId: String
    },
    data() {
        return {
            selectedIndex: 1,
            contents: [],
            completedStatus: '',
            valueStatus: ''
        }
    },
    created() {
        this.loadPublished();
    },
    methods: {
        enterContentPage,
        autoHeight,
        loadPublished(){
            fetchApi(`/${this.category}/published/${this.textId}`).then( (res) => {
                this.contents = res.data;
                this.setContent(0);
            })
        },
        setContent(index){
            const content = this.contents[index];
            document.getElementById('publish-body-box').innerText = content.body;
            document.getElementById('publish-content-title-box').innerText = content.title;
            document.querySelector('.publish-content-tag-container>textarea').value = content.tag;
            this.valueStatus = content.valueStatus === null ? '가' : content.valueStatus;
            this.$refs.vsBox.setValueStatus(this.valueStatus);
            if(this.category === 'poem'){
                this.completedStatus = content.completedStatus === null ? '초고본' : content.completedStatus;
                this.$refs.csBox.setCompletedStatus(this.completedStatus);
            }
        },
        changeCompletedStatus(status){
            this.completedStatus = status;
        },
        changeValueStatus(status){
            this.valueStatus = status;
        },
        updateDo(){
            reWriteApi(`/${this.category}/publish`, this.collectPublishText()).then( () => {
                this.setUpdatedContent();
            })
        },
        collectPublishText(){
            const content = this.contents[this.selectedIndex - 1];
            const formText = {
                textId: this.textId,
                body: content.body,
                title: content.title,
                tag: document.querySelector('.publish-content-tag-container>textarea').value,
                valueStatus: this.valueStatus,
                publishIndex: this.selectedIndex
            }
            if(this.category === 'poem') formText['completedStatus'] = this.completedStatus;
            return formText;
        },
        setUpdatedContent(){
            this.contents[this.selectedIndex - 1].tag = document.querySelector('.publish-content-tag-container>textarea').value;
            this.contents[this.selectedIndex - 1].valueStatus = this.valueStatus;
            if(this.category === 'poem') this.contents[this.selectedIndex - 1].completedStatus = this.completedStatus;
        }
    },
    watch: {
        selectedIndex: function(newVal){
            this.setContent(newVal-1);
        }
    }
}
</script>

<style>

.publish-frame {
    width: 100vw;
    height: 100vh;
    overflow: hidden;
}

.publish-category {
    width: 100%;
    min-width: 300px;
    height: 65px;
    background: #fff;
    display: flex;
    align-items: center;
    z-index: 2021;
}
.publish-category > button:first-child {
    margin: 0 10px;
    width: 20px;
    height: 20px;
    background: none;
    border-style: solid;
    border-width: 15px 20px 15px 0;
    border-radius: 5px;
    border-color: transparent rgba(102, 102, 102, 0.7) transparent transparent;
}
.publish-category > button:nth-child(2) {
    position: relative;
    margin: 0 10px;
    width: 50px;
    height: 50px;
    border-radius: 50%;
    border: none;
    background: rgb(139,0,139, 0.8);
    box-shadow: 0 5px 15px rgb(255, 146, 19, 0.15);
    color: #fff;
    font-size: 15px;
    font-weight: 600;
}

.publish-content-frame {
    width: 100%;
    height: calc(100vh - 65px);
    display: flex;
}

.publish-list-container { 
    margin: 45px 0 30px 0;
    width: 120px;
    height: calc(100vh - 140);
    display: flex;
    flex-direction: column;
    align-items: center;
}

.publish-index-container{
    position: relative;
    margin-bottom: 30px;
}
.publish-index-container > input[type="radio"] {
    position: absolute;
    visibility: hidden;
}
.publish-index-container > input[type="radio"] + label {
    width: 35px;
    height: 35px;
    border-radius: 50%;
    border: none;
    background: rgba(155, 155, 155, 0.8);
    color: #fff;
    box-shadow: 0 2px 4px 1px rgba(32, 33, 36, 0.6);
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
}
.publish-index-container > input[type="radio"]:checked + label {
    background: rgba(170,0,170, 0.5);
    box-shadow: 0 1px 2px 0 rgba(32, 44, 36, 0.6);
    cursor: default;
}

.publish-content-container {
    width: calc(100vw - 120px);
    display: flex;
    flex-direction: row;
    background: #fff;
}

.publish-body-container {
    flex:1;
    min-width: 700px;
    display: flex;
    justify-content: center;
    align-items: center;
}
.publish-body-container > #publish-body-box {
    padding: 15px;
    width: 70%;
    height: 90%;
    box-shadow: 0 1px 4px 1px rgba(32, 33, 36, 0.28);
    color: #666;
    font-size: 13px;
}

.publish-sub-container {
    position: relative;
    width: 450px;
    display: flex;
    flex-direction: column;
    justify-content: center;
}
.publish-content-title-container {
    width: 400px;
    display: flex;
    flex-direction: column;
}
.publish-content-title-container > span {
    padding: 0 0 10px 8px;
}
.publish-content-title-container > p {
    padding: 10px;
    width: 400px;
    height: 50px;
    box-shadow: 0 1px 4px 1px rgba(32, 33, 36, 0.28);
    color: #666;
    font-size: 20px;
    font-weight: 500;
}

.publish-content-tag-container {
    margin-top: 30px;
    width: 400px;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.publish-content-tag-container > span {
    padding: 0 0 10px 8px;
    width: 400px;
    color: #666;
    font-size: 18px;
}
.publish-content-tag-container > textarea {
    padding: 15px;
    width: 400px;
    min-height: 64px;
    max-height: 200px;
    border: none;
    outline: none;
    resize: none;
    box-shadow: inset 0 1px 6px 0 rgba(32, 33, 36, 0.28);
    color: #666;
    font-size: 15px;
}

.completed-status-container {
    width: 400px;
    display: flex;
    justify-content: center;
}

.value-status-container {
    width: 400px;
    display: flex;
    justify-content: center;
}

.update-publish-container {
    margin-top: 30px;
    width: 400px;
    display: flex;
    justify-content: center;
}
.update-publish-container > #update {
    margin-top: 50px;
    width: 140px;
    height: 38px;
    border: none;
    border-radius: 19px;
    background: #fff;
    border: 1px solid rgb(139, 0, 139, 0.5);
    border-right: 1px solid rgb(139, 0, 139, 0.2);
    border-bottom: 1px solid rgb(139, 0, 139, 0.2);
    color: #666;
    font-size: 17px;
    font-weight: 600;
    transition: all 0.3s ease-in-out;
    cursor: pointer;
}
.update-publish-container > #update:hover {
    background: rgb(139,0,139, 0.8);
    color: #fff;
}

</style>