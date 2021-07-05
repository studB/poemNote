<template>
  <main class="main-frame">
    <div class="frame-cover"></div>
    <section class="meta-container">
      <div class="meta-band-box"></div>
      <div class="logo-box">
        <div class="logo-back-circle"></div>
        <img src="../assets/quill-drawing-a-line.png"/>
      </div>
      <nav class="meta-menu-box">
        <button class="write-menu" @click="enterWritePage()">작성</button>
        <button class="statistic-menu">통계</button>
        <button class="manage-menu">관리</button>
      </nav>
    </section>
    <section class="category-container">
      <div class="category-box" style="--i:0;" @click="enterContentPage('poem')">시</div>
      <div class="category-box" style="--i:2;" @click="enterContentPage('review')">비평</div>
      <div class="category-box" style="--i:1;" @click="enterContentPage('theory')">미학론</div>
      <div class="category-box" style="--i:4;" @click="enterContentPage('opinion')">단상</div>
      <div class="category-box" style="--i:3;" @click="enterContentPage('line')">한줄모음</div>
    </section>
  </main>
</template>

<script>

import { setLogoMovingEvent, removeLogoMovingEvent } from '../effect/moving-effect';
import { enterWritePage, enterContentPage } from '../utils/enter';

export default {
  name: 'main-page',
  mounted() {
    setLogoMovingEvent();
  },
  beforeDestory() {
    removeLogoMovingEvent();
  },
  methods: {
    enterWritePage,
    enterContentPage
  }
}
</script>

<style>

.main-frame {
  position: relative;
  width: inherit;
  height: inherit;
  background-image: url("../assets/palmtreeO.gif");
  background-size: cover;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  z-index: 0;
}

.frame-cover {
  position: absolute;
  width: 100%;
  height: 100%;
  background: rgba(255, 255, 255, 0.95);
  z-index: -1;
}

.meta-container {
  position: relative;
  width: 100%;
  height: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  perspective: 1000px;
}

.meta-band-box {
  position: absolute;
  width: 100%;
  height: 300px;
  background: rgba(255, 255, 250, 1);
  box-shadow: 0px 8px 5px rgba(0,0,0,0.1),
              0px 0px 5px rgba(0,0,0,0.1);

}

.logo-box {
  width: 30%;
  max-width: 450px;
  min-width: 300px;
  height: 80%;
  display: flex;
  justify-content: center;
  align-items: center;
  transform-style: preserve-3d;
}

.logo-back-circle {
  position: absolute;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  background: linear-gradient(to bottom, rgb(0, 76, 3, 0.75), rgb(0,0,0,0));
  z-index: 1;
}

.logo-box > img {
  width: 180px;
  z-index: 2;
  transition: all 0.75s ease;
}

.meta-menu-box {
  position: relative;
  width: 70px;
  height: 80%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.meta-menu-box > button {
  margin-top: 30px;
  width: 50px;
  height: 50px;
  min-height: 50px;
  border: none;
  outline: none;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.5);
  color: #666;
  font-size: 15px;
  font-weight: 600;
  transition: all 0.3s ease-in-out;
}

.meta-menu-box .write-menu {
  border: 1px solid rgb(0, 76, 3, 0.5);
  border-right: 1px solid rgb(0, 76, 3, 0.2);
  border-bottom: 1px solid rgb(0, 76, 3, 0.2);
  box-shadow: 0 2px 8px 2px rgba(0,76,3,0.15);
}

.meta-menu-box .write-menu:hover {
  box-shadow: 0 1px 4px 1px rgba(0,76,3,0.15);
  background: rgb(0, 76, 3, 0.8);
  color: #fff;
}

.meta-menu-box .statistic-menu {
  border: 1px solid rgba(245, 70, 66, 0.5);
  border-right: 1px solid rgba(245, 70, 66, 0.2);
  border-bottom: 1px solid rgba(245, 70, 66, 0.2);
  box-shadow: 0 2px 8px 2px rgba(245, 70, 66, 0.15);
}

.meta-menu-box .statistic-menu:hover {
  box-shadow: 0 1px 4px 1px rgba(245, 70, 66, 0.15);
  background: rgb(245, 70, 66, 0.8);
  color: #fff;
}

.meta-menu-box .manage-menu {
  border: 1px solid rgba(8, 83, 156, 0.5);
  border-right: 1px solid rgba(8, 83, 156, 0.2);
  border-bottom: 1px solid rgba(8, 83, 156, 0.2);
  box-shadow: 0 2px 8px 2px rgba(8, 83, 156, 0.15);
}

.meta-menu-box .manage-menu:hover {
  box-shadow: 0 1px 4px 1px rgba(8, 83, 156, 0.15);
  background: rgb(8, 83, 156, 0.8);
  color: #fff;
}

.category-container {
  padding: 0px 100px;
  display: flex;
  justify-content: space-evenly;
  align-content: center;
}

.category-box {
  position: relative;
  margin: 100px 10px;
  width: 100px;
  min-width: 100px;
  height: 60px;
  background: rgba(250, 250, 250, 1);
  border-radius: 10px;
  border: 1px solid rgba(255,255,255,0.5);
  border-right: 1px solid rgba(255,255,255,0.2);
  border-bottom: 1px solid rgba(255,255,255,0.2);
  box-shadow: 0 3px 8px 5px rgba(0,0,0,0.1);
  color: #666;
  font-size: 15px;
  font-weight: 600;
  letter-spacing: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
.category-box:hover {
  box-shadow: 0 1.5px 4px 2.5px rgba(0,0,0,0.1);
}

.category-box::after {
  position: absolute;
  content: '';
  right: -10px;
  width: 20px;
  height: 20px;
  background: rgba(255, 255, 250, 0.9);
  border-radius: 50%;
  box-shadow: inset 0px 0px 10px rgb(255, 146, 19,0.2), 0px 0px 5px orange;
  animation: slide 3s linear infinite;
  animation-delay: calc(-0.5s * var(--i));
}

.category-box:hover::after {
  animation: none;
}


@keyframes slide {
    0%, 100%{
        transform: translateY(-10px);
    }
    50%{
        transform: translateY(10px);
    }
}
</style>