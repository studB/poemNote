import Vue from 'vue';
import Router from 'vue-router';

import EntrancePage from '../view/Entrance-page.vue';
import MainPage from '../view/Main-page.vue';
import WritePage from '../view/Write-page.vue';
import ContentPage from '../view/Content-page.vue';
import PublishPage from '../view/Publish-page.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: '/',
    routes: [
        {
            path: '/',
            component: EntrancePage 
        },
        {
            path: '/main-page',
            component: MainPage
        },
        {
            path: '/write-page',
            component: WritePage
        },
        {
            path: '/content-page/:category',
            component: ContentPage,
            props: true
        },
        {
            path: '/publish-page/:category/:textId',
            component: PublishPage,
            props: true
        }
    ]
})