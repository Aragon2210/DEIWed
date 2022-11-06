<template>
  <v-dialog
    :value="dialog"
    @input="$emit('close')"
    @keydown.esc="$emit('close')"
    max-width="75%"
  >
    <v-card>
      <v-card-title>
          <h2>Sessão</h2>
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
                    <v-btn v-if="editing" dark color="blue darken-1">upload</v-btn>
                </v-col>  
                <v-col cols="12" sm="8">
                  <v-col cols="12" sm="4">
                    <div v-if="!editing"> 
                      <h1 class="display-3 fst-italic">{{session.theme}}</h1>
                    </div>
                    <div v-else>
                      <v-text-field
                          v-model="updatedSession.theme"
                          label="Tema"
                      ></v-text-field>
                    </div>
                  </v-col>
                  <hr>
                  <v-row>
                    <v-col col="12" sm="6">
                      <div class="mt-5">
                          <h2 class="blog-post-title mb-1">Orador:</h2>
                          <div v-if="!editing" class="mt-5">
                            <h3>{{session.speaker}}</h3>
                          </div>
                          <div v-else>
                            <v-text-field
                                v-model="updatedSession.speaker"
                            ></v-text-field>
                          </div>
                      </div>
                    </v-col>
                    <v-col cols="12" sm="6">
                      <div class="mt-5">
                          <h2 class="blog-post-title mb-1">Date:</h2>
                          <div v-if="!editing" class="mt-5">
                            <h3>{{session.date}}</h3>
                          </div>
                          <div v-else>
                            <date-picker 
                              v-model="updatedSession.date" 
                              label="Data"
                              type="datetime" 
                              placeholder="Select datetime"
                            ></date-picker>
                          </div>
                      </div>
                    </v-col>
                  </v-row>
                  <v-space></v-space> 
                  <hr> 
                  <v-row>
                    <v-col cols="12" sm="6">
                      <h2 class="blog-post-title mb-1">Pratos:</h2>
                      <div class="mt-5">
                          <div class="mt-5">
                            <h3>ABCCC</h3>
                          </div>
                          <div class="mt-5">
                            <h3>Hellooooo(vegetariano)</h3>
                          </div>
                      </div>
                    </v-col>
                  </v-row>   
                </v-col>
            </v-row>
          </v-container>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn v-if="!editing" dark color="blue darken-1" @click="toggleEditing()">edit</v-btn>
        <v-btn v-if="!editing" dark color="blue darken-1" @click="$emit('close')">close</v-btn>

        <v-btn v-if="editing" dark color="blue darken-1" @click="closeEditing()">cancel</v-btn>
        <v-btn v-if="editing" dark color="blue darken-1" @click="saveEditing()">save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
  
<script lang="ts">
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';
import RemoteServices from '@/services/RemoteServices';
import { Component, Model, Prop, Vue } from 'vue-property-decorator';
import SessionDto from '@/models/deiwed/SessionDto';

@Component({
  components: { DatePicker },
})
export default class ShowSessionDialog extends Vue {
  @Model('dialog', Boolean) dialog!: boolean;
  @Prop({ required: true }) session!: SessionDto;

  search = '';
  loading = false;

  editing = false;
  updatedSession: SessionDto = new SessionDto();

  toggleEditing(){
      this.editing = !this.editing;
      this.updatedSession = { ...this.session }
  }

  closeEditing() {
      this.editing = !this.editing;
  }

  async saveEditing(){
      try {
          this.updatedSession = await RemoteServices.updateSession(this.updatedSession.id, this.updatedSession);

          if(this.updatedSession.id != 0){
              this.$emit('save');
              this.session = this.updatedSession;
          }

      } catch (error) {
          await this.$store.dispatch('error', error);
      }
      
      this.closeEditing()
  }
}
</script>