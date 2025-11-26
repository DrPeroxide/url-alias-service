<script setup>
const props = defineProps(['alias', 'fullUrl', 'shortUrl'])
const emit = defineEmits(['reload-aliases'])
const API_URL = import.meta.env.VITE_API_URL

function deleteAlias() {
  const url = `${API_URL}/${props.alias}`
  fetch(url, { method: 'DELETE' })
    .finally(() => emit('reload-aliases'))
}

</script>
<template>
  <li class="item">
    {{ alias }}
    - <a :href="fullUrl" target="_blank">{{ fullUrl }}</a>
    - <a :href="shortUrl" target="_blank">{{ shortUrl }}</a>
    - <button ref=deleteButton @click="deleteAlias">Delete</button>
  </li>
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