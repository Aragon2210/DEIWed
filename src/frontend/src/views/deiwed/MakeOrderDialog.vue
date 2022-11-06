<template>
  <v-dialog 
    :value="dialog"
    @input="$emit('close')"
    @keydown.esc="$emit('close')"
    max-width="40%">
    <v-card>
      <v-card-title>
        <h2>Pedido - FoodStore</h2>
        <v-spacer/>
        <v-btn 
          color="primary" 
          dark
          @click="toggleDishes"
          >
            Normal
        </v-btn>
        <v-btn 
          color="primary" 
          dark
          @click="toggleDishes"
          >
            Vegetariano
        </v-btn>
      </v-card-title>

      <v-card-text>
        <div v-if="normal">
          <h3>Prato Normal: {{order.normalOption.name}}</h3> 
        </div>
        <div v-else>
          <h3>Prato Vegetariano: {{order.vegetarianOption.name}}</h3> 
        </div>
        <v-data-table
          :headers="headers"
          :items="presentDishes"
          single-select
          locale="pt-PT"
          sort-by="theme"
        >
          <template v-slot:[`item.actions`]="{ item }">
            <v-tooltip top>
              <template v-slot:activator="{ on }">
                <v-icon
                    class="mr-2 action-button"
                    v-on="on"
                    @click="selectDish(item)"
                >fa-solid fa-plus</v-icon>
              </template>
              <span>Selecionar sessão</span>
            </v-tooltip>
          </template>
        </v-data-table>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn dark color="blue darken-1" @click="saveOrder">save</v-btn>
        <v-btn dark color="blue darken-1" @click="$emit('close')">close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script lang="ts">
import RemoteServices from '@/services/RemoteServices';
import DishDto from '@/models/deiwed/DishDto';
import { Component, Model, Prop, Vue } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';
import OrderDto from '@/models/deiwed/OrderDto';
import SessionDto from '@/models/deiwed/SessionDto';

@Component
export default class ShowAttendeeSessionsDialog extends Vue {
  @Model('dialog', Boolean) dialog!: boolean;
  @Prop({ required: true })  readonly session!: SessionDto;

  headers: DataTableHeader[] = [
    { text: 'ID', value: 'id', sortable: true, filterable: true },
    { text: 'Nome', value: 'theme', sortable: true, filterable: true },
    { text: 'Descrição', value: 'speaker', sortable: true, filterable: true },
    { text: 'Preço/Uni', value: 'date', sortable: true, filterable: false },
    { text: 'Peso/Uni', value: 'date', sortable: true, filterable: false },
    { text: '', value: 'actions', sortable: true, filterable: false },
  ];

  search = '';
  loading = false;
  dishes: DishDto[] = [];

  normal= true;
  order: OrderDto = new OrderDto();
  presentDishes: DishDto[] = [];

  async mounted() {
    await this.$store.dispatch('loading');
    try {
      this.dishes = await RemoteServices.getDishes();
      this.loading = false;
    } catch (error) {
      this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }

  toggleDishes() {
    this.normal = !this.normal;
    if(this.normal){
      this.presentDishes = this.dishes.filter(
            (dish) => !dish.vegetarian)
    } else {
      this.presentDishes = this.dishes.filter(
            (dish) => dish.vegetarian)
    }
  }

  selectDish(dish: DishDto){
    if(this.normal){
      this.order.normalOption = dish;
    } else {
      this.order.vegetarianOption = dish;
    }
  }

  async saveOrder() {
    try {
      this.order = await RemoteServices.createOrder(this.order, this.session.date);
    } catch (error) {
      this.$store.dispatch('error', error);
    }
  }
}
</script>