<template>
  <header class="category-toolbar-frame">
        <button @click="enterMainPage()"></button>
        <button id="poem" @click="enterContentPage('poem')">시</button>
        <button id="review" @click="enterContentPage('review')">비평</button>
        <button id="theory" @click="enterContentPage('theory')">미학</button>
        <button id="opinion" @click="enterContentPage('opinion')">단상</button>
        <button id="line" @click="enterContentPage('line')">한줄</button>
  </header>
</template>

<script>

import { enterMainPage, enterContentPage } from '../../utils/enter';

export default {
    props: {
        category: String
    },
    mounted() {
        this.markSelectedButton();
    },
    methods: {
        enterMainPage,
        enterContentPage,
        markSelectedButton() {
            this.buttonClean(document.getElementById('poem'));
            this.buttonClean(document.getElementById('review'));
            this.buttonClean(document.getElementById('theory'));
            this.buttonClean(document.getElementById('opinion'));
            this.buttonClean(document.getElementById('line'));

            const selectedButton = document.getElementById(this.category);
            selectedButton.style.background = 'rgb(255, 146, 19, 0.8)';
            selectedButton.style.color = '#fff';
            selectedButton.style.cursor = 'default';
        },
        buttonClean(button){
            button.style.background = 'none';
            button.style.color = '#666';
            button.style.cursor = 'pointer';
        }
    },
    watch: {
        category: function() {
            this.markSelectedButton();
        }
    }
}
</script>

<style>

.category-toolbar-frame {
    width: 100%;
    min-width: 500px;
    height: 65px;
    background: #fff;
    display: flex;
    align-items: center;
    z-index: 2021;
}
.category-toolbar-frame > button {
    position: relative;
    margin: 0 10px;
    width: 50px;
    height: 50px;
    border: none;
    border-radius: 50%;
    background: none;
    color: #666;
    font-size: 15px;
    font-weight: 600;
    transition: all 0.3s ease-in-out;
    cursor: pointer;
}
.category-toolbar-frame > button:first-child {
    width: 20px;
    height: 20px;
    background: none;
    border-style: solid;
    border-width: 15px 20px 15px 0;
    border-radius: 5px;
    border-color: transparent rgba(102, 102, 102, 0.7) transparent transparent;
}
.category-toolbar-frame > button:nth-child(n+2) {
    border: 1px solid rgb(255, 146, 19, 0.5);
    border-right: 1px solid rgb(255, 146, 19, 0.2);
    border-bottom: 1px solid rgb(255, 146, 19, 0.2);
    box-shadow: 0 5px 15px rgb(255, 146, 19, 0.15);
}
.category-toolbar-frame > button:nth-child(n+2):hover {
    background: rgb(255, 146, 19, 0.8);
    color: #fff;
}

</style>