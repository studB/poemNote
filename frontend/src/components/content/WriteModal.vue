<template>
  <b-modal id="write-modal" centered scrollable hide-footer>
    <template #modal-header="{ close }">
      <b-button id="close-button" @click="close()">
        close
      </b-button>
    </template>

    <!-- Editor -->

    <!-- title -->
    <div contenteditable="true" id="wTitle" v-if="this.titleExist"></div>

    <!-- content -->
    <div contenteditable="true" id="wBody"></div>

    <!-- tag -->
    <div contenteditable="true" id="wTag" v-if="this.tagExist">Wrtie Tag</div>

    <!-- completedStatus -->
    <div v-if="this.writtenStatusExist">
      <span>Written Status : </span><br/>
      <select v-model="selectedStatus">
        <option disabled value="">Select WrittenStatus</option>
        <option>발행가능</option>
        <option>작품미달</option>
        <option>씨앗</option>
      </select>
    </div>
    <!-- save --> 
    <button @click="saveAndClose()">save</button>
  </b-modal>
</template>

<script>

import { getTextId } from '../../utils/generator';
import { writeApi } from '../../utils/Apis';

export default {
  props: {
    selectedCategory: String
  },
  data() {
    return{
      selectedStatus: '',
    }
  },
  computed: {
    titleExist(){
      return ['poem', 'theory', 'review'].includes(this.selectedCategory);
    },
    title(){
      return this.titleExist ? document.getElementById("wTitle").innerText : '';
    },
    tagExist(){
      return ['poem', 'opinion', 'theory', 'review'].includes(this.selectedCategory);
    },
    tag(){
      return this.tagExist ? document.getElementById("wTag").innerText : '';
    },
    writtenStatusExist(){
      return ['poem', 'review'].includes(this.selectedCategory);
    },
    writtenStatus(){
      return this.writtenStatusExist ? this.selectedStatus : '';
    }
  },
  methods: {
    saveAndClose() {
      writeApi('/' + this.selectedCategory, this.writeContent()).then( (response) => {
        console.log(response);
        this.$root.$emit('bv::hide::modal', 'write-modal', '#btnShow');
      });
    },
    writeContent() {
      const content = { textId: getTextId(this.selectedCategory), body: document.getElementById('wBody').innerText };
      if(this.titleExist) content['title'] = this.title;
      if(this.tagExist) content['tag'] = this.tag;
      if(this.writtenStatusExist) content['writtenStatus'] = this.writtenStatus;
      return content;
    }
  }
}
</script>

<style>

.modal-header {
  padding: 0;
}

</style>
