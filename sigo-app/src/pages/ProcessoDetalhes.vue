<template>
  <div class="q-pa-md">
    <q-toolbar-title shrink class="row items-center no-wrap">
      <span
        class="q-ml-sm"
        style="font-weight: 500; margin: 0"
        v-if="newProcess != null"
        >{{ newProcess.nome }}</span
      >

      <br />
    </q-toolbar-title>
    <span class="q-ml-sm" style="margin: 0" v-if="newProcess != null"
      >De
      {{ moment(newProcess.dataInicioPlanejamento).format("DD/MM/YYYY") }} até
      {{ moment(newProcess.dataFimPlanejamento).format("DD/MM/YYYY") }}</span
    >
    <br />
    <span class="q-ml-sm" style="margin: 0" v-if="newProcess != null"
      >Status: {{ getStatusDesc(newProcess.status) }}</span
    >
    <br />
    <br />

    <div class="row q-gutter-sm">
      <q-btn
        v-if="admin == true"
        color="teal"
        label="Novo processo"
        @click="showProcessWindow = true"
        dense
      />
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
          v-for="industryManagement in industryManagementList"
          :key="industryManagement.codigo + new Date().getTime()"
        >
          <td class="text-left">
            <!-- {{ getStatusDesc(industryManagement.status) }} -->
            <q-select
              :disable="admin == false"
              @input="onSelectedStatusChange(industryManagement)"
              filled
              dense
              option-value="codigo"
              option-label="nome"
              emit-value
              map-options
              v-model="industryManagement.status"
              :options="statusOptions.slice(1, statusOptions.length)"
              style="width: 150px"
            />
          </td>
          <td class="text-left">
            <q-icon
              name="info"
              color="grey"
              size="25px"
              v-if="industryManagement.status == 0"
            />

            <q-icon
              name="info"
              color="blue"
              size="25px"
              v-if="industryManagement.status == 2"
            />

            <q-icon
              name="av_timer"
              color="blue"
              size="25px"
              v-if="industryManagement.status == 1"
            />

            <q-icon
              name="check_box"
              color="green"
              size="25px"
              v-if="industryManagement.status == 4"
            />

            <q-icon
              name="error"
              color="red"
              size="25px"
              v-if="industryManagement.status == 3"
            />

            <q-icon
              name="cancel"
              color="red"
              size="25px"
              v-if="industryManagement.status == 5"
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
            <q-btn
              v-if="admin == true"
              color="red"
              label="Excluir"
              @click="
                processToExclude = industryManagement;
                showProcessRemoveWindow = true;
              "
              dense
            />
          </td>
        </tr>
      </tbody>
    </q-markup-table>

    <q-dialog v-model="showProcessWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Novo processo</span>
        </q-card-section>

        <q-card-section>
          <div class="row q-col-gutter-xs">
            <q-input
              filled
              v-model="newProcess.nome"
              label="Nome"
              class="col-12"
            />

            <q-input
              filled
              mask="##/##/####"
              v-model="newProcess.dataInicioPlanejamento"
              label="Data de inicio de planejamento"
              class="col-6"
            />

            <q-input
              filled
              mask="##/##/####"
              v-model="newProcess.dataFimPlanejamento"
              label="Data prevista de entrega"
              class="col-6"
            />

            <q-input
              filled
              type="textarea"
              v-model="newProcess.descricao"
              label="Descrição"
              class="col-12"
            />
          </div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            label="Cancelar"
            color="red"
            v-close-popup
            @click="resetForm"
          />
          <q-btn label="Cadastrar" color="primary" @click="validate" />
        </q-card-actions>
      </q-card>
    </q-dialog>

    <q-dialog v-model="showProcessRemoveWindow" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <span class="text-bold text-h5">Excluir processo</span>
        </q-card-section>

        <q-card-section v-if="processToExclude != null">
          <div>
            Tem certeza que deseja excluir o processo
            {{ processToExclude.nome }} ?
          </div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn
            label="Não"
            color="red"
            v-close-popup
            @click="processToExclude = null"
          />
          <q-btn label="Sim" color="primary" @click="removeProcess" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { getStatusDescription } from "../services/ProcessoIndustrial";

import { getIndustryManagementItemsList } from "../services/ProcessoIndustrialItem";

import { isMyUserAdmin } from "../services/Usuario";

