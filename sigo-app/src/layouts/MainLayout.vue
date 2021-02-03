<template>
  <q-layout view="hHh lpR fFf" class="bg-grey-1">
    <q-header elevated class="bg-white text-grey-8" height-hint="64">
      <q-toolbar class="GNL__toolbar">
        <q-btn
          flat
          dense
          round
          @click="leftDrawerOpen = !leftDrawerOpen"
          aria-label="Menu"
          icon="menu"
          class="q-mr-sm"
        />

        <q-toolbar-title
          v-if="$q.screen.gt.xs"
          shrink
          class="row items-center no-wrap"
        >
          <img alt="Sigo" src="~assets/sigo-logo.png" style="" />
          <span class="q-ml-sm">Sistema Integrado de Gestão e Operação</span>
        </q-toolbar-title>

        <q-space />

        <q-space />

        <div class="q-gutter-sm row items-center no-wrap">
       
          <q-btn round flat>
            <q-avatar size="26px" color="primary" text-color="white">
             {{ this.userName!= null ? this.userName.slice(0,1) : ''}}
            </q-avatar>
            <q-tooltip>{{ this.userName }}</q-tooltip>
            <q-menu auto-close>
              <q-list dense style="min-width: 100px">
                <q-item clickable class="GL__menu-link">
                  <q-item-section>Perfil</q-item-section>
                </q-item>
                <q-item clickable class="GL__menu-link" @click="this.loggout">
                  <q-item-section>Sair</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
        </div>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      bordered
      content-class="bg-white"
      :width="280"
    >
      <q-scroll-area class="fit">
        <q-list padding class="text-grey-8">
          <q-item
            class="GNL__drawer-item"
            v-ripple
            clickable
            v-for="link in links1"
            :key="link.text"
            :to="link.to"
            v-if="showItemLink01(link)"
            style="text-decoration: none"
          >
            <q-item-section avatar>
              <q-icon :name="link.icon" />
            </q-item-section>
            <q-item-section>
              <q-item-label>{{ link.text }}</q-item-label>
            </q-item-section>
          </q-item>

          <q-separator inset class="q-my-sm" />

          <q-item
            class="GNL__drawer-item"
            v-ripple
            v-for="link in links2"
            :key="link.text"
            clickable
            :to="link.to"
            style="text-decoration: none"
          >
            <q-item-section avatar>
              <q-icon :name="link.icon" />
            </q-item-section>
            <q-item-section>
              <q-item-label>{{ link.text }}</q-item-label>
            </q-item-section>
          </q-item>

          <q-separator inset class="q-my-sm" />

          <q-item
            class="GNL__drawer-item"
            v-ripple
            v-for="link in links3"
            :key="link.text"
            clickable
            :to="link.to"
            style="text-decoration: none"
          >
            <q-item-section avatar>
              <q-icon :name="link.icon" />
            </q-item-section>
            <q-item-section>
              <q-item-label>{{ link.text }}</q-item-label>
            </q-item-section>
          </q-item>

          <q-separator inset class="q-my-sm" />

          <q-item
            class="GNL__drawer-item"
            v-ripple
            v-for="link in links4"
            :key="link.text"
            clickable
            :to="link.to"
            style="text-decoration: none"
          >
            <q-item-section avatar>
              <q-icon :name="link.icon" />
            </q-item-section>
            <q-item-section>
              <q-item-label>{{ link.text }}</q-item-label>
            </q-item-section>
          </q-item>

          <div class="q-mt-md">
            <div class="flex flex-center q-gutter-xs">
              <a
                class="GNL__drawer-footer-link"
                href="javascript:void(0)"
                aria-label="Privacy"
                >Privacidade</a
              >
              <span> · </span>
              <a
                class="GNL__drawer-footer-link"
                href="javascript:void(0)"
                aria-label="Terms"
                >Termos</a
              >
              <span> · </span>
              <a
                class="GNL__drawer-footer-link"
                href="javascript:void(0)"
                aria-label="About"
                >Sobre o SIGO</a
              >
            </div>
          </div>
        </q-list>
      </q-scroll-area>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import { fasGlobeAmericas, fasFlask } from "@quasar/extras/fontawesome-v5";
export default {
  name: "MainLayout",
  mounted() {
    this.userName = localStorage.getItem("NAME");
  },
  data() {
    return {
      userName: "Usuário",
      leftDrawerOpen: false,
      search: "",
      showAdvanced: false,
      showDateOptions: false,
      exactPhrase: "",
      hasWords: "",
      excludeWords: "",
      byWebsite: "",
      byDate: "Any time",
      links1: [
        { id: "home", icon: "web", text: "Painel", to: "/sigo" },
        { id: "users", icon: "person", text: "Usuários", to: "/sigo/usuarios" },
        {
          id: "companies",
          icon: "domain",
          text: "Filiais",
          to: "/sigo/filiais",
        },
        {
          id: "reports",
          icon: "bar_chart",
          text: "Relatórios",
          to: "/sigo/relatorios",
        },
      ],
      links2: [
        { icon: "view_list", text: "Processos", to: "/sigo/processos" },
        { icon: "warning", text: "Ocorrências", to: "/sigo/ocorrencias" },
      ],
      links3: [{ icon: "book", text: "Normas técnicas", to: "/sigo/normas" }],
      links4: [
        {
          icon: "supervisor_account",
          text: "Empresas de consultoria",
          to: "/sigo/empresas-consultoria",
        },
         {
          icon: "supervisor_account",
          text: "Consultorias",
          to: "/sigo/consultoria",
        },
      ],
    };
  },
  methods: {
    showItemLink01(item) {
      let userData = JSON.parse(localStorage.getItem("USER_DATA"));
      let hasAdmin =
        userData.permissoes.filter((e) => e.descricao == "ROLE_ADMIN").length >
        0
          ? true
          : false;

      console.log(hasAdmin);
      if (hasAdmin == false) {
        if (item.id == "users") {
          return false;
        }

        if (item.id == "companies") {
          return false;
        }
      }

      return true;
    },
    async loggout() {
      /* let response = await this.$axios.delete ('/tokens/revoke',
      {
        headers: {
          Authorization: 'Bearer ' + localStorage.getItem('TOKEN'),
        }
      });

      console.log(response);
      
      */
      localStorage.setItem("TOKEN", null);
      localStorage.setItem("CODE", null);
      localStorage.setItem("NAME", null);
      window.location.href = "/";
    },
  },
};
</script>

<style lang="sass">
.GNL
  &__toolbar
    height: 64px
  &__toolbar-input
    width: 55%
  &__drawer-item
    line-height: 24px
    border-radius: 0 24px 24px 0
    margin-right: 12px
    .q-item__section--avatar
      .q-icon
        color: #5f6368
    .q-item__label
      color: #3c4043
      letter-spacing: .01785714em
      font-size: .875rem
      font-weight: 500
      line-height: 1.25rem
  &__drawer-footer-link
    color: inherit
    text-decoration: none
    font-weight: 500
    font-size: .75rem
    &:hover
      color: #000
</style>