<template>
  <v-dialog
    :value="dialog"
    @input="$emit('close')"
    @keydown.esc="$emit('close')"
    max-width="75%"
  >
    <v-card>
      <v-card-title>
        <h2>Sessões do Participante</h2>
        <v-spacer/>
        <v-btn 
          color="primary" 
          dark
          @click="addSessionToAttendee"
          >
          <v-icon dark> fa-solid fa-plus </v-icon>
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
          no-data-text="O participante não está inscrito em nenhuma sessão"
          no-results-text="Nenhuma sessão corresponde aos critérios indicados"
          sort-by="theme"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-tooltip top>
              <template v-slot:activator="{ on }">
                <v-icon
                    class="mr-2 action-button"
                    v-on="on"
                    color="red"
                    @click="deleteSessionfromAttendee(item)"
                >fa-sharp fa-solid fa-delete-left</v-icon>
              </template>
              <span>Remover Sessão</span>
            </v-tooltip>
          </template>
        </v-data-table>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn dark color="blue darken-1" @click="$emit('close')">close</v-btn>
      </v-card-actions>
      
      <!--Add particpant to session-->
      <v-dialog v-model="addSession" max-width="40%">
        <v-card>
          <v-card-title>
            <h2>Selecione a sessão</h2>
          </v-card-title>

          <v-card-text>
            <v-data-table
              :headers="headers"
              :items="notEnrolledSessions"
              single-select
              locale="pt-PT"
              no-data-text="O participante está inscrito em todas sessão"
              sort-by="theme"
            >
              <template v-slot:[`item.actions`]="{ item }">
                <v-tooltip top>
                  <template v-slot:activator="{ on }">
                    <v-icon
                        class="mr-2 action-button"
                        v-on="on"
                        @click="selectSession(item)"
                    >fa-solid fa-plus</v-icon>
                  </template>
                  <span>Selecionar sessão</span>
                </v-tooltip>
              </template>
            </v-data-table>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn dark color="blue darken-1" @click="addSession = false">close</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import RemoteServices from '@/services/RemoteServices';
import { Component, Model, Prop, Vue } from 'vue-property-decorator';
import SessionDto from '@/models/deiwed/SessionDto';
import { DataTableHeader } from 'vuetify';

@Component
export default class ShowAttendeeSessionsDialog extends Vue {
  @Model('dialog', Boolean) dialog!: boolean;
  @Prop({ type: Array, required: true }) sessions!: SessionDto[];
  @Prop({ required: true })  readonly attendeeId!: number;

  headers: DataTableHeader[] = [
    { text: 'ID', value: 'id', sortable: true, filterable: true },
    { text: 'Tema', value: 'theme', sortable: true, filterable: true },
    { text: 'Orador', value: 'speaker', sortable: true, filterable: true },
    { text: 'Data', value: 'date', sortable: true, filterable: false },
    { text: '', value: 'actions', sortable: true, filterable: false },
  ];
  search = '';
  loading = false;

  addSession: boolean = false;
  notEnrolledSessions: SessionDto[] = [];

  async addSessionToAttendee() {
    try {
      this.notEnrolledSessions = await RemoteServices.getNotAttendeeSessions(this.attendeeId);
      this.addSession = true;
    } catch (error) {
      this.$store.dispatch('error', error);
    }
  }

  async selectSession(session: SessionDto) {
    if (
      session.id &&
        confirm('Têm a certeza que deseja adicionar o particpante a esta sessão?')
    ) {
      try {
        await RemoteServices.addAttendeeToSession(this.attendeeId, session.id);
        this.sessions.unshift(session);
      } catch (error) {
        this.$store.dispatch('error', error);
      }
      this.addSession = false;
      this.notEnrolledSessions = [];
    }
  }

  async deleteSessionfromAttendee(toDeleteSession: SessionDto) {
    if (
      toDeleteSession.id &&
        confirm('Têm a certeza que deseja remover o particpante desta sessão?')
    ) {
      try {
        await RemoteServices.removeAttendeeFromSession(this.attendeeId, toDeleteSession.id);
        this.sessions = this.sessions.filter(
            (session) =>
                session.id != toDeleteSession.id
        );
      } catch (error) {
        this.$store.dispatch('error', error);
      }
    }
  }
}
</script>