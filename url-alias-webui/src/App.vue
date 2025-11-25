<script setup>
import { ref, watchEffect } from 'vue'
const API_URL = `http://localhost:8081`
const aliases = ref([])
const newAlias = ref({customAlias: "alias", fullUrl: "http://example.com", shortUrl: ""})

watchEffect(() => {
  updateAliases()
})

function updateAliases() {
  const url = `${API_URL}/urls`
  fetch(url)
  .then(response => response.json())
  .then(data => aliases.value = data)
}


function deleteAlias(alias) {
  const url = `${API_URL}/${alias}`
  fetch(url, {
    method: 'DELETE'
  })
  .finally(() => updateAliases())
}


function addNewAlias() {
  const url = `${API_URL}/shorten`
  fetch(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(newAlias.value)
  })
  .then(response => response.json())
  .then(data => newAlias.value.shortUrl = data.shortUrl)
  .finally(() => updateAliases())
}

</script>

<template>
  <h1>URL Alias Service</h1>
  <p>
    <input v-model="newAlias.customAlias">
    <input v-model="newAlias.fullUrl">
    <button @click="addNewAlias">Shorten</button>
  </p>
  <h2 v-if="newAlias.shortUrl != ''">
    Shortened URL: <a :href="newAlias.shortUrl" target="_blank">{{ newAlias.shortUrl }}</a>
  </h2>
  <ul v-if="aliases.length > 0">
    <li v-for="{ alias, fullUrl, shortUrl } in aliases" :key="alias">
      {{ alias }} - <a :href="fullUrl" target="_blank">{{ fullUrl }}</a> - <a :href="shortUrl" target="_blank">{{ shortUrl }}</a> - <button @click="deleteAlias(alias)">Delete</button>
    </li>
  </ul>
</template>

<style scoped></style>
