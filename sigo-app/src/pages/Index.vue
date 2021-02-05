<template>
  <q-page class="q-pa-md">
    <div>
      <span class="text-h5" style="font-weight: 500"
        >Seja bem-vindo(a) ao SIGO</span
      >
      <br> <br />
      <q-select
        filled
        dense
        v-model="selectedCompany"
        :options="companies"
        option-value="codigo"
        option-label="nome"
        emit-value
        map-options
        label="Filial"
      />
    </div>
    <br />
    <div class="row q-col-gutter-md">
      <div class="col-xs-12 col-sm-3 col-3">
        <q-card>
          <q-card-section class="bg-primary text-white">
            <div class="text-h6">Total de processos</div>
            <div class="text-subtitle6">Semana atual</div>
            <div class="text-subtitle2">150</div>
          </q-card-section>
        </q-card>
      </div>
      <div class="col-xs-12 col-sm-3 col-3">
        <q-card>
          <q-card-section class="bg-red-6 text-white">
            <div class="text-h6">Ocorrências</div>
            <div class="text-subtitle6">Semana atual</div>
            <div class="text-subtitle2">10</div>
          </q-card-section>
        </q-card>
      </div>

      <div class="col-xs-12 col-sm-3 col-3">
        <q-card>
          <q-card-section class="bg-yellow-10 text-white">
            <div class="text-h6">Pausas na produção</div>
            <div class="text-subtitle6">Semana atual</div>
            <div class="text-subtitle2">20</div>
          </q-card-section>
        </q-card>
      </div>
      <div class="col-xs-12 col-sm-3 col-3">
        <q-card>
          <q-card-section class="bg-green text-white">
            <div class="text-h6">Total de entregas</div>
            <div class="text-subtitle6">Semana atual</div>
            <div class="text-subtitle2">120</div>
          </q-card-section>
        </q-card>
      </div>
      <div :class="!isUserAdmin() ? 'col-xs-12 col-sm-6 col-6' : 'col-xs-12 col-sm-4 col-4'">
        <q-card style="height: 100%">
          <q-card-section>
            <div class="text-h6">Total de processos</div>
            <div class="text-subtitle2">por ano</div>
          </q-card-section>

          <q-separator />
          <bar-chart></bar-chart>
        </q-card>
      </div>
      <div :class="!isUserAdmin() ? 'col-xs-12 col-sm-6 col-6' : 'col-xs-12 col-sm-4 col-4'">
        <q-card style="height: 100%">
          <q-card-section>
            <div class="text-h6">Tecidos mais fabricados</div>
            <div class="text-subtitle2">por tipo</div>
          </q-card-section>

          <q-separator />
          <area-chart></area-chart>
        </q-card>
      </div>
      <div class="col-xs-12 col-sm-4 col-4" v-if="isUserAdmin() == true">
        <q-card style="height: 100%">
          <q-card-section>
            <div class="text-h6">Receitas e despesas</div>
            <div class="text-subtitle2">em milhões</div>
          </q-card-section>

          <q-separator />
          <spline-chart></spline-chart>
        </q-card>
      </div>

      <div class="col-xs-12 col-sm-6 col-6">
        <q-card style="height: 100%">
          <q-card-section>
            <div class="text-h6">Processos concluídos x inconformidade</div>
            <div class="text-subtitle2">por mês</div>
          </q-card-section>

          <q-separator />
          <column-chart></column-chart>
        </q-card>
      </div>

      <div class="col-xs-12 col-sm-6 col-6">
        <q-card style="height: 100%">
          <q-card-section>
            <div class="text-h6">Tempo médio de produção</div>
            <div class="text-subtitle2">em dias</div>
          </q-card-section>

          <q-separator />
          <timeline-chart></timeline-chart>
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script>
import BarChart from "../components/BarChart";
import AreaChart from "../components/AreaChart";
import SplineChart from "../components/SplineChart";
import ColumnChart from "../components/ColumnChart";
import TimelineChart from "../components/TimelineChart";
import { isMyUserAdmin, isMyUserCommon } from "../services/Usuario";

export default {
  name: "PageIndex",
  components: { BarChart, AreaChart, SplineChart, ColumnChart, TimelineChart },
  methods: {
    isUserCommon(){
      return isMyUserCommon();
    },
    isUserAdmin(){
      return isMyUserAdmin();
    }
  },
  mounted() {
    let userData = JSON.parse(localStorage.getItem("USER_DATA"));
    this.companies = userData.filiais;
    this.selectedCompany = this.companies[0];
  },
  data: function () {
    return {
      companies: [],
      selectedCompany: null,
    };
  },
};
</script>

