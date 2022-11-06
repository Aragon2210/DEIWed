<template>
    <v-dialog
      :value="dialog"
      @input="$emit('close')"
      @keydown.esc="$emit('close')"
      max-width="75%"
    >
      <v-card>
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

          </v-data-table>
        </v-card-text>
  
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn dark color="blue darken-1" @click="$emit('close')">close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </template>
  
  <script lang="ts">
  import { Component, Model, Prop, Vue } from 'vue-property-decorator';
  //import ShowQuestion from '@/views/teacher/questions/ShowQuestion.vue';
  import SessionDto from '@/models/deiwed/SessionDto';
  import { DataTableHeader } from 'vuetify';

  @Component({
    components: {
      //'show-question': ShowQuestion,
    },
  })
  export default class ShowAttendeeSessionsDialog extends Vue {
    @Model('dialog', Boolean) dialog!: boolean;
    @Prop({ type: Array, required: true }) readonly sessions!: SessionDto[];

    headers: DataTableHeader[] = [
      { text: 'ID', value: 'id', sortable: true, filterable: true },
      { text: 'Tema', value: 'theme', sortable: true, filterable: true },
      { text: 'Orador', value: 'speaker', sortable: true, filterable: true },
      { text: 'Data', value: 'date', sortable: true, filterable: false },
      //{ text: 'Actions', value: 'actions', sortable: true, filterable: false },
    ];
    search = '';
    loading = false;
  }
  </script>