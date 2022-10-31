<template>
  <v-overlay :value="loading" absolute>
    <v-progress-circular indeterminate size="64" />
  </v-overlay>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
@Component
export default class Loading extends Vue {
  loading: boolean = this.$store.getters.isLoading;
  created() {
    this.loading = this.$store.getters.isLoading;
    this.$store.watch(
      (state, getters) => getters.isLoading,
      () => {
        this.loading = this.$store.getters.isLoading;
      }
    );
  }
}
</script>
