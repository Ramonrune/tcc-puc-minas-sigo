<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0">Processos</span>
    </q-toolbar-title>

    <br />

    <div class="row q-gutter-sm">
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
        style="width: 300px"
      />
      <q-select
        filled
        dense
        v-model="model"
        :options="options"
        label="Status"
        style="width: 200px"
      />

      <q-input
        filled
        v-model="startDate"
        mask="##/##/####"
        label="Data de ínicio"
        dense
        style="max-width: 300px"
      >
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy
              ref="qDateProxy"
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date
                lang="pt-BR"
                :locale="dateLocale"
                v-model="startDate"
                mask="DD/MM/YYYY"
              >
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Fechar" color="primary" flat />
                  <q-btn label="OK" color="primary" flat v-close-popup />
                </div>
              </q-date>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>

      <q-input
        filled
        v-model="endDate"
        mask="##/##/####"
        label="Data de fim"
        dense
        style="max-width: 300px"
      >
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy
              ref="qDateProxy"
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date
                lang="pt-BR"
                :locale="dateLocale"
                v-model="endDate"
                mask="DD/MM/YYYY"
              >
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Fechar" color="primary" flat />
                  <q-btn label="OK" color="primary" flat v-close-popup />
                </div>
              </q-date>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
    </div>
    <br />

    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Status</th>
          <th class="text-left">Indicativo</th>
          <th class="text-left">Descrição</th>
          <th class="text-left">Planejamento iniciado</th>
          <th class="text-left">Criado por</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="text-left">Aguardando</td>
          <td class="text-left">
            <q-icon name="info" color="green" size="25px" />
          </td>
          <td class="text-left">Material Requirements Planning (MRP)</td>
          <td class="text-left">04/01/2021 16:00:00</td>
          <td class="text-left">Ramon Lacava</td>
          <td class="text-left q-gutter-xs">
            <q-btn color="primary" label="Visualizar" dense />
          </td>
        </tr>
      </tbody>
    </q-markup-table>
  </div>
</template>

<script>
import { getIndustryManagementList } from "../services/ProcessoIndustrial";

export default {
  name: "Processos",
  methods: {},
  mounted() {
    this.industryManagementList = getIndustryManagementList();
    let userData = JSON.parse(localStorage.getItem("USER_DATA"));
    this.companies = userData.filiais;
    this.selectedCompany = this.companies[0];

    let date = new Date();
    this.startDate = this.moment(new Date(date.getFullYear(), date.getMonth(), 1)).format("DD/MM/YYYY");
    this.endDate = this.moment(new Date(date.getFullYear(), date.getMonth() + 1, 0)).format("DD/MM/YYYY");

   
  },
  data() {
    return {
      industryManagementList: [],
      companies: [],
      selectedCompany: null,
      startDate: null,
      endDate: null,
      dateLocale: {
        /* starting with Sunday */
        days: "Domingo_Segunda_Terca_Quarta_Quinta_Sexta_Sábado".split("_"),
        daysShort: "Dom_Seg_Ter_Qua_Qui_Sex_Sab".split("_"),
        months: "Janeiro_Fevereiro_Marco_Abril_Maio_Junho_Julho_Agosto_Setembro_Outubro_Novembro_Dezembro".split(
          "_"
        ),
        monthsShort: "Jan_Fev_Mar_Abr_Mai_Jun_Jul_Ago_Set_Out_Nov_Dez".split(
          "_"
        ),
        firstDayOfWeek: 1,
      },
      moment: require("moment"),
      model: "Todos",
      options: [
        "Todos",
        "Aguardando",
        "Em andamento",
        "Concluido",
        "Falha",
        "Cancelado",
      ],
    };
  },
};
</script>

<style scoped>
.q-table__card {
  box-shadow: none !important;
}
</style>