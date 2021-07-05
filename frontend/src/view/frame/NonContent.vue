<template>
  <section class="non-content-frame">
      <p>저장된 글이 없습니다.</p>
  </section>
</template>

<script>
export default {

}
</script>

<style>

.non-content-frame {
    position: absolute;
    width: 100%;
    height: inherit;
    background: #fff;
    z-index: 2021;
    display: flex;
    justify-content: center;
    align-items: center;
}

.non-content-frame > p {
    color: #666;
    font-size: 20px;
    font-weight: 500;
}

</style>