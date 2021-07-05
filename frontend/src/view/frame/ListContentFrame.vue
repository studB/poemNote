<template>
  <section class="list-content-frame">
      <ul class="list-container">
          <li v-for="item in titles" :key="item.textId" :id="item.textId" @click="showContent(item.textId)">
              {{ item.title }}
              <button v-show="item.pcount > 0" @click.stop="enterPublishPage(category, item.textId)">{{item.pcount}}</button>
         </li>
      </ul>
      <section class="selected-content-container" v-show="selectedTextId">
          <div class="body-container">
              <textarea></textarea>
          </div>
          <div class="content-sub-container">
              <button @click="deleteDo()">X</button>
              <div class="content-title-container" v-if="titleExist">
                  <span>제목</span>
                  <input type="text">
              </div>
              <div class="content-tag-container" v-if="tagExist">
                  <span>#</span>
                  <textarea @input="autoHeight('.content-tag-container>textarea', 74)"></textarea>
              </div>
              <div class="content-written-status-container" v-if="writtenStatusExist">
                  <written-status-box ref="wsBox"></written-status-box>
              </div>
              <div class="rewrite-container">
                <button id="rewrite" @click="reWriteDo()">수정</button>
                <button id="publish" @click="publishDo()" v-show="contentWrittenStatus === '발행가능'">발행</button>
              </div>
          </div>
      </section>
      <section class="non-selected-content" v-show="!selectedTextId">
          <p>글을 선택해주세요.</p>
      </section>
      <non-content v-show="!titles"></non-content>
  </section>
</template>

<script>

import WrittenStatusBox from '../component/WrittenStatusBox.vue';

import NonContent from './NonContent.vue'
import { autoHeight } from '../../effect/boxing-effect';
import { fetchApi, reWriteApi, deleteApi, publishApi } from '../../utils/Apis';
import { enterPublishPage } from '../../utils/enter';

export default {
    components: { 
        WrittenStatusBox,
        NonContent
    },
    props : {
        category: String,
    },
    data() {
        return {
            titles: [],
            selectedTextId: '',
            contentWrittenStatus: '',
        }
    },
    computed: {
        titleExist() {
            return ['poem', 'review', 'theory'].includes(this.category);
        },
        tagExist() {
            return ['poem', 'review', 'theory', 'opinion'].includes(this.category);
        },
        writtenStatusExist() {
            return ['poem', 'review'].includes(this.category);
        },
        publishable() {
            return ['poem', 'review'].includes(this.category);
        }
    },
    created() {
        this.loadTitle();
    },
    methods: {
        enterPublishPage,
        autoHeight,
        loadTitle() {
            if(document.querySelector('.list-container>li.selected') !== null){
                document.querySelector('.list-container>li.selected').classList.remove('selected');
            }
            fetchApi(`/${this.category}/title`).then( (res) => {
                this.titles = res.data;
                this.selectedTextId = '';
            })  
        },
        showContent(textId) {
            this.selectedTextId = textId;
            fetchApi(`/${this.category}/${textId}`).then( (res) => {
                this.setContent(res.data);
            });
        },
        setContent(content){
            document.querySelector('.body-container>textarea').value = content.body;
            if(this.titleExist) document.querySelector('.content-title-container>input[type="text"]').value = content.title;
            if(this.tagExist) document.querySelector('.content-tag-container>textarea').value = content.tag;
            if(this.writtenStatusExist) {
                this.contentWrittenStatus = content.writtenStatus;
                this.$refs.wsBox.setWrittenStatus(this.contentWrittenStatus);
            }
            this.selectedContent = content;
        },
        changeWrittenStatus(status){
            this.contentWrittenStatus = status;
        },
        reWriteDo(){
            reWriteApi(`/${this.category}`, this.collectWriteText());
        },
        deleteDo(){
            deleteApi(`/${this.category}`, this.selectedTextId).then( () => {
                this.loadTitle();
            });
        },
        publishDo() {
            const formText = this.collectWriteText();
            reWriteApi(`/${this.category}`, formText).then( () => {
                publishApi(`/${this.category}`, formText).then( () => {
                    this.loadTitle();
                })
            })
        },
        collectWriteText(){
            const formText = {
                textId: this.selectedTextId,
                body: document.querySelector('.body-container>textarea').value
            }
            if( this.titleExist ) formText['title'] = document.querySelector('.content-title-container>input[type="text"]').value;
            if( this.tagExist ) formText['tag'] = document.querySelector('.content-tag-container>textarea').value;
            if( this.writtenStatusExist ) formText['writtenStatus'] = this.contentWrittenStatus
            return formText;
        }
    },
    watch: {
        category: function() {
            this.loadTitle();
        },
        selectedTextId: function(newVal) {
            if(newVal === '') return;
            if(document.querySelector('.list-container>li.selected') !== null){
                document.querySelector('.list-container>li.selected').classList.remove('selected');
            }
            document.getElementById(newVal).classList.add('selected');
        }
    }
}
</script>

