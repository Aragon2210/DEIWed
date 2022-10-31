import Vue from 'vue';
import Vuetify, { colors } from 'vuetify/lib';
import 'vuetify/dist/vuetify.min.css';

Vue.use(Vuetify);

const vuetify = new Vuetify({
  icons: { iconfont: 'mdi' },
  iconfont: 'fa',
  theme: {
    options: { customProperties: true },
    themes: {
      light: {
        primary: colors.grey.darken1,
        secondary: '#444444',
        accent: '#999999',
        error: '#b71c1c',
        darkOrange: '#c45d0e',
        edit: '#d4ad3c',
        light: '#f1f1f1',
        background: '#ffffff',
        info: '#0F9AD7',
        success: '#07844e',
        text: '#000000',
        chat: '#ebebeb',
      },
      dark: {
        primary: colors.grey.darken1,
        secondary: '#444444',
        accent: '#999999',
        error: '#6f0000',
        darkOrange: '#92460c',
        edit: '#bd9931',
        background: '#171F24',
        info: '#0F9AD7',
        success: '#07844e',
        text: '#ffffff',
        chat: '#737373',
      },
    },
  },
});

export default vuetify;
