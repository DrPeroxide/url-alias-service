<script setup>
import { ref } from 'vue'
import FoundAlias from './components/FoundAlias.vue'
import NewAlias from './components/NewAlias.vue'

const API_URL = import.meta.env.VITE_API_URL
const aliases = ref([])

function update() {
  const url = `${API_URL}/urls`
  fetch(url)
    .then(response => response.json())
    .then(json => aliases.value = json)
}
update()
</script>

<template>
  <div class="leftDiv">
    <h1>URL Shortener</h1>
    <NewAlias @reload-aliases="update" />
  </div>
  <div class="rightDiv">
    <h2>Current aliases</h2>
    <TransitionGroup tag="ul" name="fade" class="container">
      <FoundAlias v-for="{alias, fullUrl, shortUrl} in aliases" :key="alias" :alias="alias" :full-url="fullUrl" :short-url="shortUrl" @reload-aliases="update"/>
    </TransitionGroup>
  </div>
</template>

<style scoped>
.container {
  position: relative;
  padding: 0;
  list-style-type: none;
}

.leftDiv {
  background-color: #efefef;
  color: #000;
  height: 400px;
  width: 48%;
  float: left;
}

.rightDiv {
  background-color: #efefef;
  color: #000;
  height: 400px;
  width: 48%;
  float: right;
}

/* 1. declare transition */
.fade-move,
.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s cubic-bezier(0.55, 0, 0.1, 1);
}

/* 2. declare enter from and leave to state */
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: scaleY(0.01) translate(30px, 0);
}

/* 3. ensure leaving items are taken out of layout flow so that moving
      animations can be calculated correctly. */
.fade-leave-active {
  position: absolute;
}
</style>
