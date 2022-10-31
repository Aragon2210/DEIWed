<template>
  <p>TODO: Home page for {{ appName }}</p>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class HomeView extends Vue {
  appName: string = process.env.VUE_APP_NAME;
}
</script>
