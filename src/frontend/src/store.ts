import Vue from 'vue';
import Vuex from 'vuex';
import DeiwedError from './models/error/DeiwedError';

Vue.use(Vuex);
Vue.config.devtools = true;

interface State {
  error: boolean;
  errorMessage: string;
  loading: boolean;
  darkMode: boolean;
}

const state: State = {
  error: false,
  errorMessage: '',
  loading: false,
  darkMode: false,
};

export default new Vuex.Store({
  state,
  mutations: {
    error(state, errorMessage: DeiwedError) {
      state.error = true;
      state.errorMessage = errorMessage.message;
    },
    clearError(state) {
      state.error = false;
      state.errorMessage = '';
    },
    loading(state) {
      state.loading = true;
    },
    clearLoading(state) {
      state.loading = false;
    },
    setDarkMode(state, darkMode: boolean) {
      state.darkMode = darkMode;
    },
  },
  actions: {
    error({ commit }, errorMessage) {
      commit('error', errorMessage);
    },
    clearError({ commit }) {
      commit('clearError');
    },
    loading({ commit }) {
      commit('loading');
    },
    clearLoading({ commit }) {
      commit('clearLoading');
    },
    setDarkMode({ commit }, darkMode: boolean) {
      commit('setDarkMode', darkMode);
    },
  },
  getters: {
    isLoading(state): boolean {
      return state.loading;
    },
    isError(state): boolean {
      return state.error;
    },
    getErrorMessage(state): string {
      return state.errorMessage;
    },
    isDarkMode(state): boolean {
      return state.darkMode;
    },
  },
});
