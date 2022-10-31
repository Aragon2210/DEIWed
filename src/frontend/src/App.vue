<template>
  <v-app id="app">
    <top-bar />
    <div style="display: flex; flex-flow: column; flex: 1 1 auto">
      <error-message />
      <loading />
      <router-view />
    </div>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import axios from 'axios';
import TopBar from '@/components/TopBar.vue';
import Loading from '@/components/Loading.vue';
import '@/assets/css/_global.scss';
import '@mdi/font/css/materialdesignicons.css';
import ErrorMessage from '@/components/ErrorMessage.vue';
require('typeface-roboto');

@Component({
  components: { TopBar, ErrorMessage, Loading },
})
export default class App extends Vue {
  created() {
    axios.interceptors.response.use(undefined, (err) => {
      return new Promise(() => {
        if (err.status === 401 && err.config && !err.config.__isRetryRequest) {
        }
        throw err;
      });
    });
  }
}
</script>

<style scoped>
/*! purgecss start ignore */
#app {
  text-align: center;
  display: flex;
  flex-direction: column;
}

/*noinspection CssUnusedSymbol*/
.application--wrap {
  min-height: initial !important;
}
/*! purgecss end ignore */
</style>
