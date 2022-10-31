<template>
  <nav>
    <v-app-bar class="d-none d-lg-block" color="secondary" height="36px">
      <v-toolbar-title>
        <v-btn
          dark
          class="dei-title"
          href="https://dei.tecnico.ulisboa.pt/"
          active-class="no-active"
          text
          tile
        >
          Departamento de Engenharia Informática
        </v-btn>
      </v-toolbar-title>
      <v-spacer />
      <v-toolbar-items>
        <v-switch
          v-model="darkMode"
          @change="updateDarkMode"
          color="info"
          style="padding-top: 5px"
        />
        <v-icon v-if="!darkMode" color="#ffa825">fas fa-sun</v-icon>
        <v-icon v-if="darkMode" color="#bebdb8">fas fa-moon</v-icon>
      </v-toolbar-items>
    </v-app-bar>

    <v-app-bar :fixed="isMobile" height="60px">
      <router-link to="/">
        <v-img
          :src="
            require(`../assets/img/ist_logo${darkMode ? '_white' : ''}.svg`)
          "
          class="navLogo"
          max-width="163px"
          :contain="true"
          alt="Técnico Logo"
        />
      </router-link>
      <v-spacer />
      <v-app-bar-nav-icon
        @click.stop="drawer = !drawer"
        class="d-block d-lg-none d-xl-none"
        aria-label="Menu"
      />
      <v-toolbar-items class="d-none d-lg-block deiwed-toolbar" hide-details>
        <v-menu
          v-for="item in navbarItems"
          :key="item.name"
          offset-y
          open-on-hover
        >
          <template v-slot:activator="{ on }">
            <v-btn
              class="deiwed-nav-button"
              v-on="on"
              style="cursor: default"
              text
            >
              {{ item.name }}
            </v-btn>
          </template>

          <v-list dense>
            <v-list-item
              v-for="subItem in item.list"
              :key="subItem.name"
              @click="goTo(subItem.path)"
            >
              <v-list-item-action>
                <v-icon>{{ subItem.icon }}</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>{{ subItem.name }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-toolbar-items>
    </v-app-bar>

    <v-navigation-drawer
      :height="windowHeight + 'px'"
      width="100vw"
      v-model="drawer"
      absolute
    >
      <v-list id="drawer1">
        <v-list-group
          v-for="item in navbarItems"
          :key="item.name"
          :prepend-icon="item.icon"
        >
          <template v-slot:activator>
            <v-list-item-title>{{ item.name }}</v-list-item-title>
          </template>

          <v-list-item
            v-for="subItem in item.list"
            :key="subItem.name"
            link
            @click="goTo(subItem.path)"
          >
            <v-list-item-title>{{ subItem.name }}</v-list-item-title>
          </v-list-item>
        </v-list-group>
      </v-list>

      <div id="drawer2">
        <div class="d-flex justify-center">
          <v-switch
            v-model="darkMode"
            @change="updateDarkMode"
            color="info"
            style="padding-top: 5px"
          />
          <v-icon v-if="!darkMode" color="#ffa825">fas fa-sun</v-icon>
          <v-icon v-else color="#bebdb8">fas fa-moon</v-icon>
        </div>
      </div>
    </v-navigation-drawer>
  </nav>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class TopBar extends Vue {
  appName: string = process.env.VUE_APP_NAME;
  darkMode = false;
  drawer = false;
  navbarItems: {
    name: string;
    icon: string;
    list: { name: string; path: string; icon: string }[];
  }[] = [];

  get windowWidth(): number {
    return window.innerWidth;
  }
  get windowHeight(): number {
    return window.innerHeight;
  }

  get isMobile(): boolean {
    return this.windowWidth < 1263;
  }

  goTo(path: string) {
    if (this.$route.path == path) {
      this.$router.go(0);
    } else {
      this.$router.push(path);
    }
  }

  created() {
    this.darkMode = this.$store.getters.isDarkMode;
    this.$vuetify.theme.dark = this.darkMode;
    this.navbarItems = [
      {
        name: 'DEI às Quartas',
        icon: 'fas fa-person-chalkboard',
        list: [
          {
            name: 'Attendees',
            path: '/attendees',
            icon: 'fas fa-users',
          },
        ],
      },
    ];
  }

  updateDarkMode() {
    this.darkMode = !this.$store.getters.isDarkMode;
    this.$store.dispatch('setDarkMode', this.darkMode);
    this.$vuetify.theme.dark = this.darkMode;
  }
}
</script>

<style lang="scss">
.no-active::before {
  opacity: 0 !important;
}
nav {
  z-index: 10;
}

@media screen and (max-width: 1263px) {
  nav {
    margin-bottom: 60px;
  }
}

#drawer1 {
  margin-top: 60px;
  height: 72vh;
  overflow-y: scroll;
  margin-right: -8px;
  padding-right: 8px;
}

#drawer2 {
  position: absolute;
  bottom: 0;
  width: 100%;
}
</style>
