<template>
  <section class="line-frame">
      <div class="line-container">
          <p class="pre-pre-pre-line"></p>
          <p class="pre-pre-line"></p>
          <p class="pre-line"></p>
          <p class="main-line"></p>
          <p class="post-line"></p>
          <p class="post-post-line"></p>
          <p class="post-post-post-line"></p>
      </div>
      <button class="delete-button" @click="deleteDo()"></button>
      <button class="line-up-button" @click="up()" v-show="index+1 !== contents.length"></button>
      <button class="line-down-button" @click="down()" v-show="index !== 0"></button>
      <non-content v-show="contents.length === 0"></non-content>
  </section>
</template>

<script>

import NonContent from './NonContent.vue'

import { fetchApi, deleteApi } from '../../utils/Apis';
import { lineUpMoving, lineDownMoving } from '../../effect/boxing-effect';

export default {
    components : {
        NonContent
    },
    data() {
        return {
            index: 0,
            contents: [],
            lines: { prpp:'', prp:'', pr:'', line:'', po:'', pop:'', popp:''}
        }
    },
    created() {
        this.loadContent(0);
    },
    methods: {
        loadContent(index) {
            fetchApi('/line').then( (res) => {
                this.contents = res.data;
                this.setLines();
                this.putContent(this.contents, index);
            })
        },
        putContent(contents, index) {
            if(contents.length === 0) return;
            if(index - 3 >= 0) {
                this.lines.prpp.style.visibility = 'visible';
                this.lines.prpp.innerText = contents[index-3].body;
            } else {
                this.lines.prpp.style.visibility = 'hidden';
            }
            if(index - 2 >= 0) {
                this.lines.prp.style.visibility = 'visible';
                this.lines.prp.innerText = contents[index-2].body;
            } else {
                this.lines.prp.style.visibility = 'hidden';
            }
            if(index - 1 >= 0) {
                this.lines.pr.style.visibility = 'visible';
                this.lines.pr.innerText = contents[index-1].body;
            } else {
                this.lines.pr.style.visibility = 'hidden';
            }
            this.lines.line.style.visibility = 'visible';
            this.lines.line.innerText = contents[index].body;
            if(index + 1 < contents.length) {
                this.lines.po.style.visibility = 'visible';
                this.lines.po.innerText = contents[index+1].body;
            } else {
                this.lines.po.style.visibility = 'hidden';
            }
            if(index + 2 < contents.length) {
                this.lines.pop.style.visibility = 'visible';
                this.lines.pop.innerText = contents[index+2].body;
            } else {
                this.lines.pop.style.visibility = 'hidden';
            }
            if(index + 3 < contents.length) {
                this.lines.popp.style.visibility = 'visible';
                this.lines.popp.innerText = contents[index+3].body;
            } else {
                this.lines.popp.style.visibility = 'hidden';
            }
        },
        setLines(){
            this.lines.prpp = document.querySelector('.pre-pre-pre-line');
            this.lines.prp = document.querySelector('.pre-pre-line');
            this.lines.pr = document.querySelector('.pre-line');
            this.lines.line = document.querySelector('.main-line');
            this.lines.po = document.querySelector('.post-line');
            this.lines.pop = document.querySelector('.post-post-line');
            this.lines.popp = document.querySelector('.post-post-post-line');
        },
        up() {
            if(this.index + 1 === this.contents.length) return;
            this.setLines();
            lineUpMoving(this.lines.prpp, this.lines.prp, this.lines.pr, this.lines.line, this.lines.po, this.lines.pop, this.lines.popp);
            this.setLines();
            this.index = this.index + 1;
            this.putContent(this.contents, this.index);
        },
        down() {
            if(this.index === 0) return;
            this.setLines();
            lineDownMoving(this.lines.prpp, this.lines.prp, this.lines.pr, this.lines.line, this.lines.po, this.lines.pop, this.lines.popp);
            this.setLines();
            this.index = this.index - 1;
            this.putContent(this.contents, this.index);
        },
        deleteDo() {
            const textId = this.contents[this.index].textId;
            deleteApi('/line', textId).then( () => {
                this.contents.splice(this.index, 1);
                if(this.index !== 0 && this.index >= this.contents.length){
                    this.index = this.index - 1;
                }
                this.putContent(this.contents, this.index);
            })
        }
    }
}
</script>

<style>

.line-frame {
    width: inherit;
    height: inherit;
    display: flex;
    align-items: center;
    overflow: auto;
}

.line-container {
    position: relative;
    margin: 50px 70px 130px 70px;
    width: 100%;
    min-height: 500px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.line-container > p {
    position: absolute;
    margin: 10px 0;
    padding: 15px 30px;
    display: flex;
    align-items: center;
    background: #fff;
    box-shadow: 0 0 5px rgba(255, 146, 19, 0.2),
                0 0 5px #666;
    visibility: hidden;
    transition: all 0.1s ease-in-out;
}

.main-line {
    width: 700px;
    height: 200px;
    z-index: 50;
    font-size: 22px;
    color: #666;
}
.pre-line {
    width: 550px;
    height: 158px;
    transform: translateY(-130px);
    z-index: 40;
    font-size: 17px;
    color: transparent;
    text-shadow: 0 0 3px #666;
}
.pre-pre-line {
    width: 432px;
    height: 123px;
    transform: translateY(-230px);
    z-index: 30;
    font-size: 13px;
    color: transparent;
    text-shadow: 0 0 6px #666;
}
.pre-pre-pre-line {
    width: 339px;
    height: 96px;
    transform: translateY(-290px);
    z-index: 20;
    font-size: 10px;
    color: transparent;
    text-shadow: 0 0 6px #666;
    opacity: 0;
}
.post-line {
    width: 550px;
    height: 158px;
    transform: translateY(130px);
    z-index: 40;
    font-size: 17px;
    color: transparent;
    text-shadow: 0 0 3px #666;
}
.post-post-line {
    width: 432px;
    height: 123px;
    transform: translateY(230px);
    z-index: 30;
    font-size: 13px;
    color: transparent;
    text-shadow: 0 0 6px #666;
}
.post-post-post-line {
    width: 339px;
    height: 96px;
    transform: translateY(290px);
    z-index: 20;
    font-size: 10px;
    color: transparent;
    text-shadow: 0 0 6px #666;
    opacity: 0;
}

.line-frame .delete-button {
    position: absolute;
    margin: 50px 70px 130px 70px;
}

.delete-button {
    width: 60px;
    height: 15px;
    border-radius: 16px;
    border: 3px solid rgba(102, 102, 102, 0.3);
    background: rgb(158, 158, 158, 0.5);
}

.line-up-button {
    position: absolute;
    margin: 50px 70px 130px 70px;
    right: 0;
    transform: translateY(-30px);
    width: 0;
    height: 0;
    background: none;
    border-radius: 5px;
    border-style: solid;
    border-width: 0 20px 30px 20px;
    border-color: transparent transparent rgba(102, 102, 102, 0.4) transparent;

}

.line-down-button {
    position: absolute;
    margin: 50px 70px 130px 70px;
    right: 0;
    transform: translateY(30px);
    width: 0;
    height: 0;
    background: none;
    border-radius: 5px;
    border-style: solid;
    border-width: 30px 20px 0 20px;
    border-color: rgba(102, 102, 102, 0.4) transparent transparent transparent;
}

</style>