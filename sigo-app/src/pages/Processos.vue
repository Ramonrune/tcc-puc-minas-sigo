<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0">Gestão de processos industriais</span>
    </q-toolbar-title>

    <br />

    <div class="row q-gutter-sm">
      <q-select
        filled
        dense
        v-model="selectedCompany"
        :options="companies"
        @input="onFilterChange"
        option-value="codigo"
        option-label="nome"
        emit-value
        map-options
        label="Filial"
        style="width: 300px"
      />
      <q-select
        @input="onStatusChange"
        filled
        dense
        option-value="codigo"
        option-label="nome"
        emit-value
        map-options
        v-model="selectedStatus"
        :options="statusOptions"
        label="Status"
        style="width: 150px"
      />

      <q-input
        @input="onFilterChange"
        filled
        v-model="startDate"
        mask="##/##/####"
        label="Data de ínicio"
        dense
        style="max-width: 150px"
      >
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy
              ref="qDateProxy"
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date
                @input="onFilterChange"
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
        @input="onFilterChange"
        filled
        v-model="endDate"
        mask="##/##/####"
        label="Data de fim"
        dense
        style="max-width: 150px"
      >
        <template v-slot:append>
          <q-icon name="event" class="cursor-pointer">
            <q-popup-proxy
              ref="qDateProxy"
              transition-show="scale"
              transition-hide="scale"
            >
              <q-date
                @input="onFilterChange"
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
      <q-btn color="teal" label="Novo processo" dense />
    </div>
    <br />
    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Status</th>
          <th class="text-left">Indicativo</th>
          <th class="text-left">Descrição</th>
          <th class="text-left">Planejamento iniciado</th>
          <th class="text-left">Data prevista de encerramento</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="industryManagement in industryManagementListFilter"
          :key="industryManagement.codigo"
        >
          <td class="text-left">
            {{ getStatusDesc(industryManagement.status) }}
          </td>
          <td class="text-left">
            <q-icon
              name="info"
              color="grey"
              size="25px"
              v-if="industryManagement.codigo == 0"
            />

            <q-icon
              name="info"
              color="blue"
              size="25px"
              v-if="industryManagement.codigo == 2"
            />

            <q-icon
              name="av_timer"
              color="blue"
              size="25px"
              v-if="industryManagement.codigo == 1"
            />

            <q-icon
              name="check_box"
              color="green"
              size="25px"
              v-if="industryManagement.codigo == 4"
            />

            <q-icon
              name="error"
              color="red"
              size="25px"
              v-if="industryManagement.codigo == 3"
            />

            <q-icon
              name="cancel"
              color="red"
              size="25px"
              v-if="industryManagement.codigo == 5"
            />
          </td>
          <td class="text-left">{{ industryManagement.nome }}</td>
          <td class="text-left">
            {{
              moment(
                industryManagement.dataInicioPlanejamento,
                "YYYY-MM-DD"
              ).format("DD/MM/YYYY")
            }}
          </td>
          <td class="text-left">
            {{
              moment(
                industryManagement.dataFimPlanejamento,
                "YYYY-MM-DD"
              ).format("DD/MM/YYYY")
            }}
          </td>
          <td class="text-left q-gutter-xs">
            <q-btn color="teal" label="Visualizar" dense />
            <q-btn color="red" label="Excluir" dense />
          </td>
        </tr>
      </tbody>
    </q-markup-table>
  </div>
</template>

<script>
import {
  getIndustryManagementList,
  getStatusDescription,
  getStatus,
} from "../services/ProcessoIndustrial";

export default {
  name: "Processos",
  methods: {
    async onFilterChange() {
      this.selectedStatus = this.statusOptions[0];

      this.industryManagementList = await getIndustryManagementList(
        typeof this.selectedCompany === "object" &&
          this.selectedCompany !== null
          ? this.selectedCompany.codigo
          : this.selectedCompany,
        this.moment(this.startDate, "DD/MM/YYYY").format("YYYY-MM-DD"),
        this.moment(this.endDate, "DD/MM/YYYY").format("YYYY-MM-DD")
      );
      this.industryManagementListFilter = this.industryManagementList;
    },
    onStatusChange() {
      if (this.selectedStatus == -1) {
        this.industryManagementListFilter = this.industryManagementList;
        return;
      }

      this.industryManagementListFilter = this.industryManagementList.filter(
        (e) => e.status == this.selectedStatus
      );
    },
    getStatusDesc(code) {
      return getStatusDescription(code);
    },
  },
  async mounted() {
    let userData = JSON.parse(localStorage.getItem("USER_DATA"));
    this.companies = userData.filiais;
    this.selectedCompany = this.companies[0];

    let date = new Date();
    this.startDate = this.moment(
      new Date(date.getFullYear(), date.getMonth(), 1)
    ).format("DD/MM/YYYY");
    this.endDate = this.moment(
      new Date(date.getFullYear(), date.getMonth() + 1, 0)
    ).format("DD/MM/YYYY");

    this.statusOptions = getStatus();
    this.selectedStatus = this.statusOptions[0];

    this.industryManagementList = await getIndustryManagementList(
      this.selectedCompany.codigo,
      this.moment(this.startDate, "DD/MM/YYYY").format("YYYY-MM-DD"),
      this.moment(this.endDate, "DD/MM/YYYY").format("YYYY-MM-DD")
    );
    this.industryManagementListFilter = this.industryManagementList;
  },
  data() {
    return {
      industryManagementList: [],
      industryManagementListFilter: [],

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
      selectedStatus: null,
      statusOptions: [],
    };
  },
};
</script>

<style scoped>
.q-table__card {
  box-shadow: none !important;
}
</style>