<style>

.list-content-frame {
    width: 100%;
    height: inherit;
    display: flex;
}

.list-container {
    margin: 15px 0 30px 0;
    width: 30%;
    min-width: 250px;
    max-width: 400px;
    height: calc(100vh - 110px);
    overflow-y: auto;
}
.list-container > li {
    position: relative;
    padding: 8px 8px 8px 40px;
    height: 70px;
    display: flex;
    align-items: center;
    color: #666;
    font-size: 16px;
    cursor: pointer;
}
.list-container > li.selected {
    background: rgba(32,33,36,0.02);
    box-shadow: inset 0px 1px 6px 0px rgba(32,33,36,0.28);
    cursor: default;
}
.list-container > li > button {
    position: absolute;
    right: 3px;
    width: 35px;
    height: 35px;
    border-radius: 50%;
    border: none;
    background: rgba(170,0,170, 0.5);
    color: #fff;
    box-shadow: 0 2px 4px 1px rgba(32, 33, 36, 0.6);
    cursor: pointer;
}
.list-container > li > button:hover {
    box-shadow: 0 1px 2px 1px rgba(32, 33, 36, 0.6);
}

.non-selected-content {
    flex: 1;
    min-width: 500px;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
.non-selected-content > p {
    color: #666;
    font-size: 20px;
    font-weight: 500;
}

.selected-content-container {
    width: 100%;
    display: flex;
    flex-direction: row;
    background: #fff;
}
.body-container {
    flex: 1;
    min-width: 700px;
    display: flex;
    justify-content: center;
    align-items: center;
}
.body-container > textarea {
    padding: 15px;
    width: 70%;
    height: 90%;
    border: none;
    outline: none;
    resize: none;
    box-shadow: inset 0 1px 3px 0 rgba(32, 33, 36, 0.28);
    color: #666;
    font-size: 13px;
}

.content-sub-container {
    position: relative;
    width: 450px;
    display: flex;
    flex-direction: column;
    justify-content: center;
}
.content-sub-container > button {
    position: absolute;
    top: 10px;
    right: 50px;
    width: 30px;
    height: 30px;
    border-radius: 50%;
    border: none;
    background: none;
    color: #666;
    font-size: 17px;
    font-family: monospace;
    box-shadow: 0 1px 2px 1px rgba(32, 33, 38, 0.8);
}
.content-sub-container > button:hover {
    box-shadow: 0 1px 1px 1px rgba(132, 33, 38, 0.8);
    color: red;
}

.content-title-container {
    width: 400px;
    display: flex;
    flex-direction: column;
}
.content-title-container > span {
    padding: 0 0 10px 8px;
    width: 400px;
    font-size: 18px;
}
.content-title-container > input[type="text"]{
    padding: 10px;
    width: 400px;
    height: 50px;
    border: none;
    outline: none;
    box-shadow: inset 0 1px 6px 0 rgba(32, 33, 36, 0.28);
    color: #666;
    font-size: 20px;
    font-weight: 500;
}

.content-tag-container {
    margin-top: 30px;
    width: 400px;
    display: flex;
    flex-direction: column;
    align-items: center;
}
.content-tag-container > span {
    padding: 0 0 10px 8px;
    width: 400px;
    color: #666;
    font-size: 18px;
}
.content-tag-container > textarea {
    padding: 15px;
    width: 400px;
    min-height: 64px;
    max-height: 300px;
    border: none;
    outline: none;
    resize: none;
    box-shadow: inset 0 1px 6px 0 rgba(32, 33, 36, 0.28);
    color: #666;
    font-size: 15px;
}

.content-written-status-container {
    width: 400px;
    display: flex;
    justify-content: center;
}

.rewrite-container {
    margin-top: 50px;
    width: 400px;
    display: flex;
    justify-content: center;
}
.rewrite-container > #rewrite {
    width: 140px;
    height: 38px;
    border: none;
    border-radius: 19px;
    background: #fff;
    border: 1px solid rgb(255, 146, 19, 0.5);
    border-right: 1px solid rgb(255, 146, 19, 0.2);
    border-bottom: 1px solid rgb(255, 146, 19, 0.2);
    color: #666;
    font-size: 17px;
    font-weight: 600;
    transition: all 0.3s ease-in-out;
    cursor: pointer;
}
.rewrite-container > #rewrite:hover {
    background: rgb(255, 146, 19,0.8);
    color: #fff;
}

.rewrite-container > #publish {
    margin-left: 20px;
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
.rewrite-container > #publish:hover {
    background: rgb(139,0,139, 0.8);
    color: #fff;
}
</style>