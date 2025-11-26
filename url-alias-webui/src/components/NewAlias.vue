<script setup>
import { ref } from 'vue'
const emit = defineEmits(['reload-aliases'])
const data = ref({request: { customAlias: "alias", fullUrl: "http://example.com"}, response: {shortUrl: "" }})
const API_URL = import.meta.env.VITE_API_URL
const CREATE_ALIAS_URL = `${API_URL}/shorten`

function createNewAlias() {
  fetch(CREATE_ALIAS_URL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(data.value.request)
  })
  .then(response => response.json())
  .then(json => data.value.response.shortUrl = json.shortUrl)
  .finally(() => emit('reload-aliases'))
}

</script>
<template>
  <p>
    <input ref='aliasInput' v-model="data.request.customAlias">
    <input ref='urlInput' v-model="data.request.fullUrl">
    <button ref='shortenButton' @click="createNewAlias">Shorten</button>
  </p>
  <h2 v-if="data.response.shortUrl != ''">
    Shortened URL: <a :href="data.response.shortUrl" target="_blank">{{ data.response.shortUrl }}</a>
  </h2>
</template>

<style>
.item {
  width: 100%;
  height: 30px;
  background-color: #f3f3f3;
  border: 1px solid #666;
  box-sizing: border-box;
}
</style>