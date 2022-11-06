import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue'),
    },
    {
      path: '/attendees',
      name: 'attendees',
      component: () => import('@/views/deiwed/AttendeesView.vue'),
    },
    {
      path: '/sessions',
      name: 'sessions',
      component: () => import('@/views/deiwed/SessionsView.vue'),
    },
  ],
});

export default router;
