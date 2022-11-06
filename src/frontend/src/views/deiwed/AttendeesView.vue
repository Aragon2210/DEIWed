<template>
  <v-card :flat="$store.getters.isMobile" class="table pb-4">
    <v-card-title>
      <h2>Participantes no DEI às Quartas</h2>
      <v-spacer/>
      <v-btn 
        color="primary" 
        dark
        @click="addAttendee"
        >
        <v-icon dark>
          fa-solid fa-plus
        </v-icon>
           Participante
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
        :items="attendees"
        :search="search"
        :loading="loading"
        single-select
        locale="pt-PT"
        no-data-text="Não existem participantes registados"
        no-results-text="Nenhum participante corresponde aos critérios indicados"
        sort-by="name"
      >
        <template v-slot:[`item.type`]="{ item }">
          <v-chip
            v-if="item.type === 'TEACHER'"
            color="purple"
            text-color="white"
          >
            Professor
          </v-chip>
          <v-chip v-else color="green" text-color="white"> Bolseiro </v-chip>
        </template>

        <template v-slot:[`item.actions`]="{ item }">
          <v-tooltip top>
            <template v-slot:activator="{ on }">
              <v-icon
                  class="mr-2 action-button"
                  v-on="on"
                  @click="showSessions(item)"
              >fa-solid fa-calendar</v-icon>
            </template>
            <span>Sessões Do Participante</span>
          </v-tooltip>
          
          <v-tooltip top>
            <template v-slot:activator="{ on }">
              <v-icon
                  class="mr-2 action-button"
                  v-on="on"
                  @click="editAttendee(item)"
              >fa-solid fa-pen</v-icon>
            </template>
            <span>Editar Participante</span>
          </v-tooltip>

          <v-tooltip top>
            <template v-slot:activator="{ on }">
              <v-icon
                  class="mr-2 action-button"
                  v-on="on"
                  color="red"
                  @click="deleteAttendee(item)"
              >fa-sharp fa-solid fa-delete-left</v-icon>
            </template>
            <span>Remover Participante</span>
          </v-tooltip>
        </template>
      </v-data-table>
    </v-card-text>
    
    <!--attendee creation tab-->
    <v-dialog v-model="attendeeDialog" max-width="75%">
      <v-card>
        <v-card-title>
          <span class="headline">{{ getTitle() }}</span>
        </v-card-title>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  v-model="newOrUpdatedAttendee.name"
                  label="Nome*"
                  required
                ></v-text-field>
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  v-model="newOrUpdatedAttendee.istId"
                  label="IST ID*"
                  hint="istxxxxxx"
                ></v-text-field>
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-select
                  v-model="newOrUpdatedAttendee.type"
                  :items="['TEACHER', 'GRANTEE']"
                  label="Type*"
                  required
                ></v-select>
              </v-col>
            </v-row>
          </v-container>
          <small>*indicates required field</small>
        </v-card-text>

        <v-card-actions>
          <v-spacer />
          <v-btn color="red darken-1" @click="closeDialogue">Cancel</v-btn>
          <v-btn color="green darken-1" @click="saveAttendee">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    
    <!--Attendee's Sessions-->
    <show-attendee-sessions-dialog
      :dialog="sessionsDialog"
      :sessions="attendeeSessions"
      v-on:close="closeSessionsDialog"
    ></show-attendee-sessions-dialog>

  </v-card>
</template>

<script lang="ts">
import AttendeeDto from '@/models/deiwed/AttendeeDto';
import SessionDto from '@/models/deiwed/SessionDto';
import RemoteServices from '@/services/RemoteServices';
import ShowAttendeeSessionsDialog from './ShowAttendeeSessionsDialog.vue';
import { Component, Vue } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';

@Component({
  components: { ShowAttendeeSessionsDialog },
})
export default class AttendeesView extends Vue {
  attendees: AttendeeDto[] = [];
  headers: DataTableHeader[] = [
    { text: 'ID', value: 'id', sortable: true, filterable: true },
    { text: 'Nome', value: 'name', sortable: true, filterable: true },
    { text: 'IST ID', value: 'istId', sortable: true, filterable: true },
    { text: 'Tipo', value: 'type', sortable: true, filterable: false },
    { text: 'Actions', value: 'actions', sortable: true, filterable: false },
  ];
  search = '';
  loading = true;

  attendeeDialog: boolean = false;
  updateAttendee:boolean = false;
  newOrUpdatedAttendee: AttendeeDto = new AttendeeDto();

  attendeeSessions: SessionDto[] = [];
  sessionsDialog: boolean = false;

  async mounted() {
    await this.$store.dispatch('loading');
    try {
      this.attendees = await RemoteServices.getAttendees();
      this.loading = false;
    } catch (error) {
      this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

  getTitle() {
    return this.updateAttendee ? 'Edit Attendee' : 'New Attendee';
  }

  closeDialogue() {
    this.attendeeDialog = false;
    this.updateAttendee = false;
  }

  addAttendee() {
    this.attendeeDialog = true;
    this.newOrUpdatedAttendee = new AttendeeDto();
  }

  editAttendee(attendee: AttendeeDto) {
    this.newOrUpdatedAttendee = { ...attendee };
    this.attendeeDialog = true;
    this.updateAttendee = true;
  }

  async saveAttendee() {
    try {
      if (this.updateAttendee) {
        this.newOrUpdatedAttendee = await RemoteServices.updateAttendee(this.newOrUpdatedAttendee.id, this.newOrUpdatedAttendee);
        this.attendees = this.attendees.filter(
          (attendee) => attendee.id !== this.newOrUpdatedAttendee.id
        );
      } else {
        this.newOrUpdatedAttendee = await RemoteServices.createAttendee(this.newOrUpdatedAttendee);
      }
      this.attendees.unshift(this.newOrUpdatedAttendee);
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
    this.closeDialogue();
  }

  closeSessionsDialog() {
    this.sessionsDialog = false;
    this.attendeeSessions = [];
  }

  async showSessions(attendee: AttendeeDto) {
    this.sessionsDialog = true;
    try {
      this.attendeeSessions = await RemoteServices.getAttendeeSessions(attendee.id);
    } catch (error) {
      await this.$store.dispatch('error', error);
    }
  }

  async deleteAttendee(
      toDeleteAttendee: AttendeeDto
  ) {
    if (
      toDeleteAttendee.id &&
        confirm('Are you sure you want to delete this failed answer?')
    ) {
      try {
        await RemoteServices.deleteAttendee(
            toDeleteAttendee.id
        );
        this.attendees = this.attendees.filter(
            (attendee) =>
                attendee.id != toDeleteAttendee.id
        );
      } catch (error) {
        this.$store.dispatch('error', error);
      }
    }
  }
}
</script>
