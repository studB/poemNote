<template>
  <header class="meta-toolbar-frame">
      <button @click="enterMainPage()"></button>
      <button id="write">작성</button>
      <button id="statistic">통계</button>
      <button id="manage">관리</button>
  </header>
</template>

<script>

import { enterMainPage } from '../../utils/enter';

export default {
    props: {
        meta: String
    },
    mounted() {
        this.markSelectedButton();
    },
    methods: {
        enterMainPage,
        markSelectedButton(){
            const selectedButton = document.getElementById(this.meta);
            switch(this.meta){
                case 'write':
                    selectedButton.style.background = 'rgb(0, 76, 3, 0.8)';
                    break;
                case 'statistic':
                    selectedButton.style.background = 'rgba(245, 70, 66, 0.8)';
                    break;
                case 'manage':
                    selectedButton.style.background = 'rgba(8, 83, 156, 0.8)';
                    break;
            }
            selectedButton.style.color = '#fff';
        }
    }
}
</script>

<style>

.meta-toolbar-frame {
    width: 100%;
    height: 65px;
    display: flex;
    align-items: center;
}

.meta-toolbar-frame > button {
    position: relative;
    margin: 0px 10px;
    width: 50px;
    height: 50px;
    border-radius: 50%;
    border: none;
    background: rgba(255, 255, 255, 0.5);
    color: #666;
    font-size: 15px;
    font-weight: 600;
    transition: all 0.3s ease-in-out;
}

.meta-toolbar-frame > button:first-child {
    width: 20px;
    height: 20px;
    background: none;
    border-style: solid;
    border-width: 15px 20px 15px 0;
    border-radius: 5px;
    border-color: transparent rgba(102, 102, 102, 0.7) transparent transparent;
}

.meta-toolbar-frame > #write {
    border: 1px solid rgb(0, 76, 3, 0.5);
    border-right: 1px solid rgb(0, 76, 3, 0.2);
    border-bottom: 1px solid rgb(0, 76, 3, 0.2);
    box-shadow: 0 5px 15px rgba(0,76,3,0.15);
}

.meta-toolbar-frame > #write:hover {
    background: rgb(0, 76, 3, 0.8);
    color: #fff;
}

.meta-toolbar-frame > #statistic {
    border: 1px solid rgba(245, 70, 66, 0.5);
    border-right: 1px solid rgba(245, 70, 66, 0.2);
    border-bottom: 1px solid rgba(245, 70, 66, 0.2);
    box-shadow: 0 5px 15px rgba(245, 70, 66, 0.15);
}

.meta-toolbar-frame > #statistic:hover {
    background: rgb(245, 70, 66, 0.8);
    color: #fff;
}

.meta-toolbar-frame > #manage {
    border: 1px solid rgba(8, 83, 156, 0.5);
    border-right: 1px solid rgba(8, 83, 156, 0.2);
    border-bottom: 1px solid rgba(8, 83, 156, 0.2);
    box-shadow: 0 5px 15px rgba(81, 96, 110, 0.15);
}

.meta-toolbar-frame > #manage:hover {
    background: rgb(8, 83, 156, 0.8);
    color: #fff;
}

</style>