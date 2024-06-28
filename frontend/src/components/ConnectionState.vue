<script setup>
import {connected, id} from "@/socket/socket"

</script>

<template>
  <p>State: {{ connected }} {{id}}</p>
</template>

<style scoped>

</style>