<template>
  <main class="write-frame">
    <meta-toolbar :meta="'write'"></meta-toolbar>
    <section class="write-body-frame">
      <section class="write-container">
        <textarea class="write-box" placeholder="입력" @input="autoHeight('.write-container>textarea', 36)"></textarea>
      </section>
      <section class="sub-container">
        <section class="write-category-container">
          <input id="write-category-poem" type="radio" v-model="selectedWriteCategory" value="poem">
          <label for="write-category-poem">시</label>
          <input id="write-category-review" type="radio" v-model="selectedWriteCategory" value="review">
          <label for="write-category-review">비평</label>
          <input id="write-category-theory" type="radio" v-model="selectedWriteCategory" value="theory">
          <label for="write-category-theory">미학론</label>
          <input id="write-category-opinion" type="radio" v-model="selectedWriteCategory" value="opinion">
          <label for="write-category-opinion">단상</label>
          <input id="write-category-line" type="radio" v-model="selectedWriteCategory" value="line">
          <label for="write-category-line">한줄모음</label>
        </section>
        <section class="write-sub-container">
          <div class="write-title-container">
            <input type="text" placeholder="제목 입력">
            <div class="shield" v-show="!titleExist"></div>
          </div>
          <div class="write-tag-container">
            <textarea placeholder="태그 입력" @input="autoHeight('.write-tag-container>textarea', 74)"></textarea>
            <div class="shield" v-show="!tagExist"></div>
          </div>
          <div class="write-written-status-container">
            <written-status-box ref="wsBox"></written-status-box>
            <div class="shield" v-show="!writtenStatusExist"></div>
          </div>
          <div class="write-do-button">
            <button @click="writeDo">작성 완료</button>
          </div>
        </section>
      </section>
    </section>
  </main>
</template>

<script>

import MetaToolbar from './frame/MetaToolbar.vue';
import WrittenStatusBox from './component/WrittenStatusBox.vue';

import { autoHeight } from '../effect/boxing-effect';
import { getTextId } from '../utils/generator';
import { writeApi } from '../utils/Apis';

export default {
  components: {
    MetaToolbar,
    WrittenStatusBox,
  },
  data() {
    return {
      selectedWriteCategory: 'poem',
      selectedWrittenStatus: '씨앗'
    }
  },
  computed: {
    titleExist() {
      return ['poem', 'review', 'theory'].includes(this.selectedWriteCategory);
    },
    tagExist() {
      return ['poem', 'review', 'theory', 'opinion'].includes(this.selectedWriteCategory);
    },
    writtenStatusExist() {
      return ['poem', 'review'].includes(this.selectedWriteCategory);
    }
  },
  methods: {
    autoHeight,
    writeDo(){
      writeApi('/' + this.selectedWriteCategory, this.collectWriteText()).then( () => {
        this.refreshWriteText();
      });
    },
    collectWriteText(){
      const formText = { 
        textId: getTextId(this.selectedWriteCategory), 
        body: document.querySelector('.write-container>textarea').value
      }
      if( this.titleExist ) formText['title'] = document.querySelector('.write-title-container>input[type="text"]').value;
      if( this.tagExist ) formText['tag'] = document.querySelector('.write-tag-container>textarea').value;
      if( this.writtenStatusExist ) formText['writtenStatus'] = this.selectedWrittenStatus
      return formText;
    },
    refreshWriteText(){
      document.querySelector('.write-title-container>input[type="text"]').value = '';
      document.querySelector('.write-tag-container>textarea').value = '';
      this.$refs.wsBox.refreshWrittenStatus();
      document.querySelector('.write-container>textarea').value = '';
    },
    changeWrittenStatus(status){
      this.selectedWrittenStatus = status;
    }
  },
  watch: {
    selectedWriteCategory: function() {
      this.refreshWriteText();
    }
  }
}
</script>

<style>

.write-frame {
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.write-body-frame {
  width: 100%;
  height: calc(100vh - 65px);
  background: rgba(255, 255, 250, 1);
  box-shadow: 0 1px 6px 0 rgba(32, 33, 36, 0.28);
  display: flex;
  flex-direction: row;
  overflow: auto;
}

.write-container {
  padding: 30px 70px;
  min-width: 500px;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.write-container > textarea {
  position: relative;
  padding: 8px 10px;
  width: 100%;
  height: 36px;
  line-height: 20px;
  border: none;
  outline: none;
  resize: none;
  font-size: 15px;
  box-shadow: inset 0 1px 1px 1px rgba(32, 33, 36, 0.28),
              7px 0 0 0 rgba(32, 33, 36, 0.28);
}

.sub-container {
  width: 500px;
  display: flex;
  flex-direction: column;
}

.write-category-container {
  height: 60px;
  display: flex;
  justify-content: space-evenly;
}
.write-category-container > input[type="radio"]{
  display: none;
}
.write-category-container > input[type="radio"] + label {
  position: relative;
  margin-top: 5px;
  padding-bottom: 10px;
  width: 90px;
  display: flex;
  justify-content: center;
  align-items: flex-end;
  color: #666;
  font-size: 17px;
  cursor: pointer;
}
.write-category-container > input[type="radio"] + label::after {
  position: absolute;
  bottom: -5px;
  content: '';
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: rgba(102, 102, 102, 0.8);
  border: 1px solid #fff;
  box-shadow: inset 0 0 5px rgba(255, 146, 19, 0.2),
              0 0 5px #666;
}
.write-category-container > input[type="radio"]:checked + label {
  font-size: 19px;
  font-weight: 600;
}
.write-category-container > input[type="radio"]:checked + label::after {
  background: rgba(0, 76, 3, 0.8);
}

.sub-container {
  margin-bottom: 200px;
  display: flex;
  flex-direction: column;
}

.write-title-container {
  height: 150px;
  display: flex;
  justify-content: center;
  align-items: flex-end;
}
.write-title-container > input[type="text"] {
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
.write-title-container > .shield {
  position: absolute;
  width: 410px;
  height: 60px;
  background: rgba(255, 255, 250, 0.9);
}

.write-tag-container {
  min-height: 90px;
  display: flex;
  justify-content: center;
  align-items: flex-end;
}
.write-tag-container > textarea {
  margin-top: 70px;
  padding: 15px 15px;
  width: 400px;
  min-height: 64px;
  max-height: 100px;
  border: none;
  outline: none;
  resize: none;
  box-shadow: inset 0 1px 6px 0 rgba(32, 33, 36, 0.28);
  color: #666;
  font-size: 15px;
}
.write-tag-container > .shield {
  position: absolute;
  width: 410px;
  height: 80px;
  background: rgba(255, 255, 250, 0.9);
}

.write-written-status-container{
  width: 100%;
  height: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.write-written-status-container > .shield {
  position: absolute;
  width: 300px;
  height: 100px;
  background: rgba(255, 255, 250, 0.9);
}

.write-do-button {
  height: 120px;
  display: flex;
  justify-content: center;
  align-items: flex-end;
}
.write-do-button > button {
  width: 140px;
  height: 38px;
  border: none;
  border-radius: 13px;
  background: #fff;
  border: 1px solid rgb(0, 76, 3, 0.5);
  border-right: 1px solid rgb(0, 76, 3, 0.2);
  border-bottom: 1px solid rgb(0, 76, 3, 0.2);
  color: #666;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}
.write-do-button > button:hover {
  background: rgba(0, 76, 3, 0.8);
  color: #fff;
}

</style>