export default {
  name: "Processo",
  methods: {
    async removeProcess() {
      let response = await deleteProcess(this.processToExclude);
      this.processToExclude = null;
      this.showProcessRemoveWindow = false;

      if (response != null && response.status == 204) {
        await this.onFilterChange();
        this.$q.notify({
          color: "positive",
          message: "Processo excluido com sucesso!",
          position: "top",
          timeout: 1000,
        });
        return;
      } else {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao remover o processo, tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });
      }
    },
    resetForm() {
      this.newProcess = {
        nome: "",
        status: 0,
        descricao: "",
        dataInicioPlanejamento: "",
        dataFimPlanejamento: "",
        codigoUsuario: "",
        codigoFilial: "",
        codigoExterno: "",
      };
    },
    async validate() {
      if (this.newProcess.nome.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Nome deve ser preenchido!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newProcess.dataInicioPlanejamento.length != 10) {
        this.$q.notify({
          color: "negative",
          message: "Data de início inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newProcess.dataFimPlanejamento.length != 10) {
        this.$q.notify({
          color: "negative",
          message: "Data prevista de encerramento inválida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      if (this.newProcess.descricao.trim() == "") {
        this.$q.notify({
          color: "negative",
          message: "Descrição deve ser preenchida!",
          position: "top",
          timeout: 1000,
        });
        return;
      }

      console.log(this.selectedCompany);
      this.newProcess.codigoUsuario = JSON.parse(
        localStorage.getItem("USER_DATA")
      ).codigo;
      this.newProcess.codigoFilial =
        typeof this.selectedCompany === "object" &&
        this.selectedCompany !== null
          ? this.selectedCompany.codigo
          : this.selectedCompany;

      this.newProcess.codigoExterno = "";

      let body = { ...this.newProcess };

      body.dataInicioPlanejamento = this.moment(
        this.newProcess.dataInicioPlanejamento,
        "DD/MM/YYYY"
      ).format("YYYY-MM-DD");

      body.dataFimPlanejamento = this.moment(
        this.newProcess.dataFimPlanejamento,
        "DD/MM/YYYY"
      ).format("YYYY-MM-DD");

      console.log(body);
      let response = await addNewProcess(body);
      if (response == null || response.status != 201) {
        this.$q.notify({
          color: "negative",
          message:
            "Ocorreu um problema ao tentar adicionar um novo processo, por favor tente novamente mais tarde!",
          position: "top",
          timeout: 1000,
        });

        return;
      }

      this.resetForm();
      await this.onFilterChange();
      this.showProcessWindow = false;

      this.$q.notify({
        color: "positive",
        message: "Processo adicionado com sucesso!",
        position: "top",
        timeout: 1000,
      });
      return;
    },

    getStatusDesc(code) {
      return getStatusDescription(code);
    },
  },
  async mounted() {
    this.admin = isMyUserAdmin();
    this.companies = JSON.parse(localStorage.getItem("USER_DATA")).filiais;

    let industryManagementRouteParam = {
      codigo: 1,
      nome: "bbbbbbbbbbbbbb",
      status: 1,
      descricao: "aadssadsad",
      dataInicioPlanejamento: "2021-01-21",
      dataFimPlanejamento: "2021-01-21",
      codigoUsuario: 1,
      codigoFilial: 1,
      codigoExterno: "366b3189-e848-46ab-8a07-1bd4d05187e2",
    }; //this.$route.params.industryManagement;

    let success = false;

    for (let company of this.companies) {
      if (company.codigo == industryManagementRouteParam.codigoFilial) {
        success = true;
      }
    }

    if (success) {
      this.newProcess = industryManagementRouteParam; 

      let response = await getIndustryManagementItemsList(this.newProcess.codigo);

      console.log(response);
    }
  },
  data() {
    return {
      admin: false,
      newProcess: {
        nome: "",
        status: 0,
        descricao: "",
        dataInicioPlanejamento: "",
        dataFimPlanejamento: "",
        codigoUsuario: "",
        codigoFilial: "",
        codigoExterno: "",
      },
      processToExclude: null,
      showProcessWindow: false,
      showProcessRemoveWindow: false,
      industryManagementList: [],

      companies: [],
      moment: require("moment"),
    };
  },
};
</script>

<style scoped>
.q-table__card {
  box-shadow: none !important;
}
</style>