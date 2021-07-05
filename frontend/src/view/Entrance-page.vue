<template>
  <main class="entrance-frame">
    <section class="entrance-image-container">
      <div class="entrance-image-box"></div>
    </section>
    <section class="entrance-key-container">
      <input type="password"/>
      <button @click="enterMainPage()">방문자</button>
    </section>
  </main>
</template>

<script>

import { enterMainPage } from '../utils/enter';

export default {
  name: 'entrance',
  methods: {
    enterMainPage
  }

}
</script>

<style>

.entrance-frame {
  width: inherit;
  height: inherit;
  background: rgb(255, 255, 250);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items:  center;
}

.entrance-image-container {
  width: 240px;
  height: 240px;
  border-radius: 40px;
  box-shadow: inset 0 0 5px #004c03,
              0 0 10px #004c03;
}

.entrance-image-box {
  width: 100%;
  height: 100%;
  border-radius: 40px;
  background-image: url('../assets/palmtreeO.gif');
  background-size: cover;
}

.entrance-key-container {
  margin-top: 50px;
  display: flex;
  flex-direction: column;
  width: 200px;
}

.entrance-key-container > input[type="password"]{
  width: 100%;
  height: 35px;
  padding: 0 20px;
  border: none;
  outline: none;
  background: rgba(255, 255, 250, 0.2);
  border-radius: 30px;
  border: 1px solid rgba(255, 255, 250, 0.5);
  border-right: 1px solid rgba(255, 255, 250, 0.2);
  border-bottom: 1px solid rgba(255, 255, 250, 0.2);
  box-shadow: 0px 2px 4px 2px rgba(0, 0, 0, 0.2);
  font-size: 15px;
  color: #666;
}

.entrance-key-container > button {
  margin-top: 15px;
  line-height: 35px;
  border: none;
  outline: none;
  background: rgba(255, 255, 250, 0.2);
  border-radius: 30px;
  border: 1px solid rgba(255, 255, 250, 0.5);
  border-right: 1px solid rgba(255, 255, 250, 0.2);
  border-bottom: 1px solid rgba(255, 255, 250, 0.2);
  box-shadow: 0px 2px 4px 2px rgba(0, 0, 0, 0.2);
  font-size: 15px;
  color: #666;
  transition: box-shadow 0.3s ease;
}

.entrance-key-container > button:hover {
  box-shadow: 0px 1px 1px 1px rgba(0, 0, 0, 0.2);
}

</style>