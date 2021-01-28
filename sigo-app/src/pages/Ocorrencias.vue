<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span class="q-ml-sm" style="font-weight: 500; margin: 0"
        >Ocorrências</span
      >
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
    
    </div>
    <br />
    <q-markup-table>
      <thead>
        <tr>
          <th class="text-left">Status</th>
          <th class="text-left">Indicativo</th>
          <th class="text-left">Descrição</th>
          <th class="text-left">Data de registro</th>
          <th class="text-left">Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="occurence in occurenceList"
          :key="occurence.codigo + new Date().getTime()"
        >
          <td class="text-left">
            <!-- {{ getStatusDesc(industryManagement.status) }} -->
            <q-select
              :disable="admin == false"
              @input="onSelectedStatusChange(occurence)"
              filled
              dense
              option-value="codigo"
              option-label="nome"
              emit-value
              map-options
              v-model="occurence.status"
              :options="statusOptions.slice(1, statusOptions.length)"
              style="width: 150px"
            />
          </td>
          <td class="text-left">
            
            <q-icon
              name="error"
              color="red"
              size="25px"
              v-if="occurence.status == 0"
            />

            <q-icon
              name="check_box"
              color="green"
              size="25px"
              v-if="occurence.status == 1"
            />


        
          </td>
          <td class="text-left">{{ occurence.nome }}</td>
          <td class="text-left">
            {{
              moment(
                occurence.dataInicio,
                "YYYY-MM-DD"
              ).format("DD/MM/YYYY")
            }}
          </td>
        
          <td class="text-left q-gutter-xs">
            <q-btn
              v-if="admin == true"
              color="red"
              label="Excluir"
              @click="
                occurenceToExclude = occurence;
                showOccurenceRemoveWindow = true;
              "
              dense
            />
          </td>
        </tr>
      </tbody>
    </q-markup-table>


    <q-dialog v-model="showOccurenceRemoveWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Excluir processo</span>
        </q-card-section>

        <q-card-section v-if="occurenceToExclude != null">
          <div>
            Tem certeza que deseja excluir a ocorrência
            {{ occurenceToExclude.nome }} ?
          </div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            label="Não"
            color="red"
            v-close-popup
            @click="occurenceToExclude = null"
          />
          <q-btn label="Sim" color="primary" @click="removeOccurence" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>

import {getStatus, getStatusDescription, deleteOccurrence, updateOccurrenceStatus, getOccurrenceList} from '../services/Ocorrencias';

import { isMyUserAdmin } from "../services/Usuario";

export default {
  name: "Processos",
  methods: {
     navigate(industryManagement) {
      this.$router.push({
        name: "processo",
        params: { industryManagement },
      });
    },
    async onSelectedStatusChange(occurence) {
      console.log(occurence);
      let response = await updateOccurrenceStatus(occurence);

      if (response != null && response.status == 200) {
        this.$q.notify({
          color: "positive",
          message: "Status da ocorrência atualizado com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        await this.onFilterChange();
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao atualizar o status da ocorrência, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    async removeOccurence() {
      let response = await deleteOccurrence(this.occurenceToExclude);
      this.occurenceToExclude = null;
      this.showOccurenceRemoveWindow = false;

      if (response != null && response.status == 204) {
        await this.onFilterChange();
        this.$q.notify({
          color: "positive",
          message: "Ocorrência excluida com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao remover a ocorrência, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    async onFilterChange() {
      this.selectedStatus = this.statusOptions[0];

      this.occurenceList = await getOccurrenceList(
        typeof this.selectedCompany === "object" &&
          this.selectedCompany !== null
          ? this.selectedCompany.codigo
          : this.selectedCompany,
        this.moment(this.startDate, "DD/MM/YYYY").format("YYYY-MM-DD"),
        this.moment(this.endDate, "DD/MM/YYYY").format("YYYY-MM-DD")
      );

     
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
    this.admin = isMyUserAdmin();
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

    this.occurenceList = await getIndustryManagementList(
      this.selectedCompany.codigo,
      this.moment(this.startDate, "DD/MM/YYYY").format("YYYY-MM-DD"),
      this.moment(this.endDate, "DD/MM/YYYY").format("YYYY-MM-DD")
    );

  },
  
  data() {
    return {
      admin: false,
      occurenceToExclude: null,
      showOccurenceRemoveWindow: false,
      occurenceList: [],

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