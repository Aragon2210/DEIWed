<template>
  <v-card :flat="$store.getters.isMobile" class="table pb-4">
    <v-card-title>
      <h2>Sessões do DEI às Quartas</h2>
      <v-spacer/>
      <v-btn 
        color="primary" 
        dark
        @click="addSession"
        >
        <v-icon dark>
          fa-solid fa-plus
        </v-icon>
           Sessão
      </v-btn>
    </v-card-title>

    <v-card-text>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Pesquisar"
        single-line
        hide-details
      ></v-text-field>

      <v-data-table
        :headers="headers"
        :items="sessions"
        :search="search"
        :loading="loading"
        single-select
        locale="pt-PT"
        no-data-text="Nenhuma sessão existente"
        no-results-text="Nenhuma sessão corresponde aos critérios indicados"
        sort-by="theme"
      >
        <template v-slot:[`item.actions`]="{ item }">
          <v-tooltip top>
            <template v-slot:activator="{ on }">
              <v-icon
                  class="mr-2 action-button"
                  v-on="on"
                  @click="expandSession(item)"
              >fa-solid fa-up-right-and-down-left-from-center</v-icon>
            </template>
            <span>Mostrar mais detalhes</span>
          </v-tooltip>

          <v-tooltip top>
            <template v-slot:activator="{ on }">
              <v-icon
                  class="mr-2 action-button"
                  v-on="on"
                  color="red"
                  @click="deleteSession(item)"
              >fa-sharp fa-solid fa-delete-left</v-icon>
            </template>
            <span>Remover Sessão</span>
          </v-tooltip>
        </template>
      </v-data-table>
    </v-card-text>
    
    <!--session creation tab-->
    <v-dialog v-model="createEditDialog" max-width="75%">
      <v-card>
        <v-card-title>
          <span class="headline">Adicionar Sessão </span>
        </v-card-title>

        <v-card-text>
          <v-container>
            <v-row>
                <v-col cols="12" sm="4">
                    <v-hover v-slot="{ hover }" open-delay="200">
                        <v-card :elevation="hover ? 16 : 2" :class="{ 'on-hover': hover }">
                          <v-img :src="
                            require(`../../assets/img/ist_logo.svg`)" 
                            alt="" 
                            class=""/>
                        </v-card>
                    </v-hover>
                    <v-space></v-space>
                    <v-btn dark color="blue darken-1">upload</v-btn>
                </v-col>
                <v-col cols="12" sm="8">
                  <v-row>
                    <v-col col="12" sm="6">
                      <div class="mt-5">
                        <h2 class="blog-post-title mb-1">Orador*:</h2>
                        <v-text-field
                            v-model="newSession.speaker"
                            label="Orador"
                        ></v-text-field>
                      </div>
                    </v-col>
                    <v-col cols="12" sm="6">
                      <div class="mt-5">
                        <h2 class="blog-post-title mb-1">Tema*:</h2>
                        <v-text-field
                            v-model="newSession.theme"
                            label="Tema"
                        ></v-text-field>
                      </div>
                    </v-col>
                  </v-row>
                  <v-space></v-space> 
                  <hr> 
                  <v-space></v-space>
                  <v-row>
                    <v-col cols="12" sm="6">
                      <h4></h4>
                      <v-card>
                        <v-card-title>
                          <h3>Pratos*:</h3>
                          <v-spacer/>
                          <v-btn 
                            color="primary" 
                            dark
                            @click="placeOrder"
                            >
                            <v-icon dark> fa-solid fa-plus </v-icon>
                          </v-btn>
                        </v-card-title>
                        <v-card-text>
                          <div class="mt-5">
                            <h3>Normal: </h3>
                          </div>
                          <div class="mt-5">
                            <h3>Vegetariano: </h3>
                          </div>
                        </v-card-text>
                      </v-card>
                    </v-col>
                    <v-col cols="12" sm="6">
                      <div class="mt-5">
                          <h2 class="blog-post-title mb-1">Date*:</h2>
                          <date-picker 
                            v-model="newSession.date" 
                            label="Data"
                            type="datetime" 
                            placeholder="Select datetime"
                          ></date-picker>
                      </div>
                    </v-col>
                  </v-row>   
                </v-col>
            </v-row>
          </v-container>
          <small>*indicates required field</small>
        </v-card-text>

        <v-card-actions>
          <v-spacer />
          <v-btn color="red darken-1" @click="closeDialogue">Cancel</v-btn>
          <v-btn color="green darken-1" @click="saveSession">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    
    <!--Expanded Session-->
    <show-session-dialog
      :dialog="sessionDialog"
      :session="sessionToShow"
      v-on:save="updatedSession"
      v-on:close="closeSessionDialog" 
    ></show-session-dialog>

    <make-order-dialog
      :dialog="placeOrderDialog"
      :session="newSession"
      v-on:close="closeOrderDialog"
    ></make-order-dialog>

  </v-card>
</template>

<script lang="ts">
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';
import SessionDto from '@/models/deiwed/SessionDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';
import ShowSessionDialog from './ShowSessionDialog.vue'
import MakeOrderDialog from './MakeOrderDialog.vue'

@Component({
  components: { DatePicker, ShowSessionDialog, MakeOrderDialog },
})
export default class SessionsView extends Vue {
  sessions: SessionDto[] = [];
  headers: DataTableHeader[] = [
    { text: 'ID', value: 'id', sortable: true, filterable: true },
    { text: 'Tema', value: 'theme', sortable: true, filterable: true },
    { text: 'Orador', value: 'speaker', sortable: true, filterable: true },
    { text: 'Data', value: 'date', sortable: true, filterable: false },
    { text: '', value: 'actions', sortable: true, filterable: false },
  ];
  search = '';
  loading = true;

  createEditDialog: boolean = false;
  newSession: SessionDto = new SessionDto();

  sessionDialog: boolean = false;
  sessionToShow: SessionDto = new SessionDto();

  placeOrderDialog: boolean = false;

  async mounted() {
    await this.$store.dispatch('loading');
    try {
      this.sessions = await RemoteServices.getSessions();
      this.loading = false;
    } catch (error) {
      this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

  closeDialogue() {
    this.createEditDialog = false;
  }

  addSession() {
    this.createEditDialog = true;
    this.newSession = new SessionDto();
  }

  closeSessionDialog() {
    this.sessionDialog = false;
  }

  expandSession(session: SessionDto) {
    this.sessionToShow = session;
    this.sessionDialog = true;
  }

  async updatedSession() {
    try {
      this.sessions = await RemoteServices.getSessions();
    } catch (error) {
      this.$store.dispatch('error', error);
    }
  }

  async saveSession() {
    try {
      this.newSession = await RemoteServices.createSession(this.newSession);

      if(this.newSession.id != 0){
        this.sessions.unshift(this.newSession);
      }

    } catch (error) {
      await this.$store.dispatch('error', error);
    }
    this.closeDialogue();
  }

  async deleteSession(
      toDeleteSession: SessionDto
  ) {
    if (
      toDeleteSession.id &&
        confirm('Têm a certeza que deseja remver esta sessão?')
    ) {
      try {
        await RemoteServices.deleteSession(
            toDeleteSession.id
        );
        this.sessions = this.sessions.filter(
            (session) =>
                session.id != toDeleteSession.id
        );
      } catch (error) {
        this.$store.dispatch('error', error);
      }
    }
  }

  closeOrderDialog() {
    this.placeOrderDialog = false;
  }

  placeOrder(){
    this.placeOrderDialog = true;
  }
}
</script>
